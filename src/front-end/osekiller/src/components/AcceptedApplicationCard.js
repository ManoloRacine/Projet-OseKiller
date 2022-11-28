import { useContext } from "react";
import { Button } from "react-bootstrap";
import { Link } from "react-router-dom";
import { AuthenticatedUserContext } from "../App";

const AcceptedApplicationCard = ({
    application,
    showContractGenerationModal,
    handleShowContractModalById,
    setSelectedApplicationIdx,
    handleShowReportModalById,
}) => {
    const userRole = useContext(AuthenticatedUserContext)?.authenticatedUser
        ?.role;
    return (
        <>
            <div
                className={
                    "offer d-flex justify-content-evenly align-items-center text-white my-4 py-4 rounded"
                }
                style={{ backgroundColor: "#2C324C" }}
                data-testid={"acceptedApplication-card"}
            >
                <div>
                    <p className={"fs-4 text-decoration-underline"}>
                        Compagnie
                    </p>
                    <p>{application.companyName}</p>
                </div>
                <div>
                    <p className={"fs-4 text-decoration-underline"}>Position</p>
                    <p>{application.position}</p>
                </div>
                <div>
                    <p className={"fs-4 text-decoration-underline"}>Étudiant</p>
                    <p>{application.studentName}</p>
                </div>
                {application.contractId && application.hasContractPdf && (
                    <Button
                        variant="info"
                        onClick={() => {
                            handleShowContractModalById(application.contractId);
                            setSelectedApplicationIdx();
                        }}
                    >
                        Voir l'entente de Stage
                    </Button>
                )}
                {
                    userRole === "MANAGER" && !application.hasContractPdf && (
                    
                    <button
                        className={"btn btn-primary"}
                        onClick={() => {
                            showContractGenerationModal(application);
                            setSelectedApplicationIdx();
                        }}
                    >
                        Créer une entente de stage
                    </button>
                    )
                }
                {
                    userRole === "MANAGER" && application.hasReport && (
                    <Button
                        variant="warning"
                        onClick={() => {
                            handleShowReportModalById(application.contractId);
                            setSelectedApplicationIdx();
                        }}
                    >
                        Voir le rapport de Stage
                    </Button>
                    )
                }
                {
                    userRole === "STUDENT" && application.contractId && application.hasContractPdf && (

                    <Link to={"/upload-report"} state={{contractId: application.contractId}}>
                        <button
                            className={"btn btn-primary"}
                        >
                            Soumettre un rapport de stage
                        </button>
                    </Link>
                    )
                }
            </div>
        </>
    );
};

export default AcceptedApplicationCard;
