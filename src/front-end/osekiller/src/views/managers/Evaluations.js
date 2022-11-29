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
            <h2 className="text-center">Évaluations des milieux de stages</h2>
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
