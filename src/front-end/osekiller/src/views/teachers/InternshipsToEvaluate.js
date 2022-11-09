import { useEffect, useState } from "react";
import { ContractEvalCard } from "../../components/ContractEvalCard";

const InternshipsToEvaluate = () => {
    const [internships, setInternships] = useState([]);

    useEffect(() => {}, []);

    return (
        <div>
            {internships.map((value, index) => (
                <ContractEvalCard contractInfo={value} />
            ))}
        </div>
    );
};

export default InternshipsToEvaluate;
