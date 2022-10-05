import {useContext, useEffect} from "react";
import {Navigate} from "react-router-dom";
import {AuthenticatedUserContext} from "./App";
import {pingToken} from "./services/AuthService";

const ProtectedRoute = ({children, redirectTo = "/", authenticated = false, allowedRoles}) => {

    const {authenticatedUser, setAuthenticatedUser} = useContext(AuthenticatedUserContext);
    
    useEffect(() => {
        pingToken()
            .then(
                (response) => {
                    setAuthenticatedUser(response.data);        
                }
            )
            .catch(
                () => {
                    setAuthenticatedUser(undefined)
                }
            ) 
    },[children, setAuthenticatedUser])

    if(!authenticatedUser){
        if (authenticated) return <Navigate to={redirectTo} relative={false}/>

        return children
    }

    if(!authenticated) return <Navigate to={redirectTo} relative={false}/>
                
    if(!allowedRoles) return children;

    if(allowedRoles.includes(authenticatedUser.role)) return children
                    
    return <Navigate to={redirectTo} relative={false}/>
};

export default ProtectedRoute;