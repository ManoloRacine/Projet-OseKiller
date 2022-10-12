import React, { useEffect, useState } from "react";
import { getOffer } from "../services/CompanyService";
import { getOffers } from "../services/OfferService";
import Offer from "../components/Offer";

const ValidateOffer = () => {
    const [offers, setOffers] = useState([]);
    const [pdf, setPdf] = useState("");

    useEffect(() => {
        getOffers() // Temporaire, en attendant d'avoir un endpoint pour get toutes les offres de stages, de toutes les commpagnies
            .then((response) => {
                console.log(response);
                setOffers(response.data);
            })
            .catch((err) => {
                console.log(err);
            });
    }, []);

    const getPdf = (companyId, offerId) => {
        // Ne fonctionne pas pour l'instant
        getOffer(companyId, offerId)
            .then((response) => {
                console.log(response);
                //response.data = new FormData();
                console.log(response.data);

                var blob1 = new Blob([response.data], {
                    type: "application/json",
                });

                var data_url = window.URL.createObjectURL(blob1);
                setPdf(data_url);
                console.log(blob1);
                console.log(localStorage.getItem("accessToken"));
            })
            .catch((err) => {
                console.log(err);
            });
    };

    return (
        <div>
            {offers.map((offer, index) => (
                <Offer
                    key={index}
                    position={offer.position}
                    salary={offer.salary}
                    startDate={offer.startDate}
                    endDate={offer.endDate}
                    getPdf={() => getPdf(12, offer.offerId)}
                />
            ))}
            <div className="row">
                <iframe
                    title="student-cv"
                    type="application/pdf"
                    src={pdf}
                    height="500px"
                    width="50%"
                ></iframe>
            </div>
        </div>
    );
};

ValidateOffer.propTypes = {};

export default ValidateOffer;
