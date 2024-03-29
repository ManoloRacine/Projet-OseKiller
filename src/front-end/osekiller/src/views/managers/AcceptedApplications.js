import { useContext, useEffect, useState } from "react";
import AcceptedApplicationCard from "../../components/AcceptedApplicationCard";
import { getAllAcceptedApplications } from "../../services/ApplicationService";

import {
    signContract,
    generateContract,
    getContract,
    getContracts,
    getReport,
} from "../../services/ContractService";
import Modal from "react-bootstrap/Modal";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSquarePlus } from "@fortawesome/free-solid-svg-icons";
import Button from "react-bootstrap/Button";
import ContractTask from "../../components/ContractTask";
import LoadPdf from "../../components/LoadPdf";
import Signature from "../../components/Signature";
import dataURItoBlob from "../../utils/DataURLConverter";
import { AuthenticatedUserContext } from "../../App";

const AcceptedApplications = () => {
    const authenticatedUser = useContext(
        AuthenticatedUserContext
    )?.authenticatedUser;
    const [acceptedApplications, setAcceptedApplications] = useState([]);
    const [currentApplication, setCurrentApplication] = useState({});
    const [currentPdf, setCurrentPdf] = useState("");
    const [currentIdx, setCurrentIdx] = useState(0);
    const [modalTitle, setModalTitle] = useState("");
    const [tasks, setTasks] = useState([]);
    const [showModal, setShowModal] = useState(false);

    const handleCloseModal = () => {
        setTasks([]);
        setShowModal(false);
    };
    const handleShowTasksModal = (application) => {
        setModalTitle("Tâches et Responsabilités");
        setCurrentApplication(application);
        setCurrentPdf(undefined);
        setShowModal(true);
    };
    const handleShowPdfModal = (pdf, title) => {
        setModalTitle(title);
        setCurrentPdf(pdf);
        setCurrentApplication(undefined);
        setShowModal(true);
    };
    
    const handleShowContractModalById = (contractId) => {
        getContract(contractId).then((response) => {
            const blob = new Blob([response.data], {
                type: "application/pdf",
            });
            const data_url = window.URL.createObjectURL(blob);
            handleShowPdfModal(data_url, "Entente de stage");
        });
    };

    const handleShowReportModalById = (contractId) => {
        getReport(contractId).then((response) => {
            const blob = new Blob([response.data], {
                type: "application/pdf",
            });
            const data_url = window.URL.createObjectURL(blob);
            handleShowPdfModal(data_url, "Rapport de stage");
        });
    };

    const handleChangeTask = (index, newText) => {
        const newTaskState = [...tasks];
        newTaskState[index] = newText;
        setTasks(newTaskState);
    };
    const handleDeleteTask = (index) => {
        const newTaskState = [...tasks];
        newTaskState.splice(index, 1);
        setTasks(newTaskState);
    };

    const handleAddNewTask = () => {
        const newTaskState = [...tasks];
        newTaskState.push("");
        setTasks(newTaskState);
    };

    const handleGenerateContract = (studentId, offerId, tasks) => {
        generateContract(studentId, offerId, tasks)
            .then((response) => {
                const blob = new Blob([response.data], {
                    type: "application/pdf",
                });
                const data_url = window.URL.createObjectURL(blob);
                handleShowPdfModal(data_url);
            })
            .finally(() => fetchApplications());
        handleCloseModal();
    };

    const handleSignContract = (data) => {
        const payload = new FormData();
        const blob = dataURItoBlob(data);
        payload.append("image", blob);
        console.log(currentIdx);
        signContract(acceptedApplications[currentIdx].contractId, payload)
            .then((response) => {
                console.log(response);
                getContract(acceptedApplications[currentIdx].contractId).then(
                    (response) => {
                        const blob2 = new Blob([response.data], {
                            type: "application/pdf",
                        });
                        const data_url = window.URL.createObjectURL(blob2);
                        setCurrentPdf(data_url);
                    }
                );
            })
            .finally(() => fetchApplications());
    };

    useEffect(() => {
        fetchApplications();
    }, []);

    function fetchApplications() {
        if (authenticatedUser?.role === "MANAGER") {
            getAllAcceptedApplications()
                .then((response) => {
                    console.log(response.data);
                    setAcceptedApplications(response.data);
                })
                .catch((error) => {
                    console.error(error);
                });
        } else {
            getContracts(authenticatedUser?.id)
                .then((response) => {
                    console.log(response.data);
                    setAcceptedApplications(response.data);
                })
                .catch((error) => {
                    console.error(error);
                });
        }
    }

    function isContractSigned() {
        if (
            authenticatedUser?.role === "MANAGER" &&
            acceptedApplications[currentIdx]?.managerSigningDate != null
        ) {
            return true;
        } else if (
            authenticatedUser?.role === "COMPANY" &&
            acceptedApplications[currentIdx]?.companySigningDate != null
        ) {
            return true;
        } else
            return (
                authenticatedUser?.role === "STUDENT" &&
                acceptedApplications[currentIdx]?.studentSigningDate != null
            );
    }

    function showSignaturePad() {
        if (modalTitle === "Tâches et Responsabilités") {
            return false;
        } else if (isContractSigned()) {
            return false;
        } else if (authenticatedUser?.role === "MANAGER") {
            return (
                acceptedApplications[currentIdx]?.managerId ===
                authenticatedUser?.id
            );
        } else return true;
    }

    return (
        <>
            <h2 className="text-center">Ententes de stages</h2>
            <div>
                {acceptedApplications.map((acceptedApplication, index) => (
                    <AcceptedApplicationCard
                        application={acceptedApplication}
                        showContractGenerationModal={handleShowTasksModal}
                        handleShowReportModalById={
                            handleShowReportModalById
                        }
                        handleShowContractModalById={
                            handleShowContractModalById
                        }
                        key={index}
                        setSelectedApplicationIdx={() => setCurrentIdx(index)}
                    />
                ))}
            </div>
            <Modal show={showModal} onHide={handleCloseModal} size="xl">
                <Modal.Header closeButton>
                    <Modal.Title>{modalTitle}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {currentApplication &&
                        tasks.map((task, key) => (
                            <ContractTask
                                key={key}
                                index={key}
                                task={task}
                                handleChangeTask={handleChangeTask}
                                handleDeleteTask={handleDeleteTask}
                            />
                        ))}
                    {currentApplication && (
                        <div className="text-center">
                            <Button
                                variant="info"
                                className="mb-4 text-white"
                                onClick={handleAddNewTask}
                            >
                                <FontAwesomeIcon
                                    icon={faSquarePlus}
                                    size="xl"
                                />
                            </Button>
                        </div>
                    )}
                    {currentPdf && (
                        <LoadPdf
                            src={currentPdf}
                            width={"100%"}
                            title={"contract"}
                            type={"application/json"}
                            height={"700rem"}
                        />
                    )}
                </Modal.Body>
                <Modal.Footer>
                    {currentApplication && (
                        <button
                            className={"btn btn-primary"}
                            disabled={
                                tasks.filter((e) => e.trim() !== "").length <
                                    tasks.length || tasks.length < 1
                            }
                            onClick={() => {
                                handleGenerateContract(
                                    currentApplication.studentId,
                                    currentApplication.offerId,
                                    tasks
                                );
                            }}
                        >
                            Soumettre
                        </button>
                    )}
                    {showSignaturePad() && (
                        <div className={"me-auto"}>
                            <p className={"fs-4"}>Signature</p>
                            <Signature saveData={handleSignContract} />
                        </div>
                    )}
                </Modal.Footer>
            </Modal>
        </>
    );
};

export default AcceptedApplications;
