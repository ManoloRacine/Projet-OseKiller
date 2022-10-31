import React from "react";
import PropTypes from "prop-types";
import { Link } from "react-router-dom";

const StudentCard = ({ student, redirectTo, offerId, confirmStudent }) => {
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

            {student.isChosen ? (
                <div>Sélectionné pour le stage</div>
            ) : (
                <>
                    <Link
                        to={redirectTo}
                        className={"btn btn-primary"}
                        state={{
                            studentEmail: student.email,
                            studentId: student.id,
                            offerId: offerId,
                        }}
                    >
                        Convoquer
                    </Link>
                    <button
                        className={"btn"}
                        style={{ backgroundColor: "#ee7600" }}
                        onClick={() => confirmStudent(student.id)}
                    >
                        Choisir
                    </button>
                </>
            )}
        </div>
    );
};

StudentCard.propTypes = {
    student: PropTypes.object.isRequired,
    redirectTo: PropTypes.string.isRequired,
    offerId: PropTypes.number.isRequired,
    confirmStudent: PropTypes.func.isRequired,
};

export default StudentCard;
