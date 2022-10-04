import { Link, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import calLogo from "../assets/calLogo.jpg";
import { pingToken } from "../services/AuthService";
import { getCV } from "../services/CvServices";
import { getStudent } from "../services/UserServices";

const Dashboard = () => {
    const [userName, setUserName] = useState("");
    const [userId, setUserId] = useState("");
    const [userPdf, setUserPdf] = useState("");
    const [role, setRole] = useState("");
    const [studentInfo, setStudentInfo] = useState({});

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
                getCV(response.data.id).then((response) => {
                    if (response.status !== 204) {
                        var blob1 = new Blob([response.data], {
                            type: "application/pdf",
                        });
                        var data_url = window.URL.createObjectURL(blob1);
                        setUserPdf(data_url);
                    }
                });
                if (response.data.role === "STUDENT") {
                    getStudent(response.data.id).then((response) => {
                        setStudentInfo(response.data);
                        console.log(response.data);
                    });
                }
                setRole(response.data.role);
                console.log(response.data);

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
                <div className="links d-flex mx-auto">
                    {role === "MANAGER" && (
                        <Link
                            to={"/students-cv"}
                            className="m-4 fs-2 d-flex align-items-center"
                        >
                            Valider des CV
                        </Link>
                    )}

                </div>

                {/* Bouton à améliorer */}
                <button className="btn btn-primary" onClick={logout}>
                    Déconnexion
                </button>
            </nav>
            <h1>{`Bonjour, ${userName}`}</h1>
            <div className="row">
                <div className="col-6"></div>
                <div className="col-6">
                    {role === "STUDENT" ? (
                        userPdf !== "" ? (
                            <iframe
                                src={userPdf}
                                height="600px"
                                width="100%"
                            ></iframe>
                        ) : (
                            <p>You do not have a CV uploaded</p>
                        )
                    ) : null}
                    {role === "STUDENT" &&
                    studentInfo["cvPresent"] &&
                    (studentInfo["cvRejected"] === true ||
                        studentInfo["cvValidated"] === true) ? (
                        <p>{studentInfo["feedback"]}</p>
                    ) : null}
                </div>
            </div>
        </div>
    );
};

export default Dashboard;
