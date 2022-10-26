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
        <div className={"mt-3 col-6"}>
            <h1>Mon CV</h1>
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
                (studentInfo["cvRejected"] || studentInfo["cvValidated"]) ? (
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
        /*<div className="row">
            <div className="col-6">
                {studentInfo["cvValidated"] && (
                    <h3 className="text-success">CV est valide</h3>
                )}
                {studentInfo["cvRejected"] && (
                    <h3 className="text-danger">CV n'est pas valide</h3>
                )}
                {studentInfo["cvPresent"] &&
                (studentInfo["cvRejected"] || studentInfo["cvValidated"]) ? (
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
            <div className="col-6">
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
            </div>
        </div>*/
    );
};

export default StudentDashboard;
