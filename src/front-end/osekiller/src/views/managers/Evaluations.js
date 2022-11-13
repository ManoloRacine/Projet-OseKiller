import { useEffect, useState } from "react";
import { EvaluationCard } from "../../components/EvaluationCard";
import { getEvaluations } from "../../services/ManagerService";

const Evaluations = () => {
    const [evaluations, setEvaluations] = useState([
        {
            contractId: 0,
            companyName: "Google",
            studentName: "Obama",
            position: "dev",
            startDate: "2022-10-20",
            endDate: "2022-12-20",
        },
        {
            contractId: 0,
            companyName: "Google",
            studentName: "Obama",
            position: "dev",
            startDate: "2022-10-20",
            endDate: "2022-12-20",
        },
        {
            contractId: 0,
            companyName: "Google",
            studentName: "Obama",
            position: "dev",
            startDate: "2022-10-20",
            endDate: "2022-12-20",
        },
    ]);

    useEffect(() => {
        getEvaluations().then((response) => {
            setEvaluations(response.data);
        });
    }, []);

    return (
        <div>
            {evaluations.map((evaluation, index) => (
                <EvaluationCard
                    contract={evaluation}
                    redirectTo={"/evaluation-detail"}
                />
            ))}
        </div>
    );
};

export default Evaluations;
