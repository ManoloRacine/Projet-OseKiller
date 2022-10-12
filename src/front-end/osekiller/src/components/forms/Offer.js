import React from "react";
import PropTypes from "prop-types";

const Offer = ({ position, salary, startDate, endDate }) => {
    return (
        <div
            className={
                "offer d-flex justify-content-evenly text-white my-4 py-4 rounded"
            }
            style={{ backgroundColor: "#2C324C" }}
        >
            <div>
                <p className={"fs-4 text-decoration-underline"}>Position</p>
                <p>{position}</p>
            </div>
            <div>
                <p className={"fs-4 text-decoration-underline"}>Salaire</p>
                <p>{salary}</p>
            </div>
            <div>
                <p className={"fs-4 text-decoration-underline"}>
                    Date de début
                </p>
                <p>{startDate}</p>
            </div>
            <div>
                <p className={"fs-4 text-decoration-underline"}>Date de fin</p>
                <p>{endDate}</p>
            </div>

            <div>
                <button className={"btn btn-success d-block mb-2"}>
                    Approuver
                </button>
                <button className={"btn btn-danger d-block"}>
                    Désapprouvé
                </button>
            </div>
        </div>
    );
};

Offer.propTypes = {
    position: PropTypes.string.isRequired,
    salary: PropTypes.number.isRequired,
    startDate: PropTypes.string.isRequired,
    endDate: PropTypes.string.isRequired,
};

export default Offer;
