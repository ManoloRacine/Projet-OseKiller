import { Routes, Route } from "react-router-dom";
import Home from "./views/Home";
import Dashboard from "./views/Dashboard";
import ProtectedRoute from "./ProtectedRoute";
import UploadCv from "./views/UploadCv";
import ValidateCv from "./views/ValidateCv";
import StudentCvs from "./views/StudentCvs";


function App() {
    return (
        <div
            className="App"
            style={{
                backgroundColor: "#da8362",
                minHeight: "100vh",
                color: "#2C324C",
            }}
        >
            <Routes>
                <Route
                    path="/"
                    element={
                        <ProtectedRoute redirectTo="/dashboard">
                            <Home />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/dashboard"
                    element={
                        <ProtectedRoute authenticated>
                            <Dashboard />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/upload-cv"
                    element={
                        <ProtectedRoute authenticated allowedRole={["STUDENT"]}>
                            <UploadCv />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/validate-cv"
                    element={
                        <ProtectedRoute>
                            <ValidateCv />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/students-cv"
                    element={
                        <ProtectedRoute>
                            <StudentCvs />
                        </ProtectedRoute>
                    }
                />

            </Routes>
        </div>
    );
}

export default App;
