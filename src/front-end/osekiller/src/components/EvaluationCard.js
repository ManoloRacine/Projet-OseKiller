import { Link } from "react-router-dom";
import React from "react";
import { getSessionFromDate } from "../services/StudentService";

export const EvaluationCard = ({ contract, redirectTo }) => {
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
            <div>
                <p className={"fs-4 text-decoration-underline"}>
                    Date de début
                </p>
                <p>{contract.startDate}</p>
            </div>
            <div>
                <p className={"fs-4 text-decoration-underline"}>Date de fin</p>
                <p>{contract.endDate}</p>
            </div>
            <div>
                <p className={"fs-4 text-decoration-underline"}>Session</p>
                <p>{getSessionFromDate(new Date(contract.startDate))}</p>
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
