import React, { useEffect, useState } from "react";
import PropTypes from "prop-types";
import { getOffers } from "../services/CompanyService";

const ValidateOffer = (props) => {
    const [offers, setOffers] = useState([]);

    useEffect(() => {
        getOffers(12) // Temporaire, en attendant d'avoir un endpoint pour get toutes les offres de stages, de toutes les commpagnies
            .then(({ data }) => {
                setOffers(data);
            })
            .catch((err) => {
                console.log(err);
            });
    }, []);

    return (
        <div>
            {offers.map((offer, index) => (
                <div key={index}>
                    <p>Position: {offer.position}</p>
                    <p>Salaire: {offer.salary}</p>
                    <p>Date de début: {offer.startDate}</p>
                    <p>Date de fin: {offer.endDate}</p>
                </div>
            ))}
        </div>
    );
};

ValidateOffer.propTypes = {};

export default ValidateOffer;
