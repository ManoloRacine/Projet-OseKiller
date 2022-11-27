import React from "react";

import ErrorMessage from "../ErrorMessage";

const SignUpForm = ({
    usedFormik,
    userType,
    isOpen,
    hasOpenError,
    setUserType,
    title,
    changeForm,
}) => {
    const formType = () => {
        return (
            <form className="col sm-8" onSubmit={usedFormik.handleSubmit}>
                {userType === "compagnie" ? (
                    <div className="input-group pb-2">
                        <input
                            name="nom"
                            id="nom"
                            type="text"
                            className={
                                `form-control ` +
                                (usedFormik.touched.nom && usedFormik.errors.nom
                                    ? `is-invalid`
                                    : ``) +
                                (usedFormik.touched.nom &&
                                !usedFormik.errors.nom
                                    ? `is-valid`
                                    : ``)
                            }
                            onChange={usedFormik.handleChange}
                            value={usedFormik.values.nom}
                            onBlur={usedFormik.handleBlur}
                            placeholder="NOM DE LA COMPAGNIE"
                        ></input>
                        {usedFormik.touched.nom && usedFormik.errors.nom ? (
                            <div className="invalid-feedback">
                                {usedFormik.errors.nom}
                            </div>
                        ) : null}
                    </div>
                ) : (
                    <>
                        <div className="input-group pb-2">
                            <input
                                name="nom"
                                id="nom"
                                type="text"
                                className={
                                    `form-control ` +
                                    (usedFormik.touched.nom &&
                                    usedFormik.errors.nom
                                        ? `is-invalid`
                                        : ``) +
                                    (usedFormik.touched.nom &&
                                    !usedFormik.errors.nom
                                        ? `is-valid`
                                        : ``)
                                }
                                onChange={usedFormik.handleChange}
                                value={usedFormik.values.nom}
                                onBlur={usedFormik.handleBlur}
                                placeholder="NOM"
                            ></input>
                            {usedFormik.touched.nom && usedFormik.errors.nom ? (
                                <div className="invalid-feedback">
                                    {usedFormik.errors.nom}
                                </div>
                            ) : null}
                        </div>
                        <div className="input-group pb-2">
                            <input
                                name="prenom"
                                id="prenom"
                                type="text"
                                className={
                                    `form-control ` +
                                    (usedFormik.touched.prenom &&
                                    usedFormik.errors.prenom
                                        ? `is-invalid`
                                        : ``) +
                                    (usedFormik.touched.prenom &&
                                    !usedFormik.errors.prenom
                                        ? `is-valid`
                                        : ``)
                                }
                                onChange={usedFormik.handleChange}
                                value={usedFormik.values.prenom}
                                onBlur={usedFormik.handleBlur}
                                placeholder="PRÉNOM"
                            ></input>
                            {usedFormik.touched.prenom &&
                            usedFormik.errors.prenom ? (
                                <div className="invalid-feedback">
                                    {usedFormik.errors.prenom}
                                </div>
                            ) : null}
                        </div>
                    </>
                )}
                <div className="input-group pb-2">
                    <input
                        name="email"
                        id="email"
                        type="text"
                        className={
                            `form-control ` +
                            (usedFormik.touched.email && usedFormik.errors.email
                                ? `is-invalid`
                                : ``) +
                            (usedFormik.touched.email &&
                            !usedFormik.errors.email
                                ? `is-valid`
                                : ``)
                        }
                        onChange={usedFormik.handleChange}
                        value={usedFormik.values.email}
                        onBlur={usedFormik.handleBlur}
                        placeholder="E-MAIL"
                    ></input>
                    {usedFormik.touched.email && usedFormik.errors.email ? (
                        <div className="invalid-feedback">
                            {usedFormik.errors.email}
                        </div>
                    ) : null}
                </div>
                <div className="input-group pb-2">
                    <input
                        name="password"
                        id="password"
                        type="password"
                        className={
                            `form-control ` +
                            (usedFormik.touched.password &&
                            usedFormik.errors.password
                                ? `is-invalid`
                                : ``) +
                            (usedFormik.touched.password &&
                            !usedFormik.errors.password
                                ? `is-valid`
                                : ``)
                        }
                        onChange={usedFormik.handleChange}
                        value={usedFormik.values.password}
                        onBlur={usedFormik.handleBlur}
                        placeholder="MOT DE PASSE"
                    ></input>
                    {usedFormik.touched.password &&
                    usedFormik.errors.password ? (
                        <div className="invalid-feedback">
                            {usedFormik.errors.password}
                        </div>
                    ) : null}
                </div>
                <div className="input-group pb-2">
                    <input
                        name="passwordConfirmation"
                        id="passwordConfirmation"
                        type="password"
                        className={
                            `form-control ` +
                            (usedFormik.touched.passwordConfirmation &&
                            usedFormik.errors.passwordConfirmation
                                ? `is-invalid`
                                : ``) +
                            (usedFormik.touched.passwordConfirmation &&
                            !usedFormik.errors.passwordConfirmation
                                ? `is-valid`
                                : ``)
                        }
                        onChange={usedFormik.handleChange}
                        value={usedFormik.values.passwordConfirmation}
                        onBlur={usedFormik.handleBlur}
                        placeholder="CONFIRMATION MOT DE PASSE"
                    ></input>
                    {usedFormik.touched.passwordConfirmation &&
                    usedFormik.errors.passwordConfirmation ? (
                        <div className="invalid-feedback">
                            {usedFormik.errors.passwordConfirmation}
                        </div>
                    ) : null}
                </div>
                <div className="input-group">
                    <button
                        className="btn"
                        style={{ backgroundColor: "#ee7600" }}
                        type="submit"
                    >
                        Soumettre
                    </button>
                </div>
            </form>
        );
    };

    const userTypesCapitalized = (type) => {
        switch (type) {
            case "etudiant":
                return <h2>Étudiant</h2>;
            case "gestionnaire":
                return <h2>Gestionnaire</h2>;
            case "compagnie":
                return <h2>Compagnie</h2>;
            case "professeur":
                return <h2>Professeur</h2>;
            default:
                return "ERROR";
        }
    };

    return (
        <div
            className="d-flex flex-column justify-content-evenly align-items-center"
            style={{ minHeight: "95vh" }}
        >
            <div className="text-center">
                <h1 className="display-1">{title}</h1>
                {userTypesCapitalized(userType)}
            </div>

            <div
                className="container py-5 text-white rounded"
                style={{ backgroundColor: "#2C324C" }}
            >
                <div className="row">
                    <div className="col sm-2 d-flex flex-column align-items-center justify-content-center">
                        <p>Vous avez déjà un compte?</p>
                        <button
                            className="btn"
                            style={{ backgroundColor: "#ee7600" }}
                            onClick={changeForm}
                        >
                            Se connecter
                        </button>
                    </div>
                    {formType(userType)}
                    <div className="col sm-2">
                        <div className="row">
                            <div
                                onClick={() => setUserType("etudiant")}
                                className="mx-3 mb-2 w-50 btn"
                                style={{ backgroundColor: "#ee7600" }}
                            >
                                Étudiant
                            </div>
                        </div>
                        <div className="row">
                            <div
                                onClick={() => setUserType("gestionnaire")}
                                className="mx-3 mb-2 w-50 btn"
                                style={{ backgroundColor: "#ee7600" }}
                            >
                                Gestionnaire
                            </div>
                        </div>
                        <div className="row">
                            <div
                                onClick={() => setUserType("compagnie")}
                                className="mx-3 mb-2 w-50 btn"
                                style={{ backgroundColor: "#ee7600" }}
                            >
                                Compagnie
                            </div>
                        </div>
                        <div className="row">
                            <div
                                onClick={() => setUserType("professeur")}
                                className="mx-3 mb-2 w-50 btn"
                                style={{ backgroundColor: "#ee7600" }}
                            >
                                Professeur
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            {isOpen && (
                <ErrorMessage
                    message={"Votre demande a été envoyée !"}
                    severity="success"
                />
            )}
            {hasOpenError && (
                <ErrorMessage
                    message={
                        "Il y a eu une erreur, la demande n'a pas été envoyée."
                    }
                    severity="error"
                />
            )}
        </div>
    );
};

export default SignUpForm;
