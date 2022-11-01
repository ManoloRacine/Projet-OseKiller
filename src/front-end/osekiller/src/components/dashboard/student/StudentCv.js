import React, { useEffect, useState } from "react";
import PropTypes from "prop-types";
import { getCv } from "../../../services/StudentService";
import LoadPdf from "../../LoadPdf";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
    faCircleCheck,
    faCircleXmark,
    faClock,
    faFile,
} from "@fortawesome/free-solid-svg-icons";
import { Link } from "react-router-dom";

const StudentCv = ({ studentInfo, studentId }) => {
    const [userPdf, setUserPdf] = useState("");

    useEffect(() => {
        getCv(studentId).then((response) => {
            if (response.status !== 204) {
                const blob1 = new Blob([response.data], {
                    type: "application/pdf",
                });
                const data_url = window.URL.createObjectURL(blob1);
                setUserPdf(data_url);
            }
        });
    }, []);

    return (
        <>
            {userPdf !== "" ? (
                <>
                    <LoadPdf
                        src={userPdf}
                        width={"100%"}
                        title={"studentCv"}
                        type={"application/json"}
                        height={"600px"}
                    />
                    <div
                        className={"text-white rounded p-2"}
                        style={{ backgroundColor: "#2C324C" }}
                    >
                        <div className={"d-flex align-items-center"}>
                            <p className={"fs-2 me-3"}>État :</p>
                            {studentInfo["cvValidated"] ? (
                                <div className={"d-flex flex-column"}>
                                    <FontAwesomeIcon
                                        icon={faCircleCheck}
                                        className="fa-2x text-success"
                                    />
                                    <p>Valide</p>
                                </div>
                            ) : studentInfo["cvRejected"] ? (
                                <div className={"d-flex flex-column"}>
                                    <FontAwesomeIcon
                                        icon={faCircleXmark}
                                        className="fa-2x text-danger"
                                    />
                                    <p>Invalide</p>
                                </div>
                            ) : (
                                <div className={"d-flex flex-column"}>
                                    <FontAwesomeIcon
                                        icon={faClock}
                                        className="fa-2x text-warning"
                                    />
                                    <p>Non validé</p>
                                </div>
                            )}
                        </div>
                        {studentInfo["cvPresent"] &&
                            (studentInfo["cvRejected"] ||
                                studentInfo["cvValidated"]) && (
                                <div
                                    className={
                                        "d-flex justify-content-between align-items-center"
                                    }
                                >
                                    <div>
                                        <h4>Feedback :</h4>
                                        <p>{studentInfo["feedback"]}</p>
                                    </div>

                                    {studentInfo["cvRejected"] && (
                                        <Link
                                            className={"btn btn-primary"}
                                            to={"/upload-cv"}
                                        >
                                            Modifier votre CV
                                        </Link>
                                    )}
                                </div>
                            )}
                    </div>
                </>
            ) : (
                <div
                    className={
                        "d-flex flex-column align-items-center justify-content-center text-white h-100 rounded"
                    }
                    style={{ backgroundColor: "#2C324C" }}
                >
                    <FontAwesomeIcon icon={faFile} className="fa-2x mb-3" />
                    <p>Vous n'avez pas téléversé de CV</p>
                    <Link className={"btn btn-primary"} to={"/upload-cv"}>
                        Téléverser votre CV
                    </Link>
                </div>
            )}
        </>
    );
};

StudentCv.propTypes = {
    studentInfo: PropTypes.object.isRequired,
    studentId: PropTypes.object.isRequired,
};

export default StudentCv;
