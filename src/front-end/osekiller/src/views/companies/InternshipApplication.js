import React, { useEffect, useState } from "react";
import { getOffers } from "../../services/OfferService";
import { OfferCard } from "../../components/OfferCard";

const InternshipApplication = () => {
    const [offers, setOffers] = useState([]);

    useEffect(() => {
        getOffers(true).then((response) => {
            setOffers(response.data);
        });
    }, []);

    return (
        <>
            {offers.map((offer, index) => (
                // Changer le redirectTo dans la prochaine story
                <OfferCard key={index} offer={offer} redirectTo={""} />
            ))}
        </>
    );
};

export default InternshipApplication;
