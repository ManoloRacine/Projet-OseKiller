import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getStudents } from "../../services/StudentService";

const StudentCvs = () => {
    const [students, setStudents] = useState([]);

    useEffect(() => {
        getStudents().then((response) => {
            setStudents(response.data);
        });
    }, []);

    return (
        <div className="row">
            <h2 className="text-center">Validation des CV</h2>
            <div className="col-4">
                <ul className="text-center">
                    {students.map((student, index) =>
                        student["cvPresent"] === true &&
                        student["cvRejected"] === false &&
                        student["cvValidated"] === false ? (
                            <li key={index}>
                                <Link
                                    to={"../validate-cv"}
                                    state={{ studentId: student["id"] }}
                                    key={index}
                                >
                                    {student["name"]}
                                </Link>
                            </li>
                        ) : null
                    )}
                </ul>
            </div>
            <div className="col-4"></div>
        </div>
    );
};

export default StudentCvs;
