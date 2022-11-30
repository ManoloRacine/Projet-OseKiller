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
            <h2 className="text-center">Évaluer des milieux de stage</h2>
            {internships.map((value, index) => (
                <ContractEvalCard contractInfo={value} />
            ))}
        </div>
    );
};

export default InternshipsToEvaluate;
