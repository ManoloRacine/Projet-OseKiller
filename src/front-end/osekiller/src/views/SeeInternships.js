import { useEffect, useState } from "react";
import { getOffers } from "../services/OfferService";
import { OfferCard } from "../components/OfferCard";

const SeeInternships = () => {
    const [offers, setOffers] = useState([]) ;

    useEffect(() => {
        getOffers(true).then((response) => {
            setOffers(response.data) ;
        })
    }, []) ;

    return (
        <div className="row">
            <div className="col-2"></div>
            <div className="col-8">{offers.map((offer, index) => (<OfferCard  offer={offer} ></OfferCard>))}</div>
            <div className="col-2"></div>
        </div>
    )
}

export default SeeInternships ;