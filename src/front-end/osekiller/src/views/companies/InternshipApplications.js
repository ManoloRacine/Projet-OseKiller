import React, { useContext, useEffect, useState } from "react";
import { getApplicantsByOffer } from "../../services/CompanyService";
import { OfferCard } from "../../components/OfferCard";
import { useLocation } from "react-router-dom";
import { AuthenticatedUserContext } from "../../App";

const InternshipApplications = () => {
    const { authenticatedUser } = useContext(AuthenticatedUserContext);
    const [applicants, setApplicants] = useState([]);
    const location = useLocation();
    const { state } = location;
    const companyId = authenticatedUser.id;
    const offerId = state?.offerId;

    useEffect(() => {
        getApplicantsByOffer(companyId, offerId)
            .then((response) => {
                console.log(response);
                setApplicants(response.data);
            })
            .catch((err) => console.log(err));
    }, []);

    return (
        <main>
            {applicants.map((offer, index) => (
                <OfferCard
                    key={index}
                    offer={offer}
                    redirectTo={"/internship-applications"}
                />
            ))}
        </main>
    );
};

export default InternshipApplications;
