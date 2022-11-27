import { useEffect, useState } from "react";
import { StudentEvaluationCard } from "../../components/StudentEvaluationCard";
import { getStudentEvaluations } from "../../services/ManagerService";

const StudentEvaluations = () => {
    const [evaluations, setEvaluations] = useState([]);

    useEffect(() => {
        getStudentEvaluations().then((response) => {
            setEvaluations(response.data);
        });
    }, []);

    return (
        <div>
            {evaluations.map((evaluation, index) => (
                <StudentEvaluationCard
                    contract={evaluation}
                    redirectTo={"/evaluation-detail"}
                />
            ))}
        </div>
    );
};

export default StudentEvaluations;
