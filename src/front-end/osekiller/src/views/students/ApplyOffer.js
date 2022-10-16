import { useEffect, useState } from "react";
import { getOfferPdf } from "../../services/OfferService";
import { useLocation } from "react-router-dom";
import LoadPdf from "../../components/LoadPdf";

const ApplyOffer = () => {
    const [pdf, setPdf] = useState("");
    const location = useLocation();
    const { companyId, offerId } = location.state;

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

    const handleApplyOffer = () => {
        console.log("Apply offer");
    };

    return (
        <div
            className={"pt-3 ps-3 m-4 rounded"}
            style={{ backgroundColor: "#2C324C" }}
        >
            <LoadPdf
                src={pdf}
                width={"75%"}
                title={"apply-offer"}
                type={"application/pdf"}
                height={"500px"}
            />
            <button
                className={"btn mb-4 ms-2"}
                style={{ backgroundColor: "#ee7600" }}
                onClick={handleApplyOffer}
            >
                Appliquer
            </button>
        </div>
    );
};

export default ApplyOffer;
