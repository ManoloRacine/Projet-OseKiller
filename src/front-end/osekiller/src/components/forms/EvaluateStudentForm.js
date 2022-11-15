import React from "react";
import PropTypes from "prop-types";
import TextInput from "./TextInput";
import SelectInput from "./SelectInput";
import TextareaInput from "./TextareaInput";

const EvaluateStudentForm = ({ formData, setFormData, submit }) => {
    return (
        <div className={"mt-4"}>
            <h1 className={"text-center"}>FICHE D'ÉVALUATION DU STAGIAIRE</h1>
            <form onSubmit={submit}>
                <div className="section mb-3">
                    <TextInput
                        label={"Nom de l'élève"}
                        inputName={"studentName"}
                        inputValue={formData.studentName}
                        changeInputValue={({ target }) =>
                            setFormData({
                                ...formData,
                                studentName: target.value,
                            })
                        }
                    />
                    <TextInput
                        label={"Programme d'études"}
                        inputName={"program"}
                        inputValue={formData.program}
                        changeInputValue={({ target }) =>
                            setFormData({
                                ...formData,
                                program: target.value,
                            })
                        }
                    />
                    <TextInput
                        label={"Nom de l'entreprise"}
                        inputName={"companyName"}
                        inputValue={formData.companyName}
                        changeInputValue={({ target }) =>
                            setFormData({
                                ...formData,
                                companyName: target.value,
                            })
                        }
                    />
                    <TextInput
                        label={"Nom du superviseur"}
                        inputName={"supervisorName"}
                        inputValue={formData.supervisorName}
                        changeInputValue={({ target }) =>
                            setFormData({
                                ...formData,
                                supervisorName: target.value,
                            })
                        }
                    />
                    <div className={"d-flex mt-4"}>
                        <TextInput
                            label={"Fonction"}
                            inputName={"supervisorPosition"}
                            inputValue={formData.supervisorPosition}
                            changeInputValue={({ target }) =>
                                setFormData({
                                    ...formData,
                                    supervisorPosition: target.value,
                                })
                            }
                        />
                        <TextInput
                            label={"Téléphone"}
                            inputName={"phone"}
                            inputValue={formData.phone}
                            changeInputValue={({ target }) =>
                                setFormData({
                                    ...formData,
                                    phone: target.value,
                                })
                            }
                        />
                    </div>
                </div>

                <div className="section mb-3">
                    <h2 className={"text-center"}>1. PRODUCTIVITÉ</h2>
                    <p className={"text-center"}>
                        Capacité d'optimiser son rendement au travail
                    </p>
                    <p className={"fs-4"}>Le stagiaire a été en mesure de :</p>
                    <SelectInput
                        label={
                            "Planifier et organiser son travail de façon efficace"
                        }
                        inputName={"field1.1"}
                        inputValue={formData["field1.1"]}
                        changeInputValue={({ target }) =>
                            setFormData({
                                ...formData,
                                "field1.1": target.value,
                            })
                        }
                    />
                    <SelectInput
                        label={
                            "Comprendre rapidement les directives relatives à son travail"
                        }
                        inputName={"field1.2"}
                        inputValue={formData["field1.2"]}
                        changeInputValue={({ target }) =>
                            setFormData({
                                ...formData,
                                "field1.2": target.value,
                            })
                        }
                    />
                    <SelectInput
                        label={"Maintenir un rythme de travail soutenu"}
                        inputName={"field1.3"}
                        inputValue={formData["field1.3"]}
                        changeInputValue={({ target }) =>
                            setFormData({
                                ...formData,
                                "field1.3": target.value,
                            })
                        }
                    />
                    <SelectInput
                        label={"Établir ses priorités"}
                        inputName={"field1.4"}
                        inputValue={formData["field1.4"]}
                        changeInputValue={({ target }) =>
                            setFormData({
                                ...formData,
                                "field1.4": target.value,
                            })
                        }
                    />
                    <SelectInput
                        label={"Respecter ses échéanciers"}
                        inputName={"field1.5"}
                        inputValue={formData["field1.5"]}
                        changeInputValue={({ target }) =>
                            setFormData({
                                ...formData,
                                "field1.5": target.value,
                            })
                        }
                    />
                    <TextareaInput
                        inputName={"section1Comment"}
                        inputValue={formData["section1Comment"]}
                        changeInputValue={({ target }) => {
                            setFormData({
                                ...formData,
                                section1Comment: target.value,
                            });
                        }}
                    />
                </div>

                <div className="section mb-3">
                    <h2 className={"text-center"}>2. QUALITÉ DU TRAVAIL</h2>
                    <p className={"text-center"}>
                        Capacité de s'acquitter des tâches sous sa
                        responsabilité en s'imposant personnellement des normes
                        de qualité
                    </p>
                    <p className={"fs-4"}>Le stagiaire a été en mesure de :</p>
                    <SelectInput
                        label={"Respecter les mandats qui lui ont été confiés"}
                        inputName={"field2.1"}
                        inputValue={formData["field2.1"]}
                        changeInputValue={({ target }) =>
                            setFormData({
                                ...formData,
                                "field2.1": target.value,
                            })
                        }
                    />
                    <SelectInput
                        label={
                            "Porter attention aux détails dans la réalisation de ses tâches"
                        }
                        inputName={"field2.2"}
                        inputValue={formData["field2.2"]}
                        changeInputValue={({ target }) =>
                            setFormData({
                                ...formData,
                                "field2.2": target.value,
                            })
                        }
                    />
                    <SelectInput
                        label={
                            "Vérifier son travail, s’assurer que rien n’a été oublié"
                        }
                        inputName={"field2.3"}
                        inputValue={formData["field2.3"]}
                        changeInputValue={({ target }) =>
                            setFormData({
                                ...formData,
                                "field2.3": target.value,
                            })
                        }
                    />
                    <SelectInput
                        label={"Rechercher des occasions de se perfectionner"}
                        inputName={"field2.4"}
                        inputValue={formData["field2.4"]}
                        changeInputValue={({ target }) =>
                            setFormData({
                                ...formData,
                                "field2.4": target.value,
                            })
                        }
                    />
                    <SelectInput
                        label={
                            "Faire une bonne analyse des problèmes rencontrés"
                        }
                        inputName={"field2.5"}
                        inputValue={formData["field2.5"]}
                        changeInputValue={({ target }) =>
                            setFormData({
                                ...formData,
                                "field2.5": target.value,
                            })
                        }
                    />
                    <TextareaInput
                        inputName={"section2Comment"}
                        inputValue={formData["section2Comment"]}
                        changeInputValue={({ target }) => {
                            setFormData({
                                ...formData,
                                section2Comment: target.value,
                            });
                        }}
                    />
                </div>

                <div className="section mb-3">
                    <h2 className={"text-center"}>
                        3. QUALITÉS DES RELATIONS INTERPERSONNELLES
                    </h2>
                    <p className={"text-center"}>
                        Capacité d’établir des interrelations harmonieuses dans
                        son milieu de travail
                    </p>
                    <p className={"fs-4"}>Le stagiaire a été en mesure de :</p>
                    <SelectInput
                        label={"Établir facilement des contacts avec les gens"}
                        inputName={"field3.1"}
                        inputValue={formData["field3.1"]}
                        changeInputValue={({ target }) =>
                            setFormData({
                                ...formData,
                                "field3.1": target.value,
                            })
                        }
                    />
                    <SelectInput
                        label={"Contribuer activement au travail d’équipe"}
                        inputName={"field3.2"}
                        inputValue={formData["field3.2"]}
                        changeInputValue={({ target }) =>
                            setFormData({
                                ...formData,
                                "field3.2": target.value,
                            })
                        }
                    />
                    <SelectInput
                        label={
                            "S’adapter facilement à la culture de l’entreprise"
                        }
                        inputName={"field3.3"}
                        inputValue={formData["field3.3"]}
                        changeInputValue={({ target }) =>
                            setFormData({
                                ...formData,
                                "field3.3": target.value,
                            })
                        }
                    />
                    <SelectInput
                        label={"Accepter les critiques constructives"}
                        inputName={"field3.4"}
                        inputValue={formData["field3.4"]}
                        changeInputValue={({ target }) =>
                            setFormData({
                                ...formData,
                                "field3.4": target.value,
                            })
                        }
                    />
                    <SelectInput
                        label={"Être respectueux envers les gens"}
                        inputName={"field3.5"}
                        inputValue={formData["field3.5"]}
                        changeInputValue={({ target }) =>
                            setFormData({
                                ...formData,
                                "field3.5": target.value,
                            })
                        }
                    />
                    <SelectInput
                        label={
                            "Faire preuve d’écoute active en essayant de comprendre le point de vue de l’autre"
                        }
                        inputName={"field3.6"}
                        inputValue={formData["field3.6"]}
                        changeInputValue={({ target }) =>
                            setFormData({
                                ...formData,
                                "field3.6": target.value,
                            })
                        }
                    />
                    <TextareaInput
                        inputName={"section3Comment"}
                        inputValue={formData["section3Comment"]}
                        changeInputValue={({ target }) => {
                            setFormData({
                                ...formData,
                                section3Comment: target.value,
                            });
                        }}
                    />
                </div>

                <div className="section mb-3">
                    <h2 className={"text-center"}>4. HABILETÉS PERSONNELLES</h2>
                    <p className={"text-center"}>
                        Capacité de faire preuve d’attitudes ou de comportements
                        matures et responsables
                    </p>
                    <p className={"fs-4"}>Le stagiaire a été en mesure de :</p>
                    <SelectInput
                        label={
                            "Démontrer de l’intérêt et de la motivation au travail"
                        }
                        inputName={"field4.1"}
                        inputValue={formData["field4.1"]}
                        changeInputValue={({ target }) =>
                            setFormData({
                                ...formData,
                                "field4.1": target.value,
                            })
                        }
                    />
                    <SelectInput
                        label={"Exprimer clairement ses idées"}
                        inputName={"field4.2"}
                        inputValue={formData["field4.2"]}
                        changeInputValue={({ target }) =>
                            setFormData({
                                ...formData,
                                "field4.2": target.value,
                            })
                        }
                    />
                    <SelectInput
                        label={"Faire preuve d’initiative"}
                        inputName={"field4.3"}
                        inputValue={formData["field4.3"]}
                        changeInputValue={({ target }) =>
                            setFormData({
                                ...formData,
                                "field4.3": target.value,
                            })
                        }
                    />
                    <SelectInput
                        label={"Travailler de façon sécuritaire"}
                        inputName={"field4.4"}
                        inputValue={formData["field4.4"]}
                        changeInputValue={({ target }) =>
                            setFormData({
                                ...formData,
                                "field4.4": target.value,
                            })
                        }
                    />
                    <SelectInput
                        label={
                            "Démontrer un bon sens des responsabilités ne requérant qu’un minimum de supervision"
                        }
                        inputName={"field4.5"}
                        inputValue={formData["field4.5"]}
                        changeInputValue={({ target }) =>
                            setFormData({
                                ...formData,
                                "field4.5": target.value,
                            })
                        }
                    />
                    <SelectInput
                        label={"Être ponctuel et assidu à son travail"}
                        inputName={"field4.6"}
                        inputValue={formData["field4.6"]}
                        changeInputValue={({ target }) =>
                            setFormData({
                                ...formData,
                                "field4.6": target.value,
                            })
                        }
                    />
                    <TextareaInput
                        inputName={"section4Comment"}
                        inputValue={formData["section4Comment"]}
                        changeInputValue={({ target }) => {
                            setFormData({
                                ...formData,
                                section4Comment: target.value,
                            });
                        }}
                    />
                </div>

                <div className="section mb-3">
                    <SelectInput
                        label={"Appréciation globale du stagiaire"}
                        inputName={"globalAppreciation"}
                        inputValue={formData["globalAppreciation"]}
                        changeInputValue={({ target }) =>
                            setFormData({
                                ...formData,
                                globalAppreciation: target.value,
                            })
                        }
                        optionsValues={[
                            ["Veuillez choisir une réponse", ""],
                            [
                                "Les habiletés démontrées dépassent de beaucoup les attentes",
                                0,
                            ],
                            [
                                "Les habiletés démontrées dépassent les attentes",
                                1,
                            ],
                            [
                                "Les habiletés démontrées répondent pleinement aux attentes",
                                2,
                            ],
                            [
                                "Les habiletés démontrées répondent partiellement aux attentes",
                                3,
                            ],
                            [
                                "Les habiletés démontrées ne répondent pas aux attentes",
                                4,
                            ],
                        ]}
                    />
                    <TextareaInput
                        label={"Précisez votre appréciation"}
                        inputName={"section5Comment"}
                        inputValue={formData["section5Comment"]}
                        changeInputValue={({ target }) => {
                            setFormData({
                                ...formData,
                                section5Comment: target.value,
                            });
                        }}
                    />
                    <SelectInput
                        label={
                            "Cette évaluation a été discutée avec le stagiaire"
                        }
                        inputName={"hasBeenDiscussed"}
                        inputValue={formData["hasBeenDiscussed"]}
                        changeInputValue={({ target }) =>
                            setFormData({
                                ...formData,
                                hasBeenDiscussed: target.value,
                            })
                        }
                        optionsValues={[
                            ["Veuillez choisir une réponse", ""],
                            ["Oui", true],
                            ["Non", false],
                        ]}
                    />
                    <TextInput
                        label={
                            "Veuillez indiquer le nombre d’heures réel par semaine d’encadrement accordé au stagiaire"
                        }
                        inputName={"nbHoursPerWeekOfSupport"}
                        inputType={"number"}
                        inputValue={formData.nbHoursPerWeekOfSupport}
                        changeInputValue={({ target }) => {
                            setFormData({
                                ...formData,
                                nbHoursPerWeekOfSupport: target.value,
                            });
                        }}
                    />
                </div>

                <div className="section mb-3">
                    <SelectInput
                        label={
                            "L'entreprise aimerait accueillir cet élève pour son prochain stage"
                        }
                        inputName={"wouldLikeToRetakeStudent"}
                        inputValue={formData["wouldLikeToRetakeStudent"]}
                        changeInputValue={({ target }) =>
                            setFormData({
                                ...formData,
                                wouldLikeToRetakeStudent: target.value,
                            })
                        }
                        optionsValues={[
                            ["Veuillez choisir une réponse", ""],
                            ["Oui", 0],
                            ["Non", 1],
                            ["Peut-être", 2],
                        ]}
                    />
                    <TextareaInput
                        label={
                            "La formation technique du stagiaire était-elle suffisante pour accomplir le mandat de stage?"
                        }
                        inputName={"formationWasAdequate"}
                        inputValue={formData["formationWasAdequate"]}
                        changeInputValue={({ target }) => {
                            setFormData({
                                ...formData,
                                formationWasAdequate: target.value,
                            });
                        }}
                    />
                    <TextInput
                        label={"Date"}
                        inputName={"date"}
                        inputType={"date"}
                        inputValue={formData.date}
                        changeInputValue={({ target }) => {
                            setFormData({
                                ...formData,
                                date: target.value,
                            });
                        }}
                    />
                </div>
                <button className={"btn btn-primary"}>Soumettre</button>
            </form>
        </div>
    );
};

EvaluateStudentForm.propTypes = {
    formData: PropTypes.object.isRequired,
    setFormData: PropTypes.func.isRequired,
    submit: PropTypes.func.isRequired,
};

export default EvaluateStudentForm;
