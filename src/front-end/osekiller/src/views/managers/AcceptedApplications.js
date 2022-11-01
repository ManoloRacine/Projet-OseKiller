
import { useEffect, useState } from "react";
import AcceptedApplicationCard from "../../components/AcceptedApplicationCard";
import { getAllAcceptedApplications } from "../../services/ApplicationService";

import generateContract from "../../services/ContractService";
import Modal  from "react-bootstrap/Modal";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSquarePlus } from "@fortawesome/free-solid-svg-icons";
import Button from 'react-bootstrap/Button';
import ContractTask from "../../components/ContractTask";


const AcceptedApplications = () => {
    const [acceptedApplications, setAcceptedApplications] = useState([]);
    const [currentApplication, setCurrentApplication] = useState({})
    const [tasks, setTasks] = useState([]);
    const [showModal, setShowModal] = useState(false);

    const handleCloseModal = () => {
        setTasks([])
        setShowModal(false)
    };
    const handleShowModal = (application) => {
        setCurrentApplication(application)
        setShowModal(true)
    };
    const handleChangeTask = (index, newText) => {
        const newTaskState = [...tasks]
        newTaskState[index] = newText
        setTasks(newTaskState)
    }
    const handleDeleteTask = (index) => {
        const newTaskState = [...tasks]
        newTaskState.splice(index, 1);
        setTasks(newTaskState)
    }

    const handleAddNewTask = () => {
        const newTaskState = [...tasks]
        newTaskState.push("")
        setTasks(newTaskState)
    }

    const handleGenerateContract = (studentId, offerId, tasks) => {
        generateContract(studentId, offerId, tasks).finally(() => {
                getAllAcceptedApplications()
                    .then((response) => {
                        setAcceptedApplications(response.data);
                    })
                    .catch((error) => {
                        console.error(error);
                    })
            }
        )
        handleCloseModal();
        
    }

    useEffect(()=>{
        getAllAcceptedApplications()
        .then((response) => {
            setAcceptedApplications(response.data);
        })
        .catch((error) => {
            console.error(error);
        })
    },[])

    return (
        <>
            <div>
                {acceptedApplications.map(
                        (acceptedApplication, key) => 
                        <AcceptedApplicationCard 
                            setCurrentApplication={setCurrentApplication}
                            application={acceptedApplication} 
                            showContractGenerationModal={handleShowModal}
                            tasks={tasks || []}
                            handleChangeTask={handleChangeTask}
                            handleAddNewTask={handleAddNewTask}
                            handleDeleteTask={handleDeleteTask}
                            handleGenerateContract={handleGenerateContract}
                            handleCloseModal={handleCloseModal} 
                            showModal={showModal}
                            key={key}
                        />
                    )
                }
            </div>
            <Modal show={showModal} onHide={handleCloseModal}>
                <Modal.Header closeButton>
                    <Modal.Title>Tâches et Responsabilités</Modal.Title>
                </Modal.Header>
                    <Modal.Body>
                        {
                            tasks.map(
                                (task, key) => 
                                <ContractTask 
                                    key={key}
                                    index={key} 
                                    task={task} 
                                    handleChangeTask={handleChangeTask}
                                    handleDeleteTask={handleDeleteTask}
                                />
                            )
                        }
                    </Modal.Body>
                    <div className="text-center" >
                        <Button variant="info" className="mb-4 text-white" onClick={handleAddNewTask}>
                            <FontAwesomeIcon icon={faSquarePlus} size="xl" />
                        </Button>
                    </div>
                <Modal.Footer>
                    <button className={"btn btn-primary"} disabled={
                        tasks.filter(e => e.trim() !== "").length < tasks.length || tasks.length < 1} onClick={
                        () => {
                            
                            handleGenerateContract(currentApplication.studentId,currentApplication.offerId,tasks)
                        }}>
                        Soumettre
                    </button>
                </Modal.Footer>
            </Modal>
        </>
    );
}

export default AcceptedApplications;