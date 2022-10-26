import { useContext, useEffect, useState } from "react";
import { AuthenticatedUserContext } from "../App";
import { getCv, getStudent } from "../services/StudentService";
import StudentDashboard from "../components/dashboard/StudentDashboard";
import CompanyDashboard from "../components/dashboard/CompanyDashboard";

const Dashboard = () => {
    const [userPdf, setUserPdf] = useState("");
    const [studentInfo, setStudentInfo] = useState({});

    const { authenticatedUser } = useContext(AuthenticatedUserContext);

    useEffect(() => {
        if (authenticatedUser.role === "STUDENT") {
            getStudent(authenticatedUser.id).then((response) => {
                setStudentInfo(response.data);
            });
            getCv(authenticatedUser.id).then((response) => {
                if (response.status !== 204) {
                    const blob1 = new Blob([response.data], {
                        type: "application/pdf",
                    });
                    const data_url = window.URL.createObjectURL(blob1);
                    setUserPdf(data_url);
                }
            });
        }
    }, [authenticatedUser.id, authenticatedUser.role]);

    return (
        <div className="p-3">
            <h1>{`Bonjour, ${authenticatedUser.name}`}</h1>

            {authenticatedUser.role === "STUDENT" && (
                <StudentDashboard
                    isCvValidated={studentInfo["cvValidated"]}
                    isCvRejected={studentInfo["cvRejected"]}
                    isCvPresent={studentInfo["cvPresent"]}
                    feedback={studentInfo["feedback"]}
                    userPdf={userPdf}
                />
            )}
            {authenticatedUser.role === "COMPANY" && <CompanyDashboard />}
        </div>
    );
};

export default Dashboard;
