import React, { useContext, useEffect, useState } from "react";
import {
    acceptStudentApplication,
    getApplicantsByOffer,
} from "../../services/CompanyService";
import { useLocation } from "react-router-dom";
import { AuthenticatedUserContext } from "../../App";
import StudentCard from "../../components/StudentCard";
import ErrorMessage from "../../components/ErrorMessage";

const InternshipApplications = () => {
    const authenticatedUser = useContext(
        AuthenticatedUserContext
    )?.authenticatedUser;
    const [applicants, setApplicants] = useState([]);
    const [message, setMessage] = useState("");
    const [severity, setSeverity] = useState("");
    const location = useLocation();
    const { state } = location;
    const offerId = state?.offerId;

    useEffect(() => {
        getApplicantsByOffer(authenticatedUser?.id, offerId)
            .then((response) => {
                setApplicants(response.data);
            })
            .catch((err) => console.log(err));
    }, [authenticatedUser?.id, offerId]);

    const handleConfirm = (applicantId) => {
        acceptStudentApplication(authenticatedUser?.id, offerId, applicantId)
            .then(() => {
                setMessage("Étudiant choisi avec succès!");
                setSeverity("success");
            })
            .catch((err) => {
                console.log(err);
                setMessage("Une erreur s'est produite...");
                setSeverity("error");
            });
    };

    return (
        <main>
            {applicants.map((student, index) => (
                <StudentCard
                    key={index}
                    offerId={offerId}
                    student={student}
                    redirectTo={"/invite-student"}
                    confirmStudent={handleConfirm}
                />
            ))}
            {message && <ErrorMessage message={message} severity={severity} />}
        </main>
    );
};

export default InternshipApplications;
