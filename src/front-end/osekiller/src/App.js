import { Routes, Route } from "react-router-dom";
import Home from "./components/Home";
import Dashboard from "./components/Dashboard";
import ProtectedRoute from "./ProtectedRoute";

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
            </Routes>
        </div>
    );
}

export default App;
