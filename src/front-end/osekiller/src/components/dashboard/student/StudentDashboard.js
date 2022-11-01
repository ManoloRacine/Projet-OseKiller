import StudentCv from "./StudentCv";
import StudentConvocation from "./StudentConvocation";
import React, { useContext, useEffect, useState } from "react";
import { confirmInterviewDate } from "../../../services/InterviewService";
import {
    getStudentConvocations,
    updateStudentSession,
    getStudent,
} from "../../../services/StudentService";
import { AuthenticatedUserContext } from "../../../App";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCalendarDay } from "@fortawesome/free-solid-svg-icons";
import ErrorMessage from "../../ErrorMessage";

const StudentDashboard = () => {
    const studentId = useContext(AuthenticatedUserContext)?.authenticatedUser
        ?.id;
    const [convocations, setConvocation] = useState([]);
    const [message, setMessage] = useState("");

    const [studentInfo, setStudentInfo] = useState({});

    useEffect(() => {
        getStudent(studentId).then((response) => {
            setStudentInfo(response.data);
        });
        getStudentConvocations(studentId)
            .then((response) => {
                setConvocation(response.data);
            })
            .catch((err) => {
                console.log(err);
            });
    }, [convocations]);

    const handleConfirmInterviewDate = (interviewId, confirmDate) => {
        confirmInterviewDate(interviewId, studentId, confirmDate)
            .then(() => {
                setConvocation(convocations);
                setMessage("Date de l'entrevue confirmée avec succès!");
            })
            .catch((err) => {
                console.log(err);
            });
    };

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
        <div className="row mt-3">
            <div className="col-3">
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
            </div>
            <div className={"mt-3 row"}>
                <div className={"col-6"}>
                    <h2>Mes Infos</h2>
                    <StudentCv
                        studentInfo={studentInfo}
                        studentId={studentId}
                    />
                </div>
                <div className={"col-6"}>
                    <h2>Mes convocations</h2>
                    {convocations.length === 0 ? (
                        <div
                            className={
                                "h-75 text-white d-flex flex-column align-items-center justify-content-center rounded"
                            }
                            style={{ backgroundColor: "#2C324C" }}
                        >
                            <FontAwesomeIcon
                                icon={faCalendarDay}
                                className="fa-4x mb-4"
                            />
                            <p>Aucune convocation pour l'instant</p>
                        </div>
                    ) : (
                        convocations.map((convocation, index) => (
                            <StudentConvocation
                                key={index}
                                convocation={convocation}
                                confirmInterviewDate={
                                    handleConfirmInterviewDate
                                }
                            />
                        ))
                    )}
                </div>
                <div>
                    {message && (
                        <ErrorMessage message={message} severity={"success"} />
                    )}
                </div>
            </div>
        </div>
    );
};

export default StudentDashboard;
