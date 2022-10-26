import { useEffect, useState, useContext } from "react";
import { getOffers } from "../../services/OfferService";
import { OfferCard } from "../../components/OfferCard";
import { getStudent } from "../../services/StudentService";
import { AuthenticatedUserContext } from "../../App";

const SeeInternships = () => {
    const [offers, setOffers] = useState([]);
    const [session, setSession] = useState("");
    const { authenticatedUser } = useContext(AuthenticatedUserContext);

    useEffect(() => {
        getStudent(authenticatedUser.id).then((response) => {
            setSession(response.data.sessionYear);
            let sessionYear = response.data.sessionYear;
            console.log(sessionYear);
            getOffers(true, sessionYear).then((response) => {
                console.log(response.data);
                setOffers(response.data);
            });
        });
    }, []);

    return (
        <div className="row">
            <h2 align="center">Offres de stage pour l'hiver {session}</h2>
            <div className="col-12">
                {offers.map((offer, index) => (
                    <OfferCard
                        key={index}
                        offer={offer}
                        redirectTo={"/apply-offer"}
                    ></OfferCard>
                ))}
            </div>
        </div>
    );
};

export default SeeInternships;
