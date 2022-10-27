import StudentCv from "./StudentCv";
import StudentConvocation from "./StudentConvocation";
import { useState } from "react";
import { confirmInterviewDate } from "../../../services/InterviewService";

const StudentDashboard = () => {
    const [convocations, setConvocation] = useState([
        {
            id: 1,
            position: "Développeur Java",
            date1: "2022-10-23",
            date2: "2022-10-24",
            date3: "2022-10-25",
        },
        {
            id: 2,
            position: "Développeur Python",
            date1: "2022-11-04",
            date2: "2022-11-07",
            date3: "2022-11-15",
        },
    ]);

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
                {convocations.map((convocation) => (
                    <StudentConvocation
                        key={convocation.id}
                        convocation={convocation}
                        confirmInterviewDate={handleConfirmInterviewDate}
                    />
                ))}
            </div>
        </div>
    );
};

export default StudentDashboard;
