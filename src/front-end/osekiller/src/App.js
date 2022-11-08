import { createContext, useState } from "react";
import { Outlet } from "react-router-dom";

import Header from "./components/Header";
import InternshipEvaluation from "./views/teachers/InternshipEvaluation";

export const AuthenticatedUserContext = createContext();

export const App = () => {
    const [authenticatedUser, setAuthenticatedUser] = useState({});

    return (
        /*
        <AuthenticatedUserContext.Provider value={{authenticatedUser, setAuthenticatedUser}}>
            <div
            className="App p-3"
            style={{
                backgroundColor: "#da8362",
                minHeight: "100vh",
                color: "#2C324C",
            }}>
                <Header />
                <Outlet />
            </div>
        </AuthenticatedUserContext.Provider>*/
        <InternshipEvaluation />
    );
};
