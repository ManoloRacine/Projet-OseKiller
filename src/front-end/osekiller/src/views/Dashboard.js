import { Link, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import calLogo from "../assets/calLogo.jpg";
import { pingToken } from "../services/AuthService";

const Dashboard = () => {
    const [userName, setUserName] = useState("");
    const [userId, setUserId] = useState("");
    const navigate = useNavigate();

    const logout = () => {
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");
        navigate("/");
    };

    useEffect(() => {
        pingToken()
            .then((response) => {
                setUserName(response.data.name);
                setUserId(response.data.id);
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
                    <Link
                        to={"/upload-cv"}
                        state={{ userId: userId }}
                        className="m-4 fs-2 d-flex align-items-center"
                    >
                        Téléverser votre CV
                    </Link>
                </div>
                <div className="links d-flex mx-auto">
                    <Link
                        to={"/validate-cv"}
                        state={{ userId: userId }}
                        className="m-4 fs-2 d-flex align-items-center"
                    >
                        Valider des CV
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
