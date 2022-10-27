import { useContext } from "react";
import { AuthenticatedUserContext } from "../App";
import StudentDashboard from "../components/dashboard/student/StudentDashboard";
import CompanyDashboard from "../components/dashboard/company/CompanyDashboard";

const Dashboard = () => {
    const { authenticatedUser } = useContext(AuthenticatedUserContext);

    return (
        <div className="p-3">
            <h1>{`Bonjour, ${authenticatedUser.name}`}</h1>

            {authenticatedUser.role === "STUDENT" && <StudentDashboard />}
            {authenticatedUser.role === "COMPANY" && <CompanyDashboard />}
        </div>
    );
};

export default Dashboard;
