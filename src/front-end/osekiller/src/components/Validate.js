import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCheck, faX } from "@fortawesome/free-solid-svg-icons";
import PropTypes from "prop-types";

const Validate = ({ pdf, feedBack, setFeedBack, validate }) => {
    return (
        <>
            <div className="row">
                <iframe
                    title="student-cv"
                    type="application/pdf"
                    src={pdf}
                    height="500px"
                    width="50%"
                ></iframe>
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
                onClick={() => validate(true)}
            >
                <FontAwesomeIcon icon={faCheck} className="me-2" />
                Approuver
            </button>
            <button
                className="btn btn-danger float-left"
                onClick={() => validate(false)}
            >
                <FontAwesomeIcon icon={faX} className="me-2" />
                Désapprouver
            </button>
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
