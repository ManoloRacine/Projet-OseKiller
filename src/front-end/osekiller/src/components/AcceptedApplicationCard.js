

const AcceptedApplicationCard = ({application, showContractGenerationModal, handleAddNewTask, handleCloseModal, showModal, tasks, handleChangeTask, handleDeleteTask, handleGenerateContract}) => {
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
                    <p className={"fs-4 text-decoration-underline"}>Compagnie</p>
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
                {
                    application.contractId ? <p>Entente Généré</p> :
                    <button className={"btn btn-primary"} onClick={() => showContractGenerationModal(application)} >
                        Créer une entente de stage
                    </button> 
                }
                
            </div>
            
        </>
    );
}

export default AcceptedApplicationCard;