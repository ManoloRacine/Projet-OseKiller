import { useEffect, useState } from "react";
import { Navigate } from "react-router-dom";
import { pingToken } from "./services/AuthService";

const ProtectedRoute = ({
    children,
    redirectTo = "/",
    authenticated = false,
    allowedRoles,
}) => {
    const [userInfo, setUserInfo] = useState(undefined);

    useEffect(() => {
        pingToken()
            .then((response) => {
                setUserInfo(response.data);
            })
            .catch(() => {
                setUserInfo(undefined);
            });
    }, [children]);

    if (!userInfo) {
        if (authenticated) return <Navigate to={redirectTo} relative={false} />;

        return children;
    }
    if (!authenticated) return <Navigate to={redirectTo} relative={false} />;

    if (!allowedRoles) return children;

    if (allowedRoles.includes(userInfo.role)) return children;

    return <Navigate to={redirectTo} relative={false} />;
};

export default ProtectedRoute;
