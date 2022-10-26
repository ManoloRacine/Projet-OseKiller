import LoadPdf from "../LoadPdf";
import { useContext, useEffect, useState } from "react";
import {
    getCv,
    getStudent,
    updateStudentSession,
} from "../../services/StudentService";
import { AuthenticatedUserContext } from "../../App";

const StudentDashboard = () => {
    const studentId = useContext(AuthenticatedUserContext)?.authenticatedUser
        ?.id;
    const [userPdf, setUserPdf] = useState("");
    const [studentInfo, setStudentInfo] = useState({});

    useEffect(() => {
        getStudent(studentId).then((response) => {
            setStudentInfo(response.data);
        });
        getCv(studentId).then((response) => {
            if (response.status !== 204) {
                const blob1 = new Blob([response.data], {
                    type: "application/pdf",
                });
                const data_url = window.URL.createObjectURL(blob1);
                setUserPdf(data_url);
            }
        });
    }, [studentId]);

    const isSessionOver = (year) => {
        if (Date.now() > new Date(year + "-05-31")) {
            return true;
        } else {
            return false;
        }
    };

    const updateSessionClick = () => {
        updateStudentSession(studentId).then((response) => {
            setStudentInfo(response.data);
        });
    };

    return (
        <div className="row">
            <div className="col-6">
                {studentInfo["sessionYear"] ? (
                    <h3>Session : {studentInfo["sessionYear"]}</h3>
                ) : null}
                {isSessionOver(studentInfo["sessionYear"]) ? (
                    <button
                        className="btn btn-primary"
                        onClick={() => updateSessionClick()}
                    >
                        S'inscrire à la prochaine session
                    </button>
                ) : null}
                {studentInfo["cvValidated"] && (
                    <h3 className="text-success">CV est valide</h3>
                )}
                {studentInfo["cvRejected"] && (
                    <h3 className="text-danger">CV n'est pas valide</h3>
                )}
                {studentInfo["cvPresent"] &&
                (studentInfo["cvRejected"] || studentInfo["cvValidated"]) ? (
                    <div>
                        <h4>Feedback :</h4>
                        <p>{studentInfo["feedback"]}</p>
                    </div>
                ) : studentInfo["cvPresent"] ? (
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

export default StudentDashboard;
