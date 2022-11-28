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
import SeeInternships from "./views/students/SeeInternships";
import ValidateUsers from "./views/managers/ValidateUsers";
import UploadCv from "./views/students/UploadCv";
import StudentCvs from "./views/students/StudentCvs";
import ValidateCv from "./views/managers/ValidateCv";
import UploadInternship from "./views/companies/UploadInternship";
import OffersManager from "./views/managers/OffersManager";
import ValidateOffer from "./views/managers/ValidateOffer";
import ApplyOffer from "./views/students/ApplyOffer";
import OffersCompany from "./views/companies/OffersCompany";
import InternshipApplications from "./views/companies/InternshipApplications";
import AppliedOffers from "./views/students/AppliedOffers";
import InviteStudent from "./views/companies/InviteStudent";
import OfferDetails from "./views/managers/OfferDetails";
import AcceptedApplications from "./views/managers/AcceptedApplications";
import EvaluationDetail from "./views/managers/EvaluationDetail";
import Evaluations from "./views/managers/Evaluations";
import InternshipEvaluation from "./views/teachers/InternshipEvaluation";
import InternshipsToEvaluate from "./views/teachers/InternshipsToEvaluate";
import EvaluateStudent from "./views/companies/EvaluateStudent";
import GetInterns from "./views/companies/GetInterns";
import StudentEvaluations from "./views/managers/StudentEvaluations";
import StudentEvaluationDetail from "./views/managers/StudentEvaluationDetail";

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
                            <ValidateUsers />
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
                    path="offers-students"
                    element={
                        <ProtectedRoute
                            authenticated
                            allowedRoles={["STUDENT"]}
                        >
                            <SeeInternships />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="apply-offer"
                    element={
                        <ProtectedRoute
                            authenticated
                            allowedRoles={["STUDENT"]}
                        >
                            <ApplyOffer />
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
                    path="offers-manager"
                    element={
                        <ProtectedRoute
                            authenticated
                            allowedRoles={["MANAGER"]}
                        >
                            <OffersManager />
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
                <Route
                    path="offers-company"
                    element={
                        <ProtectedRoute
                            authenticated
                            allowedRoles={["COMPANY"]}
                        >
                            <OffersCompany />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="internship-applications"
                    element={
                        <ProtectedRoute
                            authenticated
                            allowedRoles={["COMPANY"]}
                        >
                            <InternshipApplications />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="offers-applied"
                    element={
                        <ProtectedRoute
                            authenticated
                            allowedRoles={["STUDENT"]}
                        >
                            <AppliedOffers />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="invite-student"
                    element={
                        <ProtectedRoute
                            authenticated
                            allowedRoles={["COMPANY"]}
                        >
                            <InviteStudent />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="offer-details"
                    element={
                        <ProtectedRoute
                            authenticated
                            allowedRoles={["MANAGER"]}
                        >
                            <OfferDetails />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="accepted-applications"
                    element={
                        <ProtectedRoute
                            authenticated
                            allowedRoles={["MANAGER", "STUDENT", "COMPANY"]}
                        >
                            <AcceptedApplications />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="evaluation-detail"
                    element={
                        <ProtectedRoute
                            authenticated
                            allowedRoles={["MANAGER"]}
                        >
                            <EvaluationDetail />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="internship-evaluations"
                    element={
                        <ProtectedRoute
                            authenticated
                            allowedRoles={["TEACHER"]}
                        >
                            <InternshipEvaluation />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="evaluations"
                    element={
                        <ProtectedRoute
                            authenticated
                            allowedRoles={["MANAGER"]}
                        >
                            <Evaluations />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="internships-to-evaluate"
                    element={
                        <ProtectedRoute
                            authenticated
                            allowedRoles={["TEACHER"]}
                        >
                            <InternshipsToEvaluate />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="evaluate-student"
                    element={
                        <ProtectedRoute
                            authenticated
                            allowedRoles={["COMPANY"]}
                        >
                            <EvaluateStudent />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="get-interns"
                    element={
                        <ProtectedRoute
                            authenticated
                            allowedRoles={["COMPANY"]}
                        >
                            <GetInterns />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="intern-evaluations"
                    element={
                        <ProtectedRoute
                            authenticated
                            allowedRoles={["MANAGER"]}
                        >
                            <StudentEvaluations />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="intern-evaluation-detail"
                    element={
                        <ProtectedRoute
                            authenticated
                            allowedRoles={["MANAGER"]}
                        >
                            <StudentEvaluationDetail />
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
