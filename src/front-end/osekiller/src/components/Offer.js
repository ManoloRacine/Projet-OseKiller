import React from "react";
import PropTypes from "prop-types";
import { Link } from "react-router-dom";

const Offer = ({
    companyName,
    position,
    salary,
    startDate,
    endDate,
    companyId,
    offerId,
}) => {
    return (
        <div
            className={
                "offer d-flex justify-content-evenly align-items-center text-white my-4 py-4 rounded"
            }
            style={{ backgroundColor: "#2C324C" }}
        >
            <div>
                <p className={"fs-4 text-decoration-underline"}>
                    Nom de la compagnie
                </p>
                <p>{companyName}</p>
            </div>
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

            <Link
                to={"/validate-offer"}
                className={"btn btn-primary"}
                state={{ companyId: companyId, offerId: offerId }}
            >
                Détail
            </Link>
        </div>
    );
};

Offer.propTypes = {
    companyName: PropTypes.string.isRequired,
    position: PropTypes.string.isRequired,
    salary: PropTypes.number.isRequired,
    startDate: PropTypes.string.isRequired,
    endDate: PropTypes.string.isRequired,
    companyId: PropTypes.number.isRequired,
    offerId: PropTypes.number.isRequired,
};

export default Offer;
