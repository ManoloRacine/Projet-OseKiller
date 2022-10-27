import StudentCv from "./StudentCv";
import StudentConvocation from "./StudentConvocation";
import React, { useContext, useEffect, useState } from "react";
import { confirmInterviewDate } from "../../../services/InterviewService";
import { getStudentConvocations } from "../../../services/StudentService";
import { AuthenticatedUserContext } from "../../../App";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCalendarDay } from "@fortawesome/free-solid-svg-icons";

const StudentDashboard = () => {
    const studentId = useContext(AuthenticatedUserContext)?.authenticatedUser
        ?.id;
    const [convocations, setConvocation] = useState([]);

    useEffect(() => {
        getStudentConvocations(studentId)
            .then((response) => {
                console.log(response);
                setConvocation(response.data);
            })
            .catch((err) => {
                console.log(err);
            });
    });

    const handleConfirmInterviewDate = (interviewId, confirmDate) => {
        confirmInterviewDate(interviewId, confirmDate)
            .then((response) => {
                console.log(response);
                setConvocation(response.data);
            })
            .catch((err) => {
                console.log(err);
            });
    };

    return (
        <div className={"mt-3 row"}>
            <div className={"col-6"}>
                <h2>Mon CV</h2>
                <StudentCv />
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
                    convocations.map((convocation) => (
                        <StudentConvocation
                            key={convocation.id}
                            convocation={convocation}
                            confirmInterviewDate={handleConfirmInterviewDate}
                        />
                    ))
                )}
            </div>
        </div>
    );
};

export default StudentDashboard;
