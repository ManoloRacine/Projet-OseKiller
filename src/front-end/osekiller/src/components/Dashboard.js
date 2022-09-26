import { Link, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import calLogo from "../calLogo.jpg";
import { pingToken } from "../services/AuthService";

const Dashboard = () => {
    const [userName, setUserName] = useState("");
    const navigate = useNavigate();

    const logout = () => {
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");
        navigate("/");
    };

    useEffect(() => {
        pingToken()
            .then((response) => {
                setUserName(response.data.userName);
            })
            .catch((err) => {
                console.log(err);
            });
    }, []);

    return (
        <div className="p-3">
            <nav
                className="d-flex p-2 rounded"
                style={{ backgroundColor: "#2C324C" }}
            >
                <div className="header d-flex align-items-center text-white">
                    <img src={calLogo} alt="Logo du Cégep André-Laurendeau" />
                    <h1 className="ps-4 display-4">Ose killer</h1>
                </div>
                <div className="links d-flex mx-auto">
                    <Link to="/" className="m-4 fs-2 d-flex align-items-center">
                        Link 1
                    </Link>
                    <Link to="/" className="m-4 fs-2 d-flex align-items-center">
                        Link 2
                    </Link>
                    <Link to="/" className="m-4 fs-2 d-flex align-items-center">
                        Link 3
                    </Link>
                </div>

                {/* Bouton à améliorer */}
                <button className="btn btn-primary" onClick={logout}>
                    Déconnexion
                </button>
            </nav>
            <h1>{`Bonjour, ${userName}`}</h1>
        </div>
    );
};

export default Dashboard;
