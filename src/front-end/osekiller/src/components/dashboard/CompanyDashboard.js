import React from "react";
import PropTypes from "prop-types";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
    faCircleCheck,
    faCircleXmark,
    faClock,
} from "@fortawesome/free-solid-svg-icons";
import { Link } from "react-router-dom";

const CompanyDashboard = ({ offers }) => {
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
                        {offer.isValidated && offer.isAccepted ? (
                            <div className={"d-flex flex-column"}>
                                <FontAwesomeIcon
                                    icon={faCircleCheck}
                                    className="fa-2x text-success"
                                />
                                <p>Valide</p>
                            </div>
                        ) : offer.isValidated && !offer.isAccepted ? (
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

                    {!(offer.isValidated && offer.isAccepted) && (
                        <Link
                            to={"/upload-internship"}
                            className={"btn btn-primary"}
                            state={{
                                position: offer.position,
                                salary: offer.salary,
                                startDate: offer.startDate,
                                endDate: offer.endDate
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

CompanyDashboard.propTypes = {
    offers: PropTypes.array.isRequired,
};

export default CompanyDashboard;
