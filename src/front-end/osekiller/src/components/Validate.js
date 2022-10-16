import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCheck, faX } from "@fortawesome/free-solid-svg-icons";
import PropTypes from "prop-types";
import { useState } from "react";
import ErrorMessage from "./ErrorMessage";
import LoadPdf from "./LoadPdf";

const Validate = ({ pdf, feedBack, setFeedBack, validate }) => {
    const [errorMessage, setErrorMessage] = useState("");

    const handleValidate = (isValid) => {
        if (feedBack === "") {
            setErrorMessage("Le feedback ne doit pas être vide");
        } else {
            validate(isValid);
        }
    };

    return (
        <>
            <div className="row">
                <LoadPdf
                    src={pdf}
                    width={"50%"}
                    title={"student-cv"}
                    type={"application/pdf"}
                    height={"500px"}
                />
            </div>
            <div className="input-group py-3">
                <span className="input-group-text">Feedback</span>
                <textarea
                    className="form-control"
                    value={feedBack}
                    onChange={setFeedBack}
                ></textarea>
            </div>
            <button
                className="btn btn-success float-left"
                onClick={() => handleValidate(true)}
            >
                <FontAwesomeIcon icon={faCheck} className="me-2" />
                Approuver
            </button>
            <button
                className="btn btn-danger float-left"
                onClick={() => handleValidate(false)}
            >
                <FontAwesomeIcon icon={faX} className="me-2" />
                Désapprouver
            </button>
            {errorMessage && (
                <ErrorMessage message={errorMessage} severity="error" />
            )}
        </>
    );
};

Validate.propTypes = {
    pdf: PropTypes.string.isRequired,
    feedBack: PropTypes.string.isRequired,
    setFeedBack: PropTypes.func.isRequired,
    validate: PropTypes.func.isRequired,
};

export default Validate;
