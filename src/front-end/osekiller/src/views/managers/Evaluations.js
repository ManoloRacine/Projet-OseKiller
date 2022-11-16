import { useEffect, useState } from "react";
import { EvaluationCard } from "../../components/EvaluationCard";
import { getEvaluations } from "../../services/ManagerService";

const Evaluations = () => {
    const [evaluations, setEvaluations] = useState([]);

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
