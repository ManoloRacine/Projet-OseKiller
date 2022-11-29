import { useEffect, useState } from "react";
import { StudentEvaluationCard } from "../../components/StudentEvaluationCard";
import { getStudentEvaluations } from "../../services/ManagerService";

export const StudentEvaluations = () => {
    const [evaluations, setEvaluations] = useState([]);

    useEffect(() => {
        getStudentEvaluations().then((response) => {
            setEvaluations(response.data);
        });
    }, []);

    return (
        <div>
            <h2 className="text-center">Évaluation des stagiaires</h2>
            {evaluations.map((evaluation, index) => (
                <StudentEvaluationCard
                    contract={evaluation}
                    redirectTo={"/intern-evaluation-detail"}
                />
            ))}
        </div>
    );
};

export default StudentEvaluations;
