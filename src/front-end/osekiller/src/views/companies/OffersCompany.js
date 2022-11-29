import React, { useContext, useEffect, useState } from "react";
import { OfferCard } from "../../components/OfferCard";
import { getOffersByCompany } from "../../services/CompanyService";
import { AuthenticatedUserContext } from "../../App";

const OffersCompany = () => {
    const authenticatedUser = useContext(
        AuthenticatedUserContext
    )?.authenticatedUser;
    const [offers, setOffers] = useState([]);

    useEffect(() => {
        getOffersByCompany(authenticatedUser?.id)
            .then(({ data }) => setOffers(data))
            .catch((err) => console.log(err));
    }, [authenticatedUser?.id]);

    return (
        <>
            <h2 className="text-center">Offres de stages</h2>
            {offers.map((offer, index) => (
                <OfferCard
                    key={index}
                    offer={offer}
                    redirectTo={"/internship-applications"}
                />
            ))}
        </>
    );
};

export default OffersCompany;
