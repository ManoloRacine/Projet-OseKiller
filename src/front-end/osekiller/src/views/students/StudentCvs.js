import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getStudents } from "../../services/StudentService";

const StudentCvs = () => {
    const [students, setStudents] = useState([]);

    useEffect(() => {
        getStudents().then((response) => {
            console.log(response);
            setStudents(response.data);
        });
    }, []);

    return (
        <div className="row">
            <h2 className="text-center">Validation des CV</h2>
            <div>
                <ul className="text-center">
                    {students.map((student, index) =>
                        student["cvPresent"] === true &&
                        student["cvRejected"] === false &&
                        student["cvValidated"] === false ? (
                            <div className={"d-flex justify-content-evenly align-items-center text-white my-4 py-4 rounded"} style={{backgroundColor: "#2C324C"}} key={index}>
                                <div>
                                    <p className={"fs-4 text-decoration-underline"}>
                                        Nom de l'étudiant
                                    </p>
                                    <p>{student.name}</p>
                                </div>
                                <div>
                                    <p className={"fs-4 text-decoration-underline"}>Courriel</p>
                                    <p>{student.email}</p>
                                </div>
                                <Link
                                    className={"btn btn-primary"}
                                    to={"../validate-cv"}
                                    state={{ studentId: student["id"] }}
                                    key={index}
                                >
                                    Voir le CV
                                </Link>
                            </div>
                        ) : null
                    )}
                </ul>
            </div>
            <div className="col-4"></div>
        </div>
    );
};

export default StudentCvs;
