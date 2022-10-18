import React, { useContext, useEffect, useState } from "react";
import { OfferCard } from "../../components/OfferCard";
import { getOffersByCompany } from "../../services/CompanyService";
import { AuthenticatedUserContext } from "../../App";

const InternshipApplication = () => {
    const { authenticatedUser } = useContext(AuthenticatedUserContext);
    const [offers, setOffers] = useState([]);

    useEffect(() => {
        getOffersByCompany(authenticatedUser.id)
            .then(({ data }) => setOffers(data))
            .catch((err) => console.log(err));
    }, []);

    return (
        <>
            {offers.map((offer, index) => (
                <OfferCard key={index} offer={offer} redirectTo={""} />
            ))}
        </>
    );
};

export default InternshipApplication;
