import { useState } from "react";
import EvaluationForm from "../../components/forms/EvaluationForm";
import { useFormik } from "formik";
import * as Yup from "yup";

const InternshipEvaluation = () => {
    let evalArray = [];
    for (let index = 0; index < 10; index++) {
        evalArray.push(null);
    }
    const [formData, setFormData] = useState({ evaluation: evalArray });

    return <EvaluationForm formData={formData} setFormData={setFormData} />;
};

export default InternshipEvaluation;
