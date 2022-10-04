import { Link, useLocation, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import calLogo from "../assets/calLogo.jpg";
import { pingToken } from "../services/AuthService";

export const Header = () => {

    const navigate = useNavigate();

    const location = useLocation();

    const logout = () => {
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");
        navigate("/",{relative: false});
    };

    return (
        location.pathname === "/" 
        ||
        <nav
                className="d-flex p-2 rounded"
                style={{ backgroundColor: "#2C324C" }}
            >
                <div className="header d-flex align-items-center text-white">
                    <img src={calLogo} alt="Logo du Cégep André-Laurendeau" />
                    <h1 className="ps-4 display-4">Ose killer</h1>
                </div>
                <div className="links d-flex mx-auto">
                    <Link to="/user-validation" className="m-4 fs-2 d-flex align-items-center">
                        Validation d'utilisateur
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
    );
}

export default Header;