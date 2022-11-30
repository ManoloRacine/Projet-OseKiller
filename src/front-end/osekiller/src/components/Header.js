import { Link, useLocation, useNavigate } from "react-router-dom";
import { useContext } from "react";
import { AuthenticatedUserContext } from "../App";
import calLogo from "../assets/calLogo.jpg";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
    faHomeUser,
    faFileSignature,
    faFileArrowUp,
    faList,
    faBuilding,
    faArrowRightFromBracket,
    faBuildingCircleCheck,
    faFileCircleCheck,
    faPersonCircleCheck,
    faPenNib,
    faBuildingUser,
    faUserPen
} from "@fortawesome/free-solid-svg-icons";

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
                className="d-flex flex-column p-2 rounded"
                style={{ backgroundColor: "#2C324C", minHeight: "95vh" }}
            >
                <div
                    className={
                        " d-flex flex-column justify-content-center align-items-center py-4"
                    }
                >
                    <img src={calLogo} alt="Logo du cégep André-Laurendeau" />
                    <h1 className="fs-1 text-center text-white">Ose killer</h1>
                </div>

                <div className="links d-flex flex-column">
                    <Link
                        to={"/dashboard"}
                        className={"btn mb-2"}
                        style={{ backgroundColor: "#ee7600" }}
                    >
                        <FontAwesomeIcon icon={faHomeUser} className={"me-2"} />
                        Accueil
                    </Link>
                    {authenticatedUser.role === "MANAGER" && (
                        <Link
                            to="/user-validation"
                            className={"btn mb-2"}
                            style={{ backgroundColor: "#ee7600" }}
                        >
                            <FontAwesomeIcon
                                icon={faPersonCircleCheck}
                                className={"me-2"}
                            />
                            Validation des utilisateurs
                        </Link>
                    )}
                    {authenticatedUser.role === "MANAGER" && (
                        <Link
                            to={"/students-cv"}
                            className={"btn mb-2"}
                            style={{ backgroundColor: "#ee7600" }}
                        >
                            <FontAwesomeIcon
                                icon={faFileCircleCheck}
                                className={"me-2"}
                            />
                            Validation des CV
                        </Link>
                    )}
                    {authenticatedUser.role === "MANAGER" && (
                        <Link
                            to={"/offers-manager"}
                            className={"btn mb-2"}
                            style={{ backgroundColor: "#ee7600" }}
                        >
                            <FontAwesomeIcon
                                icon={faBuildingCircleCheck}
                                className={"me-2"}
                            />
                            Validation des offres de stages
                        </Link>
                    )}
                    {authenticatedUser.role === "STUDENT" && (
                        <Link
                            to={"/upload-cv"}
                            className={"btn mb-2"}
                            style={{ backgroundColor: "#ee7600" }}
                        >
                            <FontAwesomeIcon
                                icon={faFileArrowUp}
                                className={"me-2"}
                            />
                            Téléverser mon CV
                        </Link>
                    )}
                    {authenticatedUser.role === "STUDENT" && (
                        <Link
                            to={"/offers-students"}
                            className={"btn mb-2"}
                            style={{ backgroundColor: "#ee7600" }}
                        >
                            <FontAwesomeIcon
                                icon={faBuilding}
                                className={"me-2"}
                            />
                            Offres de stages
                        </Link>
                    )}
                    {authenticatedUser.role === "STUDENT" && (
                        <Link
                            to={"/offers-applied"}
                            className={"btn mb-2"}
                            style={{ backgroundColor: "#ee7600" }}
                        >
                            <FontAwesomeIcon icon={faList} className={"me-2"} />
                            Offres appliquées
                        </Link>
                    )}
                    {authenticatedUser.role === "COMPANY" && (
                        <Link
                            to={"/upload-internship"}
                            className={"btn mb-2"}
                            style={{ backgroundColor: "#ee7600" }}
                        >
                            <FontAwesomeIcon
                                icon={faFileArrowUp}
                                className={"me-2"}
                            />
                            Téléverser une offre de stage
                        </Link>
                    )}
                    {authenticatedUser.role === "COMPANY" && (
                        <Link
                            to={"/offers-company"}
                            className={"btn mb-2"}
                            style={{ backgroundColor: "#ee7600" }}
                        >
                            <FontAwesomeIcon
                                icon={faBuildingUser}
                                className={"me-2"}
                            />
                            Offres de stages
                        </Link>
                    )}
                    {(authenticatedUser.role === "MANAGER" ||
                        authenticatedUser.role === "STUDENT" ||
                        authenticatedUser.role === "COMPANY") && (
                        <Link
                            to={"/accepted-applications"}
                            className={"btn mb-2"}
                            style={{ backgroundColor: "#ee7600" }}
                        >
                            <FontAwesomeIcon
                                icon={faFileSignature}
                                className={"me-2"}
                            />
                            Ententes de stages
                        </Link>
                    )}
                    {authenticatedUser.role === "COMPANY" && (
                        <Link
                            to={"/get-interns"}
                            className={"btn mb-2"}
                            style={{ backgroundColor: "#ee7600" }}
                        >
                            <FontAwesomeIcon
                                icon={faPenNib}
                                className={"me-2"}
                            />
                            Évaluations des stagiaires
                        </Link>
                    )}

                    {authenticatedUser.role === "MANAGER" && (
                        <Link
                            to="/evaluations"
                            className={"btn mb-2"}
                            style={{ backgroundColor: "#ee7600" }}
                        >
                            <FontAwesomeIcon
                                icon={faPenNib}
                                className={"me-2"}
                            />
                            Évaluations des milieux de stages
                        </Link>
                    )}
                    {authenticatedUser.role === "TEACHER" && (
                        <Link
                            to={"/internships-to-evaluate"}
                            className={"btn mb-2"}
                            style={{ backgroundColor: "#ee7600" }}
                        >
                            <FontAwesomeIcon
                                icon={faPenNib}
                                className={"me-2"}
                            />
                            Évaluer des milieux de stage
                        </Link>
                    )}
                    {authenticatedUser.role === "MANAGER" && (
                        <Link
                            to={"/intern-evaluations"}
                            className={"btn mb-2"}
                            style={{ backgroundColor: "#ee7600" }}
                        >
                            <FontAwesomeIcon
                                icon={faUserPen}
                                className={"me-2"}
                            />
                            Évaluation des stagiaires
                        </Link>
                    )}
                </div>

                <button className="btn btn-danger mt-auto" onClick={logout}>
                    <FontAwesomeIcon
                        icon={faArrowRightFromBracket}
                        className={"me-2"}
                    />
                    Déconnexion
                </button>
            </nav>
        )
    );
};

export default Header;
