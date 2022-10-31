import React, { useContext, useEffect, useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
    faCircleCheck,
    faCircleXmark,
    faClock,
} from "@fortawesome/free-solid-svg-icons";
import { Link } from "react-router-dom";
import { AuthenticatedUserContext } from "../../App";
import { getOffersByCompany } from "../../services/CompanyService";

const CompanyDashboard = () => {
    const companyId = useContext(AuthenticatedUserContext)?.authenticatedUser
        ?.id;
    const [offers, setOffers] = useState([]);

    useEffect(() => {
        getOffersByCompany(companyId)
            .then((response) => {
                console.log(response);
                setOffers(response.data);
            })
            .catch((err) => {
                console.log(err);
            });
    }, []);

    return (
        <div className={"mt-3 col-4"} style={{ border: "3px solid #2C324C" }}>
            <h1>Mes offres de stages</h1>
            {offers.map((offer, index) => (
                <div
                    key={index}
                    className={
                        "d-flex justify-content-around text-white m-2 rounded align-items-center"
                    }
                    style={{ backgroundColor: "#2C324C" }}
                >
                    <div>
                        <p className={"fs-4 text-decoration-underline"}>
                            Nom du poste
                        </p>
                        <p>{offer.position}</p>
                    </div>

                    <div className={"d-flex flex-column align-items-center"}>
                        <p className={"fs-4 text-decoration-underline"}>État</p>
                        {offer.accepted ? (
                            <div className={"d-flex flex-column"}>
                                <FontAwesomeIcon
                                    icon={faCircleCheck}
                                    className="fa-2x text-success"
                                />
                                <p>Valide</p>
                            </div>
                        ) : offer.feedback && !offer.accepted ? (
                            <div className={"d-flex flex-column"}>
                                <FontAwesomeIcon
                                    icon={faCircleXmark}
                                    className="fa-2x text-danger"
                                />
                                <p>Invalide</p>
                            </div>
                        ) : (
                            <div className={"d-flex flex-column"}>
                                <FontAwesomeIcon
                                    icon={faClock}
                                    className="fa-2x text-warning"
                                />
                                <p>Non validé</p>
                            </div>
                        )}
                    </div>

                    {!(offer.accepted) && (
                        <Link
                            to={"/upload-internship"}
                            className={"btn btn-primary"}
                            state={{
                                offerId: offer.offerId,
                                position: offer.position,
                                salary: offer.salary,
                                startDate: offer.startDate,
                                endDate: offer.endDate,
                            }}
                        >
                            Modifier
                        </Link>
                    )}
                </div>
            ))}
        </div>
    );
};

export default CompanyDashboard;
