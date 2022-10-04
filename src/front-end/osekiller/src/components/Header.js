import { Link, useLocation, useNavigate } from "react-router-dom";
import { useContext } from "react";
import calLogo from "../assets/calLogo.jpg";
import { AuthenticatedUserContext } from "../App";

export const Header = () => {

    const navigate = useNavigate();

    const location = useLocation();
    const {authenticatedUser} = useContext(AuthenticatedUserContext);

    const logout = () => {
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");
        navigate("/");
    };

    return (
        (location.pathname === "/" || !authenticatedUser) 
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
                {authenticatedUser.role === "MANAGER" && 
                    (<Link to="/user-validation" className="m-4 fs-2 d-flex align-items-center">
                        Validation d'utilisateur
                    </Link>)}
                    {authenticatedUser.role === "STUDENT" && (
                        <Link
                            to={"/upload-cv"}
                            state={{ userId: authenticatedUser.id }}
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