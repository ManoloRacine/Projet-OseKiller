import React from "react";
import PropTypes from "prop-types";

const EvaluateStudentForm = (props) => {
    return (
        <div className={"mt-4"}>
            <h1 className={"text-center"}>FICHE D'ÉVALUATION DU STAGIAIRE</h1>
            <form>
                <div className="section mb-3">
                    <label htmlFor="studentName" className="form-label">
                        Nom de l'élève
                    </label>
                    <input
                        type="text"
                        className="form-control mb-2"
                        id={"studentName"}
                    />
                    <label htmlFor="program" className="form-label">
                        Programme d'études
                    </label>
                    <input
                        type="text"
                        className="form-control mb-2"
                        id={"program"}
                    />
                    <label htmlFor="companyName" className="form-label">
                        Nom de l'entreprise
                    </label>
                    <input
                        type="text"
                        className="form-control mb-2"
                        id={"companyName"}
                    />
                    <label htmlFor="supervisorName" className="form-label">
                        Nom du superviseur
                    </label>
                    <input
                        type="text"
                        className="form-control mb-2"
                        id={"supervisorName"}
                    />
                    <div className={"d-flex mt-4"}>
                        <label
                            htmlFor="supervisorFunction"
                            className="form-label"
                        >
                            Fonction
                        </label>
                        <input
                            type="text"
                            className="form-control mb-2 me-2"
                            id={"supervisorFunction"}
                        />
                        <label htmlFor="phoneNumber" className="form-label">
                            Téléphone
                        </label>
                        <input
                            type="text"
                            className="form-control mb-2"
                            id={"phoneNumber"}
                        />
                    </div>
                </div>
                <div className="section mb-3">
                    <h2 className={"text-center"}>1. PRODUCTIVITÉ</h2>
                    <p className={"text-center"}>
                        Capacité d'optimiser son rendement au travail
                    </p>
                    <p className={"fs-4"}>Le stagiaire a été en mesure de :</p>
                    <label htmlFor="field1.1" className="form-label">
                        Planifier et organiser son travail de façon efficace
                    </label>
                    <select id="field1.1" className="form-select mb-2">
                        <option>Totalement en accord</option>
                        <option>Plutôt en accord</option>
                        <option>Plutôt en désaccord</option>
                        <option>Totalement en désaccord</option>
                        <option>N/A</option>
                    </select>
                    <label htmlFor="field1.2" className="form-label">
                        Comprendre rapidement les directives relatives à son
                        travail
                    </label>
                    <select id="field1.2" className="form-select mb-2">
                        <option>Totalement en accord</option>
                        <option>Plutôt en accord</option>
                        <option>Plutôt en désaccord</option>
                        <option>Totalement en désaccord</option>
                        <option>N/A</option>
                    </select>
                    <label htmlFor="field1.3" className="form-label">
                        Maintenir un rythme de travail soutenu
                    </label>
                    <select id="field1.3" className="form-select mb-2">
                        <option>Totalement en accord</option>
                        <option>Plutôt en accord</option>
                        <option>Plutôt en désaccord</option>
                        <option>Totalement en désaccord</option>
                        <option>N/A</option>
                    </select>
                    <label htmlFor="field1.4" className="form-label">
                        Établir ses priorités
                    </label>
                    <select id="field1.4" className="form-select mb-2">
                        <option>Totalement en accord</option>
                        <option>Plutôt en accord</option>
                        <option>Plutôt en désaccord</option>
                        <option>Totalement en désaccord</option>
                        <option>N/A</option>
                    </select>
                    <label htmlFor="field1.5" className="form-label">
                        Respecter ses échéanciers
                    </label>
                    <select id="field1.5" className="form-select mb-2">
                        <option>Totalement en accord</option>
                        <option>Plutôt en accord</option>
                        <option>Plutôt en désaccord</option>
                        <option>Totalement en désaccord</option>
                        <option>N/A</option>
                    </select>
                    <label htmlFor="section1Comment">Commentaire</label>
                    <textarea
                        name="section1Comment"
                        id="section1Comment"
                        className={"form-control mb-2"}
                    ></textarea>
                </div>

                <div className="section mb-3">
                    <h2 className={"text-center"}>2. QUALITÉ DU TRAVAIL</h2>
                    <p className={"text-center"}>
                        Capacité de s'acquitter des tâches sous sa
                        responsabilité en s'imposant personnellement des normes
                        de qualité
                    </p>
                    <p className={"fs-4"}>Le stagiaire a été en mesure de :</p>
                    <label htmlFor="field2.1" className="form-label">
                        Respecter les mandats qui lui ont été confiés
                    </label>
                    <select id="field2.1" className="form-select mb-2">
                        <option>Totalement en accord</option>
                        <option>Plutôt en accord</option>
                        <option>Plutôt en désaccord</option>
                        <option>Totalement en désaccord</option>
                        <option>N/A</option>
                    </select>
                    <label htmlFor="field2.2" className="form-label">
                        Porter attention aux détails dans la réalisation de ses
                        tâches
                    </label>
                    <select id="field2.2" className="form-select mb-2">
                        <option>Totalement en accord</option>
                        <option>Plutôt en accord</option>
                        <option>Plutôt en désaccord</option>
                        <option>Totalement en désaccord</option>
                        <option>N/A</option>
                    </select>
                    <label htmlFor="field2.3" className="form-label">
                        Vérifier son travail, s’assurer que rien n’a été oublié
                    </label>
                    <select id="field2.3" className="form-select mb-2">
                        <option>Totalement en accord</option>
                        <option>Plutôt en accord</option>
                        <option>Plutôt en désaccord</option>
                        <option>Totalement en désaccord</option>
                        <option>N/A</option>
                    </select>
                    <label htmlFor="field2.4" className="form-label">
                        Rechercher des occasions de se perfectionner
                    </label>
                    <select id="field2.4" className="form-select mb-2">
                        <option>Totalement en accord</option>
                        <option>Plutôt en accord</option>
                        <option>Plutôt en désaccord</option>
                        <option>Totalement en désaccord</option>
                        <option>N/A</option>
                    </select>
                    <label htmlFor="field2.5" className="form-label">
                        Faire une bonne analyse des problèmes rencontrés
                    </label>
                    <select id="field2.5" className="form-select mb-2">
                        <option>Totalement en accord</option>
                        <option>Plutôt en accord</option>
                        <option>Plutôt en désaccord</option>
                        <option>Totalement en désaccord</option>
                        <option>N/A</option>
                    </select>
                    <label htmlFor="section2Comment">Commentaire</label>
                    <textarea
                        name="section2Comment"
                        id="section2Comment"
                        className={"form-control mb-2"}
                    ></textarea>
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
                    <label htmlFor="field3.1" className="form-label">
                        Établir facilement des contacts avec les gens
                    </label>
                    <select id="field3.1" className="form-select mb-2">
                        <option>Totalement en accord</option>
                        <option>Plutôt en accord</option>
                        <option>Plutôt en désaccord</option>
                        <option>Totalement en désaccord</option>
                        <option>N/A</option>
                    </select>
                    <label htmlFor="field3.2" className="form-label">
                        Contribuer activement au travail d’équipe
                    </label>
                    <select id="field3.2" className="form-select mb-2">
                        <option>Totalement en accord</option>
                        <option>Plutôt en accord</option>
                        <option>Plutôt en désaccord</option>
                        <option>Totalement en désaccord</option>
                        <option>N/A</option>
                    </select>
                    <label htmlFor="field3.3" className="form-label">
                        S’adapter facilement à la culture de l’entreprise
                    </label>
                    <select id="field3.3" className="form-select mb-2">
                        <option>Totalement en accord</option>
                        <option>Plutôt en accord</option>
                        <option>Plutôt en désaccord</option>
                        <option>Totalement en désaccord</option>
                        <option>N/A</option>
                    </select>
                    <label htmlFor="field3.4" className="form-label">
                        Accepter les critiques constructives
                    </label>
                    <select id="field3.4" className="form-select mb-2">
                        <option>Totalement en accord</option>
                        <option>Plutôt en accord</option>
                        <option>Plutôt en désaccord</option>
                        <option>Totalement en désaccord</option>
                        <option>N/A</option>
                    </select>
                    <label htmlFor="field3.5" className="form-label">
                        Être respectueux envers les gens
                    </label>
                    <select id="field3.5" className="form-select mb-2">
                        <option>Totalement en accord</option>
                        <option>Plutôt en accord</option>
                        <option>Plutôt en désaccord</option>
                        <option>Totalement en désaccord</option>
                        <option>N/A</option>
                    </select>
                    <label htmlFor="field3.6" className="form-label">
                        Faire preuve d’écoute active en essayant de comprendre
                        le point de vue de l’autre
                    </label>
                    <select id="field3.6" className="form-select mb-2">
                        <option>Totalement en accord</option>
                        <option>Plutôt en accord</option>
                        <option>Plutôt en désaccord</option>
                        <option>Totalement en désaccord</option>
                        <option>N/A</option>
                    </select>
                    <label htmlFor="section3Comment">Commentaire</label>
                    <textarea
                        name="section3Comment"
                        id="section3Comment"
                        className={"form-control mb-2"}
                    ></textarea>
                </div>

                <div className="section mb-3">
                    <h2 className={"text-center"}>4. HABILETÉS PERSONNELLES</h2>
                    <p className={"text-center"}>
                        Capacité de faire preuve d’attitudes ou de comportements
                        matures et responsables
                    </p>
                    <p className={"fs-4"}>Le stagiaire a été en mesure de :</p>
                    <label htmlFor="field4.1" className="form-label">
                        Démontrer de l’intérêt et de la motivation au travail
                    </label>
                    <select id="field4.1" className="form-select mb-2">
                        <option>Totalement en accord</option>
                        <option>Plutôt en accord</option>
                        <option>Plutôt en désaccord</option>
                        <option>Totalement en désaccord</option>
                        <option>N/A</option>
                    </select>
                    <label htmlFor="field4.2" className="form-label">
                        Exprimer clairement ses idées
                    </label>
                    <select id="field4.2" className="form-select mb-2">
                        <option>Totalement en accord</option>
                        <option>Plutôt en accord</option>
                        <option>Plutôt en désaccord</option>
                        <option>Totalement en désaccord</option>
                        <option>N/A</option>
                    </select>
                    <label htmlFor="field4.3" className="form-label">
                        Faire preuve d’initiative
                    </label>
                    <select id="field4.3" className="form-select mb-2">
                        <option>Totalement en accord</option>
                        <option>Plutôt en accord</option>
                        <option>Plutôt en désaccord</option>
                        <option>Totalement en désaccord</option>
                        <option>N/A</option>
                    </select>
                    <label htmlFor="field4.4" className="form-label">
                        Travailler de façon sécuritaire
                    </label>
                    <select id="field4.4" className="form-select mb-2">
                        <option>Totalement en accord</option>
                        <option>Plutôt en accord</option>
                        <option>Plutôt en désaccord</option>
                        <option>Totalement en désaccord</option>
                        <option>N/A</option>
                    </select>
                    <label htmlFor="field4.5" className="form-label">
                        Démontrer un bon sens des responsabilités ne requérant
                        qu’un minimum de supervision
                    </label>
                    <select id="field4.5" className="form-select mb-2">
                        <option>Totalement en accord</option>
                        <option>Plutôt en accord</option>
                        <option>Plutôt en désaccord</option>
                        <option>Totalement en désaccord</option>
                        <option>N/A</option>
                    </select>
                    <label htmlFor="field4.6" className="form-label">
                        Être ponctuel et assidu à son travail
                    </label>
                    <select id="field4.6" className="form-select mb-2">
                        <option>Totalement en accord</option>
                        <option>Plutôt en accord</option>
                        <option>Plutôt en désaccord</option>
                        <option>Totalement en désaccord</option>
                        <option>N/A</option>
                    </select>
                    <label htmlFor="section4Comment">Commentaire</label>
                    <textarea
                        name="section4Comment"
                        id="section4Comment"
                        className={"form-control mb-2"}
                    ></textarea>
                </div>
            </form>
        </div>
    );
};

EvaluateStudentForm.propTypes = {};

export default EvaluateStudentForm;
