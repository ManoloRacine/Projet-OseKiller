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
            >
                <div>
                    <p className={"fs-4 m-0 text-decoration-underline"}>
                        Poste
                    </p>
                    <p>{convocation.position}</p>
                </div>
                {!convocation.interviewDate ? (
                    <>
                        <div>
                            <p className={"fs-4 m-0 text-decoration-underline"}>
                                Dates possible
                            </p>
                            {convocation.proposedDates.map((date, index) => (
                                <div key={index} className="mb-3 form-check">
                                    <input
                                        type="radio"
                                        className="form-check-input"
                                        id="date1"
                                        name={`dates-${convocation.interviewId}`}
                                        value={date}
                                        onChange={({ target }) =>
                                            setConfirmDate(target.value)
                                        }
                                    />
                                    <label
                                        className="form-check-label"
                                        htmlFor="exampleCheck1"
                                    >
                                        {date}
                                    </label>
                                </div>
                            ))}
                        </div>
                        <div className={"d-flex align-items-end"}>
                            {confirmDate ? (
                                <button
                                    className={"btn btn-primary mb-3"}
                                    onClick={() =>
                                        confirmInterviewDate(
                                            convocation.interviewId,
                                            confirmDate
                                        )
                                    }
                                >
                                    Choisir
                                </button>
                            ) : (
                                <button
                                    className={"btn btn-primary mb-3"}
                                    onClick={() =>
                                        confirmInterviewDate(
                                            convocation.interviewId,
                                            confirmDate
                                        )
                                    }
                                    disabled
                                >
                                    Choisir
                                </button>
                            )}
                        </div>
                    </>
                ) : (
                    <div className={"d-flex flex-column"}>
                        <p className={"fs-4 m-0 text-decoration-underline"}>
                            Date de la convocation
                        </p>
                        <p>{convocation.interviewDate}</p>
                    </div>
                )}
            </div>
        </div>
    );
};

StudentConvocation.propTypes = {
    convocation: PropTypes.object.isRequired,
    confirmInterviewDate: PropTypes.func.isRequired,
};

export default StudentConvocation;
