import React, { useContext, useEffect, useState } from "react";
import { getApplicantsByOffer } from "../../services/CompanyService";
import { useLocation } from "react-router-dom";
import { AuthenticatedUserContext } from "../../App";
import StudentCard from "../../components/StudentCard";

const InternshipApplications = () => {
    const authenticatedUser = useContext(
        AuthenticatedUserContext
    )?.authenticatedUser;
    const [applicants, setApplicants] = useState([]);
    const location = useLocation();
    const { state } = location;
    const offerId = state?.offerId;

    useEffect(() => {
        getApplicantsByOffer(authenticatedUser?.id, offerId)
            .then((response) => setApplicants(response.data))
            .catch((err) => console.log(err));
    }, [authenticatedUser?.id, offerId]);

    return (
        <main>
            {applicants.map((student, index) => (
                <StudentCard key={index} student={student} redirectTo={""} />
            ))}
        </main>
    );
};

export default InternshipApplications;
