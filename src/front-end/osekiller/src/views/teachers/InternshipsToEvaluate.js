import { useEffect, useState } from "react";
import { ContractEvalCard } from "../../components/ContractEvalCard";
import { getContractsToEvaluate } from "../../services/TeacherService";

const InternshipsToEvaluate = () => {
    const [internships, setInternships] = useState([]);

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
