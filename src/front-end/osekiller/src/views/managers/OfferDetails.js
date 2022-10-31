import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { getOfferPdf, validateOffer } from "../../services/OfferService";
import Validate from "../../components/Validate";
import OfferDetailsNoValidate from "../../components/OfferDetailsNoValidate";

const OfferDetails = () => {
    const [pdf, setPdf] = useState("");
    const navigate = useNavigate();
    const location = useLocation();
    const { companyId, offerId, startDate } = location.state;

    useEffect(() => {
        getOfferPdf(companyId, offerId)
            .then((response) => {
                const blob1 = new Blob([response.data], {
                    type: "application/pdf",
                });
                const data_url = window.URL.createObjectURL(blob1);
                setPdf(data_url);
            })
            .catch((err) => console.log(err));
    }, [companyId, offerId]);

    return <OfferDetailsNoValidate pdf={pdf} />;
};

export default OfferDetails;
