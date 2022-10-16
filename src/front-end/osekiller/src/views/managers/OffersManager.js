import { useEffect, useState } from "react";
import { getOffers } from "../../services/OfferService";
import Offer from "../../components/Offer";

const OffersManager = () => {
    const [offers, setOffers] = useState([]);

    useEffect(() => {
        getOffers()
            .then(({ data }) => {
                setOffers(data);
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
                    companyName={offer.companyName}
                    position={offer.position}
                    salary={offer.salary}
                    startDate={offer.startDate}
                    endDate={offer.endDate}
                    companyId={offer.companyId}
                    offerId={offer.offerId}
                />
            ))}
        </>
    );
};

OffersManager.propTypes = {};

export default OffersManager;
