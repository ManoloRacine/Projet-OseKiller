import { Routes, Route, Outlet } from "react-router-dom";
import Home from "./views/Home";
import Dashboard from "./views/Dashboard";
import ProtectedRoute from "./ProtectedRoute";
import UserValidation from "./views/UserValidation";
import Header from "./components/Header";

function App() {
    return (
        <div
            className="App p-3"
            style={{
                backgroundColor: "#da8362",
                minHeight: "100vh",
                color: "#2C324C",
            }}
        >
            <Header />
            <Outlet />
        </div>
    );
}

export default App;
