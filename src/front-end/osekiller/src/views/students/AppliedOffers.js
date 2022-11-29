import { useContext, useEffect, useState } from "react";
import { OfferCard } from "../../components/OfferCard";
import { AuthenticatedUserContext } from "../../App";
import { getOffersStudent } from "../../services/StudentService";

const AppliedOffers = () => {
    const [offers, setOffers] = useState([]);
    const authenticatedUser = useContext(
        AuthenticatedUserContext
    )?.authenticatedUser;

    useEffect(() => {
        getOffersStudent(authenticatedUser?.id).then((response) => {
            console.log(response);
            setOffers(response.data);
        });
    }, [authenticatedUser?.id]);

    return (
        <div className="row">
            <h2 className={"text-center"}>Offres appliquées</h2>
            <div className="col-12">
                {offers.map((offer, index) => (
                    <OfferCard key={index} offer={offer}></OfferCard>
                ))}
            </div>
        </div>
    );
};

export default AppliedOffers;
