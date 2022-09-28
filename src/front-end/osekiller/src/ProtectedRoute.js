import { Navigate } from "react-router-dom";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { pingToken } from "../src/services/AuthService";

const ProtectedRoute = ({ children }) => {
    const navigate = useNavigate();

    useEffect(() => {
        pingToken()
            .then(() => {
                return children;
            })
            .catch((err) => {
                if (err.response.status === 403) {
                    console.log("Token expiré");
                    navigate("/");
                }
            });
    }, [children, navigate]);

    const token = localStorage.getItem("accessToken");

    if (!token) {
        return <Navigate to={"/"} replace />;
    }
    return children;
};

export default ProtectedRoute;
