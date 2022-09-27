import { Navigate, Outlet } from "react-router-dom";

const ProtectedRoute = ({ children }) => {
    const token = localStorage.getItem("accessToken");

    if (!token) {
        return <Navigate to={"/"} replace />;
    }
    return children;
};

export default ProtectedRoute;
