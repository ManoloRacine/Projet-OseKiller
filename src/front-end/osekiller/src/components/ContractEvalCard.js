import { Link } from "react-router-dom";
import React from "react";
import { getSessionFromDate } from "../services/StudentService";

export const ContractEvalCard = ({ contractInfo }) => {
    return (
        <div
            className={
                "offer d-flex justify-content-evenly align-items-center text-white my-4 py-4 rounded"
            }
            style={{ backgroundColor: "#2C324C" }}
            data-testid={"offer-card"}
        >
            {contractInfo.companyName && (
                <div>
                    <p className={"fs-4 text-decoration-underline"}>
                        Nom de la compagnie
                    </p>
                    <p>{contractInfo.companyName}</p>
                </div>
            )}
            <div>
                <p className={"fs-4 text-decoration-underline"}>Position</p>
                <p>{contractInfo.position}</p>
            </div>
            <div>
                <p className={"fs-4 text-decoration-underline"}>Étudiant</p>
                <p>{contractInfo.studentName}</p>
            </div>

            <Link
                to={"/internship-evaluations"}
                className={"btn btn-primary"}
                state={{
                    contractInfo: contractInfo,
                }}
            >
                Détail
            </Link>
        </div>
    );
};
