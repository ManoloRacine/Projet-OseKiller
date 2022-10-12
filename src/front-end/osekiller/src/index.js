import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import { App } from "./App";
import reportWebVitals from "./reportWebVitals";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import ProtectedRoute from "./ProtectedRoute";
import Home from "./views/Home";
import Dashboard from "./views/Dashboard";
import UserValidation from "./views/UserValidation";
import UploadCv from "./views/UploadCv";
import StudentCvs from "./views/StudentCvs";
import ValidateCv from "./views/ValidateCv";
import UploadInternship from "./views/UploadInternship";
import ValidateOffer from "./views/ValidateOffer";

<link
    rel="stylesheet"
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
    integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor"
    crossorigin="anonymous"
/>;

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
    <BrowserRouter>
        <Routes>
            <Route path="/" element={<App />}>
                <Route
                    index
                    element={
                        <ProtectedRoute redirectTo="/dashboard">
                            <Home />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="dashboard"
                    element={
                        <ProtectedRoute authenticated>
                            <Dashboard />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="user-validation"
                    element={
                        <ProtectedRoute
                            authenticated
                            allowedRoles={["MANAGER"]}
                        >
                            <UserValidation />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="upload-cv"
                    element={
                        <ProtectedRoute
                            authenticated
                            allowedRoles={["STUDENT"]}
                        >
                            <UploadCv />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="validate-cv"
                    element={
                        <ProtectedRoute
                            authenticated
                            allowedRoles={["MANAGER"]}
                        >
                            <ValidateCv />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="students-cv"
                    element={
                        <ProtectedRoute
                            authenticated
                            allowedRoles={["MANAGER"]}
                        >
                            <StudentCvs />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="upload-internship"
                    element={
                        <ProtectedRoute
                            authenticated
                            allowedRoles={["COMPANY"]}
                        >
                            <UploadInternship />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="validate-offer"
                    element={
                        <ProtectedRoute
                            authenticated
                            allowedRoles={["MANAGER"]}
                        >
                            <ValidateOffer />
                        </ProtectedRoute>
                    }
                />
            </Route>
        </Routes>
    </BrowserRouter>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
