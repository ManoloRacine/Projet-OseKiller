import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { getCv, validateCv } from "../services/StudentService";
import Validate from "../components/Validate";

const ValidateCv = () => {
    const [pdf, setPdf] = useState("");
    const navigate = useNavigate();
    const location = useLocation();
    const { studentId } = location.state;
    const [feedBack, setFeedback] = useState("");

    const handleValidate = (isValid) => {
        validateCv(studentId, isValid, feedBack)
            .then(() => navigate("/dashboard"))
            .catch((err) => console.log(err));
    };

    useEffect(() => {
        getCv(studentId).then((response) => {
            const blob1 = new Blob([response.data], {
                type: "application/pdf",
            });
            const data_url = window.URL.createObjectURL(blob1);
            setPdf(data_url);
        });
    }, [studentId]);

    return (
        <Validate
            feedBack={feedBack}
            pdf={pdf}
            setFeedBack={({ target }) => setFeedback(target.value)}
            validate={handleValidate}
        />
    );
};

export default ValidateCv;
