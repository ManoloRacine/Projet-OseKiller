import React from "react";
import PropTypes from "prop-types";
import { Link } from "react-router-dom";

const StudentCard = ({ student, redirectTo }) => {
    console.log(student) ;
    return (
        <div
            className={
                "offer d-flex justify-content-evenly align-items-center text-white my-4 py-4 rounded"
            }
            style={{ backgroundColor: "#2C324C" }}
            data-testid={"student-card"}
        >
            <div>
                <p className={"fs-4 text-decoration-underline"}>
                    Nom de l'étudiant
                </p>
                <p>{student.name}</p>
            </div>
            <div>
                <p className={"fs-4 text-decoration-underline"}>Courriel</p>
                <p>{student.email}$</p>
            </div>

            <Link
                to={redirectTo}
                className={"btn btn-primary"}
                state={{ studentEmail: student.email, studentId : student.id }}
            >
                Convoquer
            </Link>
        </div>
    );
};

StudentCard.propTypes = {
    student: PropTypes.object.isRequired,
    redirectTo: PropTypes.string.isRequired,
};

export default StudentCard;
