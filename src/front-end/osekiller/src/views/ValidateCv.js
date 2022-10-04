import axios from "../api/axios";
import { useEffect, useState } from "react";
import { faCheck, faCross, faX } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { navigate, useLocation, useNavigate } from "react-router-dom";
import { validateCv, invalidateCv, getCV} from "../services/CvServices"


const ValidateCv = () => {
    const [pdf, setPdf] = useState("");
    const navigate = useNavigate();
    const location = useLocation();
    const { studentId } = location.state;
    const [feedBack, setFeedback] = useState("");

    const handleInputFeedback = event => {
        setFeedback(event.target.value)
    }

    useEffect(() => {
        getCV(studentId)
        .then((response) => {
            console.log("test") ;
            //let data_url = URL.createObjectURL(response.data) ;
            let link = document.createElement('a');
            var blob1 = new Blob([response.data], {type: "application/pdf"});
            var blob2 = new File([blob1], "1.pdf", {type: "application/pdf"});
            
            var data_url = window.URL.createObjectURL(blob1) ;
            setPdf(data_url) ;
            console.log(pdf) ;
            console.log(blob1) ;
            console.log(localStorage.getItem("accessToken")) ;
        })
    }, [])

    return (
        <div>
            <div className="row">
                    <iframe type="application/pdf" src={pdf} height="500px" width="50%"></iframe>
            </div>
            <div className="input-group py-3">
                <span className="input-group-text">Feedback</span>
                <textarea className="form-control" onChange={handleInputFeedback}></textarea>
            </div>
            <button
                className="btn btn-success float-left"
                onClick={() => {validateCv(studentId, feedBack); navigate("/dashboard")}}
            >
                <FontAwesomeIcon icon={faCheck} className="me-2" />
                Approuver
            </button>
            <button
                className="btn btn-danger float-left"
                onClick={() => {invalidateCv(studentId, feedBack); navigate("/dashboard")}}
            >
                <FontAwesomeIcon icon={faX} className="me-2" />
                Désapprouver
            </button>
        </div>
        
        
    )
    
}

export default ValidateCv;