import { Link } from "react-router-dom";
import React from "react";

export const StudentEvaluationCard = ({ contract, redirectTo }) => {
    return (
        <div
            className={
                "offer d-flex justify-content-evenly align-items-center text-white my-4 py-4 rounded"
            }
            style={{ backgroundColor: "#2C324C" }}
            data-testid={"offer-card"}
        >
            {contract.companyName && (
                <div>
                    <p className={"fs-4 text-decoration-underline"}>
                        Nom de la compagnie
                    </p>
                    <p>{contract.companyName}</p>
                </div>
            )}
            {contract.companyName && (
                <div>
                    <p className={"fs-4 text-decoration-underline"}>
                        Nom de l'étudiant
                    </p>
                    <p>{contract.studentName}</p>
                </div>
            )}
            <div>
                <p className={"fs-4 text-decoration-underline"}>Position</p>
                <p>{contract.position}</p>
            </div>

            <Link
                to={redirectTo}
                className={"btn btn-primary"}
                state={{
                    contractId: contract.contractId,
                }}
            >
                Détail
            </Link>
        </div>
    );
};
