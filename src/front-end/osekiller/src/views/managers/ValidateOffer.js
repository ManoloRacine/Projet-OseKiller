import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { getOfferPdf, validateOffer } from "../../services/OfferService";
import Validate from "../../components/Validate";

const ValidateOffer = () => {
    const [pdf, setPdf] = useState("");
    const navigate = useNavigate();
    const location = useLocation();
    const { companyId, offerId } = location.state;
    const [feedBack, setFeedback] = useState("");

    const handleValidate = (isValid) => {
        const dto = {
            validation: isValid,
            feedBack: feedBack,
        };

        validateOffer(companyId, offerId, dto)
            .then(() => navigate("/dashboard"))
            .catch((err) => console.log(err));
    };

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

    return (
        <Validate
            feedBack={feedBack}
            pdf={pdf}
            setFeedBack={({ target }) => setFeedback(target.value)}
            validate={handleValidate}
        />
    );
};

export default ValidateOffer;
