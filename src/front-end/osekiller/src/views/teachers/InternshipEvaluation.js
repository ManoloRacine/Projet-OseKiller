import { useState } from "react";
import EvaluationForm from "../../components/forms/EvaluationForm";
import { useFormik } from "formik";
import * as Yup from "yup";

const evaluationQuestions = [
    "Les tâches confiées au stagiaire sont conformes aux tâches annoncées dans l’entente de stage.",
    "Des mesures d’accueil facilitent l’intégration du nouveau stagiaire.",
    "Le temps réel consacré à l’encadrement du stagiaire est suffisant.",
    "L’environnement de travail respecte les normes d’hygiène et de sécurité au travail.",
    "Le climat de travail est agréable.",
    "Le milieu de stage est accessible par transport en commun.",
    "Le salaire offert est intéressant pour le stagiaire.",
    "La communication avec le superviseur de stage facilite le déroulement du stage.",
    "L’équipement fourni est adéquat pour réaliser les tâches confiées.",
    "Le volume de travail est acceptable.",
];

const InternshipEvaluation = () => {
    let evalArray = [];
    for (let index = 0; index < evaluationQuestions.length; index++) {
        evalArray.push(null);
    }
    const [formData, setFormData] = useState({ evaluation: evalArray });

    return (
        <EvaluationForm
            formData={formData}
            setFormData={setFormData}
            evaluationQuestions={evaluationQuestions}
        />
    );
};

export default InternshipEvaluation;
