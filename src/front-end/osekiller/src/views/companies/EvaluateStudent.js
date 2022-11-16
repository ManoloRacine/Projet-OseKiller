import React, { useState } from "react";
import EvaluateStudentForm from "../../components/forms/EvaluateStudentForm";
import { useLocation } from "react-router-dom";
import { evaluateIntern } from "../../services/ContractService";
import ErrorMessage from "../../components/ErrorMessage";

const EvaluateStudent = () => {
    const location = useLocation();
    const studentName = location?.state?.studentName;
    const companyName = location?.state?.companyName;
    const contractId = location?.state?.contractId;
    const [errorMessage, setErrorMessage] = useState("");
    const [severity, setSeverity] = useState("");
    const [formData, setFormData] = useState({
        studentName: studentName ? studentName : "",
        program: "",
        companyName: companyName ? companyName : "",
        supervisorName: "",
        supervisorPosition: "",
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
        globalAppreciation: "",
        section5Comment: "",
        hasBeenDiscussed: "",
        nbHoursPerWeekOfSupport: "",
        wouldLikeToRetakeStudent: "",
        formationWasAdequate: "",
    });

    const handleSubmit = (e) => {
        e.preventDefault();
        const payload = {
            supervisorName: formData.supervisorName,
            supervisorPosition: formData.supervisorPosition,
            phoneNumber: formData.phone,
            productivity: {
                questionsAnswers: [
                    {
                        question:
                            "Planifier et organiser son travail de façon efficace",
                        answer: formData["field1.1"],
                    },
                    {
                        question:
                            "Comprendre rapidement les directives relatives à son travail",
                        answer: formData["field1.2"],
                    },
                    {
                        question: "Maintenir un rythme de travail soutenu",
                        answer: formData["field1.3"],
                    },
                    {
                        question: "Établir ses priorités",
                        answer: formData["field1.4"],
                    },
                    {
                        question: "Respecter ses échéanciers",
                        answer: formData["field1.5"],
                    },
                ],
                comments: formData.section1Comment,
            },
            workQuality: {
                questionsAnswers: [
                    {
                        question:
                            "Respecter les mandats qui lui ont été confiés",
                        answer: formData["field2.1"],
                    },
                    {
                        question:
                            "Porter attention aux détails dans la réalisation de ses tâches",
                        answer: formData["field2.2"],
                    },
                    {
                        question:
                            "Vérifier son travail, s’assurer que rien n’a été oublié",
                        answer: formData["field2.3"],
                    },
                    {
                        question:
                            "Rechercher des occasions de se perfectionner",
                        answer: formData["field2.4"],
                    },
                    {
                        question:
                            "Faire une bonne analyse des problèmes rencontrés",
                        answer: formData["field2.5"],
                    },
                ],
                comments: formData.section2Comment,
            },
            interpersonalQuality: {
                questionsAnswers: [
                    {
                        question:
                            "Établir facilement des contacts avec les gens",
                        answer: formData["field3.1"],
                    },
                    {
                        question: "Contribuer activement au travail d’équipe",
                        answer: formData["field3.2"],
                    },
                    {
                        question:
                            "S’adapter facilement à la culture de l’entreprise",
                        answer: formData["field3.3"],
                    },
                    {
                        question: "Accepter les critiques constructives",
                        answer: formData["field3.4"],
                    },
                    {
                        question: "Être respectueux envers les gens",
                        answer: formData["field3.5"],
                    },
                    {
                        question:
                            "Faire preuve d’écoute active en essayant de comprendre le point de vue de l’autre",
                        answer: formData["field3.6"],
                    },
                ],
                comments: formData.section3Comment,
            },
            personalAbility: {
                questionsAnswers: [
                    {
                        question:
                            "Démontrer de l’intérêt et de la motivation au travail",
                        answer: formData["field4.1"],
                    },
                    {
                        question: "Exprimer clairement ses idées",
                        answer: formData["field4.2"],
                    },
                    {
                        question: "Faire preuve d’initiative",
                        answer: formData["field4.3"],
                    },
                    {
                        question: "Travailler de façon sécuritaire",
                        answer: formData["field4.4"],
                    },
                    {
                        question:
                            "Démontrer un bon sens des responsabilités ne requérant qu’un minimum de supervision",
                        answer: formData["field4.5"],
                    },
                    {
                        question: "Être ponctuel et assidu à son travail",
                        answer: formData["field4.6"],
                    },
                ],
                comments: formData.section4Comment,
            },
            hoursOfSupportPerWeek: formData.nbHoursPerWeekOfSupport,
            expectationsAchieved: formData.globalAppreciation,
            expectationsComment: formData.section5Comment,
            internInformed: formData.hasBeenDiscussed,
            keepIntern: formData.wouldLikeToRetakeStudent,
            internFormationComment: formData.formationWasAdequate,
        };
        console.log(payload);
        evaluateIntern(contractId, payload)
            .then((response) => {
                setSeverity("success");
                setErrorMessage("Évaluation envoyée avec succès!");
                console.log(response);
            })
            .catch((err) => {
                setSeverity("error");
                setErrorMessage("Évaluation non envoyée.");
                console.log(err);
            });
    };

    return (
        <>
            <EvaluateStudentForm
                formData={formData}
                setFormData={setFormData}
                submit={handleSubmit}
            />
            {errorMessage && (
                <ErrorMessage message={errorMessage} severity={severity} />
            )}
        </>
    );
};

export default EvaluateStudent;
