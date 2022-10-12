import React, { useEffect, useState } from "react";
import { getOffers } from "../services/CompanyService";
import Offer from "../components/forms/Offer";

const ValidateOffer = () => {
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
                <Offer
                    key={index}
                    position={offer.position}
                    salary={offer.salary}
                    startDate={offer.startDate}
                    endDate={offer.endDate}
                />
            ))}
        </div>
    );
};

ValidateOffer.propTypes = {};

export default ValidateOffer;
