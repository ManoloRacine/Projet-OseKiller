import { useEffect, useState } from "react";
import { getOffers } from "../../services/OfferService";
import { OfferCard } from "../../components/OfferCard";

const OffersManager = () => {
    const [offers, setOffers] = useState([]);

    useEffect(() => {
        getOffers(false)
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
                <OfferCard
                    key={index}
                    offer={offer}
                    redirectTo={"/validate-offer"}
                />
            ))}
        </>
    );
};

OffersManager.propTypes = {};

export default OffersManager;
