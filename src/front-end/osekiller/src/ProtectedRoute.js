import { Navigate, Outlet } from "react-router-dom";

const ProtectedRoute = ({ children }) => {
    if (localStorage.getItem("accessToken")) {
        return <Navigate to={"/dashboard"} />;
    }
    return <Outlet />;
};

export default ProtectedRoute;
