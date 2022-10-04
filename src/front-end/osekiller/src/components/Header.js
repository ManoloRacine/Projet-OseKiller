import { Link, useLocation, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import calLogo from "../assets/calLogo.jpg";
import { pingToken } from "../services/AuthService";

export const Header = () => {

    const navigate = useNavigate();

    const location = useLocation();
    const [userId, setUserId] = useState("");
    const [role, setRole] = useState("");

    const logout = () => {
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");
        navigate("/");
    };

    
    //Pas vraiment optimale parce que faudrait faire un call dans chaque component qui à besoins des information de l'utilisateur
    // Faudrait qu'on ait un global state
    useEffect(() => {
        pingToken()
            .then((response) => {
                setUserId(response.data.id);
                setRole(response.data.role);
            })
            .catch((err) => {
                console.log(err);
            });
    }, []);

    return (
        location.pathname === "/" 
        ||
        <nav
                className="d-flex p-2 rounded"
                style={{ backgroundColor: "#2C324C" }}
            >
                <div className="header d-flex align-items-center text-white">
                    <Link to={"/dashboard"} >
                        <img src={calLogo} alt="Logo du Cégep André-Laurendeau" />
                    </Link>
                    
                    <h1 className="ps-4 display-4">Ose killer</h1>
                </div>
                <div className="links d-flex mx-auto">
                {role === "MANAGER" && 
                    (<Link to="/user-validation" className="m-4 fs-2 d-flex align-items-center">
                        Validation d'utilisateur
                    </Link>)}
                    {role === "STUDENT" && (
                        <Link
                            to={"/upload-cv"}
                            state={{ userId: userId }}
                            className="m-4 fs-2 d-flex align-items-center"
                        >
                            Téléverser votre CV
                        </Link>
                    )}
                </div>

                {/* Bouton à améliorer */}
                <button className="btn btn-danger" onClick={logout}>
                    Déconnexion
                </button>
            </nav>
    );
}

export default Header;