import PropTypes from "prop-types";
import LoadPdf from "../LoadPdf";

const StudentDashboard = ({
    isCvValidated,
    isCvRejected,
    isCvPresent,
    feedback,
    userPdf,
}) => {
    return (
        <div className="row">
            <div className="col-6">
                {isCvValidated && (
                    <h3 className="text-success">CV est valide</h3>
                )}
                {isCvRejected && (
                    <h3 className="text-danger">CV n'est pas valide</h3>
                )}
                {isCvPresent && (isCvRejected || isCvValidated) ? (
                    <div>
                        <h4>Feedback :</h4>
                        <p>{feedback}</p>
                    </div>
                ) : isCvPresent ? (
                    <h4 className="text-warning">
                        CV en attente de validation
                    </h4>
                ) : null}
            </div>
            <div className="col-6">
                {userPdf !== "" ? (
                    <LoadPdf
                        src={userPdf}
                        width={"100%"}
                        title={"studentCv"}
                        type={"application/json"}
                        height={"600px"}
                    />
                ) : (
                    <p>Vous n'avez pas téléversé de CV</p>
                )}
            </div>
        </div>
    );
};

StudentDashboard.propTypes = {
    isCvValidated: PropTypes.bool.isRequired,
    isCvRejected: PropTypes.bool.isRequired,
    isCvPresent: PropTypes.bool.isRequired,
    feedback: PropTypes.string.isRequired,
    userPdf: PropTypes.string.isRequired,
};

export default StudentDashboard;
