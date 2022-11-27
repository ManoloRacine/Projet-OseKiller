import { Link, useLocation, useNavigate } from "react-router-dom";
import { useContext } from "react";
import { AuthenticatedUserContext } from "../App";

export const Header = () => {
    const navigate = useNavigate();

    const location = useLocation();
    const { authenticatedUser } = useContext(AuthenticatedUserContext);

    const logout = () => {
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");
        navigate("/");
    };

    return (
        location.pathname === "/" ||
        !authenticatedUser || (
            <nav
                className="d-flex p-2 rounded"
                style={{ backgroundColor: "#2C324C" }}
            >
                <div className="header d-flex align-items-center text-white">
                    <Link to={"/dashboard"}>
                        <button className="btn btn-primary align-middle">
                            Dashboard
                        </button>
                    </Link>
                    <h1 className="ps-4 display-4">Ose killer</h1>
                </div>
                <div className="links d-flex mx-auto">
                    {authenticatedUser.role === "MANAGER" && (
                        <Link
                            to="/user-validation"
                            className="m-4 fs-2 d-flex align-items-center"
                        >
                            Validation d'utilisateur
                        </Link>
                    )}
                    {authenticatedUser.role === "MANAGER" && (
                        <Link
                            to={"/students-cv"}
                            className="m-4 fs-2 d-flex align-items-center"
                        >
                            Valider des CV
                        </Link>
                    )}
                    {authenticatedUser.role === "MANAGER" && (
                        <Link
                            to={"/offers-manager"}
                            className="m-4 fs-2 d-flex align-items-center"
                        >
                            Valider des offres de stages
                        </Link>
                    )}
                    {(authenticatedUser.role === "MANAGER" ||
                        authenticatedUser.role === "STUDENT" ||
                        authenticatedUser.role === "COMPANY") && (
                        <Link
                            to={"/accepted-applications"}
                            className="m-4 fs-2 d-flex align-items-center"
                        >
                            Applications Acceptées
                        </Link>
                    )}
                    {authenticatedUser.role === "COMPANY" && (
                        <Link
                            to={"/offers-company"}
                            state={{ userId: authenticatedUser.id }}
                            className="m-4 fs-2 d-flex align-items-center"
                        >
                            Application à mes offres de stage
                        </Link>
                    )}
                    {authenticatedUser.role === "COMPANY" && (
                        <Link
                            to={"/get-interns"}
                            className="m-4 fs-2 d-flex align-items-center"
                        >
                            Évaluer mes stagiaires
                        </Link>
                    )}
                    {authenticatedUser.role === "STUDENT" && (
                        <Link
                            to={"/upload-cv"}
                            state={{ userId: authenticatedUser.id }}
                            className="m-4 fs-2 d-flex align-items-center"
                        >
                            Téléverser votre CV
                        </Link>
                    )}
                    {authenticatedUser.role === "STUDENT" && (
                        <Link
                            to={"/offers-applied"}
                            state={{ userId: authenticatedUser.id }}
                            className="m-4 fs-2 d-flex align-items-center"
                        >
                            Voir offres appliqués
                        </Link>
                    )}
                    {authenticatedUser.role === "STUDENT" && (
                        <Link
                            to={"/offers-students"}
                            state={{ userId: authenticatedUser.id }}
                            className="m-4 fs-2 d-flex align-items-center"
                        >
                            Voir les offres de stage
                        </Link>
                    )}
                    {authenticatedUser.role === "COMPANY" && (
                        <Link
                            to={"/upload-internship"}
                            state={{ userId: authenticatedUser.id }}
                            className="m-4 fs-2 d-flex align-items-center"
                        >
                            Téléverser une offre de stage
                        </Link>
                    )}
                    {authenticatedUser.role === "MANAGER" && (
                        <Link
                            to="/evaluations"
                            className="m-4 fs-2 d-flex align-items-center"
                        >
                            Évaluations
                        </Link>
                    )}
                    {authenticatedUser.role === "TEACHER" && (
                        <Link
                            to={"/internships-to-evaluate"}
                            className="m-4 fs-2 d-flex align-items-center"
                        >
                            Évaluer des milieux de stage
                        </Link>
                    )}
                </div>

                {/* Bouton à améliorer */}
                <button className="btn btn-danger" onClick={logout}>
                    Déconnexion
                </button>
            </nav>
        )
    );
};

export default Header;
