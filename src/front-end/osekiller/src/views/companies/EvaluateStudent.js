import React, { useState } from "react";
import EvaluateStudentForm from "../../components/forms/EvaluateStudentForm";

const EvaluateStudent = () => {
    const [formData, setFormData] = useState({
        studentName: "",
        program: "",
        companyName: "",
        supervisorName: "",
        supervisorFunction: "",
        phone: "",

        "field1.1": "",
        "field1.2": "",
        "field1.3": "",
        "field1.4": "",
        "field1.5": "",
        section1Comment: "",

        "field2.1": "",
        "field2.2": "",
        "field2.3": "",
        "field2.4": "",
        "field2.5": "",
        section2Comment: "",

        "field3.1": "",
        "field3.2": "",
        "field3.3": "",
        "field3.4": "",
        "field3.5": "",
        "field3.6": "",
        section3Comment: "",

        "field4.1": "",
        "field4.2": "",
        "field4.3": "",
        "field4.4": "",
        "field4.5": "",
        "field4.6": "",
        section4Comment: "",
    });

    return (
        <EvaluateStudentForm formData={formData} setFormData={setFormData} />
    );
};

export default EvaluateStudent;
