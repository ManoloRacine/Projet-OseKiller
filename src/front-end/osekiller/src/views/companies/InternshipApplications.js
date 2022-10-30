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
            .then((response) => {
                console.log(response);
                setApplicants(response.data);
            })
            .catch((err) => console.log(err));
    }, [authenticatedUser?.id, offerId]);

    const handleConfirm = () => {
        console.log("Confirm");
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
        </main>
    );
};

export default InternshipApplications;
