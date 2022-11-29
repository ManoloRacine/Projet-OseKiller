import React, { useContext, useEffect, useState } from "react";
import { AuthenticatedUserContext } from "../../App";
import { getInterns } from "../../services/CompanyService";
import { Link } from "react-router-dom";

const GetInterns = () => {
    const companyId = useContext(AuthenticatedUserContext)?.authenticatedUser
        ?.id;
    const [interns, setInterns] = useState([]);

    useEffect(() => {
        getInterns(companyId)
            .then((response) => {
                setInterns(response.data);
                console.log(response);
            })
            .catch((err) => {
                console.log(err);
            });
    }, []);

    return (
        <div>
            <h2 className="text-center">Évaluations des stagiaires</h2>
            {interns.map((intern, index) => (
                <div
                    key={index}
                    className={
                        "offer d-flex justify-content-evenly align-items-center text-white my-4 py-4 rounded"
                    }
                    style={{ backgroundColor: "#2C324C" }}
                    data-testid={"acceptedApplication-card"}
                >
                    <div>
                        <p className={"fs-4 text-decoration-underline"}>
                            Nom de l'étudiant
                        </p>
                        <p>{intern.studentName}</p>
                    </div>
                    <div>
                        <p className={"fs-4 text-decoration-underline"}>
                            Position
                        </p>
                        <p>{intern.position}</p>
                    </div>

                    <Link
                        to={"/evaluate-student"}
                        className={"btn btn-primary"}
                        state={{
                            studentName: intern.studentName,
                            companyName: intern.companyName,
                            contractId: intern.contractId,
                        }}
                    >
                        Évaluer
                    </Link>
                </div>
            ))}
        </div>
    );
};

export default GetInterns;
