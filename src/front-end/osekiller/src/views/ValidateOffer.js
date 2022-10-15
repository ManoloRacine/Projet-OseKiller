import React, { useEffect, useState } from "react";
import { getOffers } from "../services/OfferService";
import Offer from "../components/Offer";

const ValidateOffer = () => {
    const [offers, setOffers] = useState([]);
    const [pdf, setPdf] = useState("");

    useEffect(() => {
        getOffers()
            .then((response) => {
                console.log(response);
                setOffers(response.data);
            })
            .catch((err) => {
                console.log(err);
            });
    }, []);

    return (
        <>
            {offers.map((offer, index) => (
                <Offer
                    key={index}
                    position={offer.position}
                    salary={offer.salary}
                    startDate={offer.startDate}
                    endDate={offer.endDate}
                />
            ))}
        </>
    );
};

ValidateOffer.propTypes = {};

export default ValidateOffer;
