import LoadPdf from "../LoadPdf";
import React, { useContext, useEffect, useState } from "react";
import { getCv, getStudent } from "../../services/StudentService";
import { AuthenticatedUserContext } from "../../App";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
    faCircleCheck,
    faCircleXmark,
} from "@fortawesome/free-solid-svg-icons";

const StudentDashboard = () => {
    const studentId = useContext(AuthenticatedUserContext)?.authenticatedUser
        ?.id;
    const [userPdf, setUserPdf] = useState("");
    const [studentInfo, setStudentInfo] = useState({});
    const [convocations, setConvocation] = useState([
        {
            position: "Développeur Java",
            date1: "2022-10-23",
            date2: "2022-10-24",
            date3: "2022-10-25",
        },
        {
            position: "Développeur Python",
            date1: "2022-11-04",
            date2: "2022-11-07",
            date3: "2022-11-15",
        },
    ]);

    useEffect(() => {
        getStudent(studentId).then((response) => {
            setStudentInfo(response.data);
        });
        getCv(studentId).then((response) => {
            if (response.status !== 204) {
                const blob1 = new Blob([response.data], {
                    type: "application/pdf",
                });
                const data_url = window.URL.createObjectURL(blob1);
                setUserPdf(data_url);
            }
        });
    }, [studentId]);

    return (
        <div className={"mt-3 row"}>
            <div className={"col-6"}>
                <h2>Mon CV</h2>
                {userPdf !== "" ? (
                    <LoadPdf
                        src={userPdf}
                        width={"100%"}
                        title={"studentCv"}
                        type={"application/json"}
                        height={"600px"}
                    />
                ) : (
                    <p>Vous n'avez pas téléversé de CV</p>
                )}
                <div
                    className={"text-white rounded p-2"}
                    style={{ backgroundColor: "#2C324C" }}
                >
                    <div className={"d-flex align-items-center"}>
                        <p className={"fs-2 me-3"}>État :</p>
                        {studentInfo["cvValidated"] && (
                            <div className={"d-flex flex-column"}>
                                <FontAwesomeIcon
                                    icon={faCircleCheck}
                                    className="fa-2x text-success"
                                />
                                <p>Valide</p>
                            </div>
                        )}
                        {studentInfo["cvRejected"] && (
                            <div className={"d-flex flex-column"}>
                                <FontAwesomeIcon
                                    icon={faCircleXmark}
                                    className="fa-2x text-danger"
                                />
                                <p>Invalide</p>
                            </div>
                        )}
                    </div>

                    {studentInfo["cvPresent"] &&
                    (studentInfo["cvRejected"] ||
                        studentInfo["cvValidated"]) ? (
                        <div>
                            <h4>Feedback :</h4>
                            <p>{studentInfo["feedback"]}</p>
                        </div>
                    ) : studentInfo["cvPresent"] ? (
                        <h4 className="text-warning">
                            CV en attente de validation
                        </h4>
                    ) : null}
                </div>
            </div>
            <div className={"col-6"}>
                <h2>Mes convocations</h2>

                {convocations.map((convocation) => (
                    <div
                        className={
                            "text-white d-flex justify-content-around rounded m-2"
                        }
                        style={{ backgroundColor: "#2C324C" }}
                    >
                        <div>
                            <p className={"fs-4 m-0 text-decoration-underline"}>
                                Poste
                            </p>
                            <p>{convocation.position}</p>
                        </div>
                        <div>
                            <p className={"fs-4 m-0 text-decoration-underline"}>
                                Dates possible
                            </p>

                            <div className="mb-3 form-check">
                                <input
                                    type="radio"
                                    className="form-check-input"
                                    id="date1"
                                    name={"dates"}
                                />
                                <label
                                    className="form-check-label"
                                    htmlFor="exampleCheck1"
                                >
                                    {convocation.date1}
                                </label>
                            </div>
                            <div className="mb-3 form-check">
                                <input
                                    type="radio"
                                    className="form-check-input"
                                    id="date2"
                                    name={"dates"}
                                />
                                <label
                                    className="form-check-label"
                                    htmlFor="exampleCheck1"
                                >
                                    {convocation.date2}
                                </label>
                            </div>
                            <div className="mb-3 form-check">
                                <input
                                    type="radio"
                                    className="form-check-input"
                                    id="date3"
                                    name={"dates"}
                                />
                                <label
                                    className="form-check-label"
                                    htmlFor="exampleCheck1"
                                >
                                    {convocation.date3}
                                </label>
                            </div>
                        </div>
                        <div className={"d-flex align-items-end"}>
                            <button className={"btn btn-primary mb-3"}>
                                Choisir
                            </button>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default StudentDashboard;
