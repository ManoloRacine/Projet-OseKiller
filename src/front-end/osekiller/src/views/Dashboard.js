import { useContext, useEffect, useState } from "react";
import { AuthenticatedUserContext } from "../App";
import { getCv, getStudent } from "../services/StudentService";
import StudentDashboard from "../components/dashboard/StudentDashboard";
import CompanyDashboard from "../components/dashboard/CompanyDashboard";

const Dashboard = () => {
    const [userPdf, setUserPdf] = useState("");
    const [studentInfo, setStudentInfo] = useState({});
    const [offers, setOffers] = useState([
        {
            position: "Développeur Java",
            salary: "21",
            startDate: "2022-10-22",
            endDate: "2022-11-22",
            isValidated: true,
            isAccepted: true,
        },
        {
            position: "Développeur Python",
            salary: "22",
            startDate: "2023-01-23",
            endDate: "2022-02-23",
            isValidated: true,
            isAccepted: false,
        },
        {
            position: "Développeur Cobolt",
            salary: "23",
            startDate: "2023-02-23",
            endDate: "2023-03-23",
            isValidated: false,
            isAccepted: false,
        },
    ]); // À enlever lorsque l'API sera fait

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
            {authenticatedUser.role === "COMPANY" && (
                <CompanyDashboard offers={offers} />
            )}
        </div>
    );
};

export default Dashboard;
