import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { pingToken } from "../src/services/AuthService";
import Dashboard from "./views/Dashboard";

const ProtectedRoute = ({ children, allowedRole }) => {
    const navigate = useNavigate();
    const [isAllowed, setIsAllowed] = useState(false);

    useEffect(() => {
        console.log(allowedRole);
        pingToken()
            .then(({ data }) => {
                console.log(data);
                if (allowedRole.includes(data.role)) {
                    setIsAllowed(true);
                }
            })
            .catch((err) => {
                console.log(err);
                if (err.response.status === 403) {
                    console.log("Token expiré");
                    navigate("/");
                }
            });
    }, [navigate, allowedRole]);

    if (isAllowed) {
        return children;
    } else {
        window.history.replaceState(null, null, "/dashboard");
        return <Dashboard />;
    }
};

export default ProtectedRoute;
