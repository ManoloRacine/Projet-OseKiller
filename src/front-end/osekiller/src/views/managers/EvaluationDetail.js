import { useEffect, useState } from "react";
import LoadPdf from "../../components/LoadPdf";
import { useLocation, useNavigate } from "react-router-dom";
import { getEvaluationPdf } from "../../services/ManagerService";

const EvaluationDetail = () => {
    const [pdf, setPdf] = useState("");

    const navigate = useNavigate();
    const location = useLocation();
    const { contractId } = location.state;

    useEffect(() => {
        getEvaluationPdf(contractId)
            .then((response) => {
                const blob1 = new Blob([response.data], {
                    type: "application/pdf",
                });
                const data_url = window.URL.createObjectURL(blob1);
                setPdf(data_url);
            })
            .catch((err) => console.log(err));
    }, []);

    return (
        <div className="row p-5">
            <LoadPdf
                src={pdf}
                width={"50%"}
                title={"student-cv"}
                type={"application/pdf"}
                height={"800px"}
            />
        </div>
    );
};

export default EvaluationDetail;
