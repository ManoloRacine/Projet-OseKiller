import { Routes, Route } from "react-router-dom";
import Home from "./views/Home";
import Dashboard from "./views/Dashboard";
import ProtectedRoute from "./ProtectedRoute";
import UploadCv from "./views/UploadCv";
import ValidateCv from "./views/ValidateCv";

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
                <Route path="/" element={<Home />} />
                <Route
                    path="/dashboard"
                    element={
                        <ProtectedRoute>
                            <Dashboard />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/upload-cv"
                    element={
                        <ProtectedRoute>
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
            </Routes>
        </div>
    );
}

export default App;
