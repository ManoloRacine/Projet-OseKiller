import { useEffect, useState } from "react";
import { ContractEvalCard } from "../../components/ContractEvalCard";
import { getContractsToEvaluate } from "../../services/TeacherService";

const InternshipsToEvaluate = () => {
    const [internships, setInternships] = useState([
        {
            contractId: 10,
            companyName: "Google",
            studentName: "Obama",
            position: "dev",
        },
    ]);

    useEffect(() => {
        getContractsToEvaluate().then((response) => {
            setInternships(response.data);
        });
    }, []);

    return (
        <div>
            {internships.map((value, index) => (
                <ContractEvalCard contractInfo={value} />
            ))}
        </div>
    );
};

export default InternshipsToEvaluate;
