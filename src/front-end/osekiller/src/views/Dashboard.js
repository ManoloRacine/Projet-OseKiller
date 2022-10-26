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
                    var blob1 = new Blob([response.data], {
                        type: "application/pdf",
                    });
                    var data_url = window.URL.createObjectURL(blob1);
                    setUserPdf(data_url);
                }
            });
        }
    }, [authenticatedUser.id, authenticatedUser.role]);

    return (
        <div className="p-3">
            <h1>{`Bonjour, ${authenticatedUser.name}`}</h1>

            {authenticatedUser.role === "STUDENT" && <StudentDashboard />}
            {authenticatedUser.role === "COMPANY" && <CompanyDashboard />}
        </div>
    );
};

export default Dashboard;
