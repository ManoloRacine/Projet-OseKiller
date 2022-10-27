import React, { useState } from "react";
import PropTypes from "prop-types";

const StudentConvocation = ({ convocation, confirmInterviewDate }) => {
    const [confirmDate, setConfirmDate] = useState("");

    return (
        <div>
            <div
                className={
                    "text-white d-flex justify-content-around rounded m-2"
                }
                style={{ backgroundColor: "#2C324C" }}
                key={convocation.id}
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
                            name={`dates-${convocation.id}`}
                            value={convocation.date1}
                            onChange={({ target }) =>
                                setConfirmDate(target.value)
                            }
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
                            name={`dates-${convocation.id}`}
                            value={convocation.date2}
                            onChange={({ target }) =>
                                setConfirmDate(target.value)
                            }
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
                            name={`dates-${convocation.id}`}
                            value={convocation.date3}
                            onChange={({ target }) =>
                                setConfirmDate(target.value)
                            }
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
                    <button
                        className={"btn btn-primary mb-3"}
                        onClick={() =>
                            confirmInterviewDate(convocation.id, confirmDate)
                        }
                    >
                        Choisir
                    </button>
                </div>
            </div>
        </div>
    );
};

StudentConvocation.propTypes = {
    convocation: PropTypes.object.isRequired,
    confirmInterviewDate: PropTypes.func.isRequired,
};

export default StudentConvocation;
