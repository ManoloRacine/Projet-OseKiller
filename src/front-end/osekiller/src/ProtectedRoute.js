import { Navigate, Outlet } from "react-router-dom";

const ProtectedRoute = () => {
    if (localStorage.getItem("accessToken")) {
        return <Navigate to={"/dashboard"} />;
    }
    return <Outlet />;
};

export default ProtectedRoute;
