import { createContext, useState } from "react";
import { Outlet } from "react-router-dom";

import Header from "./components/Header";

export const AuthenticatedUserContext = createContext();

export const App = () => {
    const [authenticatedUser, setAuthenticatedUser] = useState({});

    return (
        <AuthenticatedUserContext.Provider
            value={{ authenticatedUser, setAuthenticatedUser }}
        >
            <div
                className="App vh-100 p-3 row m-0"
                style={{
                    backgroundColor: "#da8362",
                    color: "#2C324C",
                }}
            >
                {authenticatedUser && (
                    <div className={"col-sm-2"}>
                        <Header />
                    </div>
                )}
                <div className={authenticatedUser ? "col-sm-10" : "col-sm 12"}>
                    <Outlet />
                </div>
            </div>
        </AuthenticatedUserContext.Provider>
    );
};
