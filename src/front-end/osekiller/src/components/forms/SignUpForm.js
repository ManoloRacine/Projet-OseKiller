import React from "react";

import ErrorMessage from "../ErrorMessage";

const SignUpForm = ({formikCompany, formikStudentOrManager, userType, isOpen, hasOpenError, setUserType, title, changeForm}) => {

    const formType = () => {
        return (
            <form className="col sm-8" onSubmit={formikStudentOrManager.handleSubmit}>
                {userType === "compagnie" ? 
                <div className="input-group pb-2">
                    <input name="nom"
                        id="nom"
                        type="text"
                        className={`form-control ` + (formikCompany.touched.nom && formikCompany.errors.nom ? `is-invalid` : ``) + (formikCompany.touched.nom && !formikCompany.errors.nom ? `is-valid` : ``)}
                        onChange={formikCompany.handleChange}
                        value={formikCompany.values.nom} 
                        onBlur={formikCompany.handleBlur}
                        placeholder="NOM DE LA COMPAGNIE">
                    </input>
                    {formikCompany.touched.nom && formikCompany.errors.nom ? <div className="invalid-feedback">{formikCompany.errors.nom}</div> : null}
                </div> :
                <>
                    <div className="input-group pb-2">
                        <input name="nom"
                        id="nom"
                        type="text"
                        className={`form-control ` + (formikStudentOrManager.touched.nom && formikStudentOrManager.errors.nom ? `is-invalid` : ``) + (formikStudentOrManager.touched.nom && !formikStudentOrManager.errors.nom ? `is-valid` : ``)}
                        onChange={formikStudentOrManager.handleChange}
                        value={formikStudentOrManager.values.nom} 
                        onBlur={formikStudentOrManager.handleBlur}
                        placeholder="NOM">
                        </input>
                        {formikStudentOrManager.touched.nom && formikStudentOrManager.errors.nom ? <div className="invalid-feedback">{formikStudentOrManager.errors.nom}</div> : null}
                    </div>
                    <div className="input-group pb-2">
                        <input name="prenom"
                            id="prenom"
                            type="text"
                            className={`form-control ` + (formikStudentOrManager.touched.prenom && formikStudentOrManager.errors.prenom ? `is-invalid` : ``) + (formikStudentOrManager.touched.prenom && !formikStudentOrManager.errors.prenom ? `is-valid` : ``)}
                            onChange={formikStudentOrManager.handleChange}
                            value={formikStudentOrManager.values.prenom} 
                            onBlur={formikStudentOrManager.handleBlur}
                            placeholder="PRÉNOM">
                        </input>
                        {formikStudentOrManager.touched.prenom && formikStudentOrManager.errors.prenom ? <div className="invalid-feedback">{formikStudentOrManager.errors.prenom}</div> : null}
                    </div>
                </>
                }
                <div className="input-group pb-2">
                    <input name="email"
                        id="email"
                        type="text"
                        className={`form-control ` + (formikStudentOrManager.touched.email && formikStudentOrManager.errors.email ? `is-invalid` : ``) + (formikStudentOrManager.touched.email && !formikStudentOrManager.errors.email ? `is-valid` : ``)}
                        onChange={formikStudentOrManager.handleChange}
                        value={formikStudentOrManager.values.email} 
                        onBlur={formikStudentOrManager.handleBlur}
                        placeholder="E-MAIL">
                    </input>
                    {formikStudentOrManager.touched.email && formikStudentOrManager.errors.email ? <div className="invalid-feedback">{formikStudentOrManager.errors.email}</div> : null}
                </div>
                <div className="input-group pb-2">
                    <input name="password"
                        id="password"
                        type="password"
                        className={`form-control ` + (formikStudentOrManager.touched.password && formikStudentOrManager.errors.password ? `is-invalid` : ``) + (formikStudentOrManager.touched.password && !formikStudentOrManager.errors.password ? `is-valid` : ``)}
                        onChange={formikStudentOrManager.handleChange}
                        value={formikStudentOrManager.values.password} 
                        onBlur={formikStudentOrManager.handleBlur}
                        placeholder="MOT DE PASSE">
                    </input>
                    {formikStudentOrManager.touched.password && formikStudentOrManager.errors.password ? <div className="invalid-feedback">{formikStudentOrManager.errors.password}</div> : null}
                </div>
                <div className="input-group pb-2">
                    <input name="passwordConfirmation"
                        id="passwordConfirmation"
                        type="password"
                        className={`form-control ` + (formikStudentOrManager.touched.passwordConfirmation && formikStudentOrManager.errors.passwordConfirmation ? `is-invalid` : ``) + 
                        (formikStudentOrManager.touched.passwordConfirmation && !formikStudentOrManager.errors.passwordConfirmation ? `is-valid` : ``)}
                        onChange={formikStudentOrManager.handleChange}
                        value={formikStudentOrManager.values.passwordConfirmation} 
                        onBlur={formikStudentOrManager.handleBlur}
                        placeholder="CONFIRMATION MOT DE PASSE">
                    </input>
                    {formikStudentOrManager.touched.passwordConfirmation && formikStudentOrManager.errors.passwordConfirmation ? <div className="invalid-feedback">{formikStudentOrManager.errors.passwordConfirmation}</div> : null}
                </div>
                <div className="input-group">
                    <button className="btn" style={{backgroundColor : "#ee7600"}} type="submit">Soumettre</button>
                </div>
            </form>
        )
    }

    const userTypesCapitalized = (type) => {
        switch (type) {
            case "etudiant":
                return <h2>Étudiant</h2>
            case "gestionnaire":
                return <h2>Gestionnaire</h2>
            case "compagnie":
                return <h2>Compagnie</h2>
            default:
                return "ERROR"
        }
    }

    return (
        <div className="d-flex flex-column justify-content-evenly align-items-center"
        style={{minHeight : "90vh"}}>
            <div className="text-center">
                <h1 className="display-1">{title}</h1>
                {userTypesCapitalized(userType)}
            </div>
            
            <div className="container py-5 text-white rounded" style={{backgroundColor : "#2C324C"}}>
                <div className="row">
                    <div className="col sm-2 d-flex flex-column align-items-center justify-content-center">   
                        <p>Vous avez déjà un compte?</p>
                        <button className="btn" style={{backgroundColor : "#ee7600"}} onClick={changeForm}>
                            Se connecter
                        </button>
                    </div>
                    {formType(userType)}
                    <div className="col sm-2">
                        <div className="row">
                            <div onClick={() => setUserType("etudiant")} className="mx-3 mb-2 w-50 btn" style={{backgroundColor : "#ee7600"}}>Étudiant</div>
                        </div>
                        <div className="row">
                            <div onClick={() => setUserType("gestionnaire")} className="mx-3 mb-2 w-50 btn" style={{backgroundColor : "#ee7600"}}>Gestionnaire</div>
                        </div>
                        <div className="row">
                            <div onClick={() => setUserType("compagnie")} className="mx-3 mb-2 w-50 btn" style={{backgroundColor : "#ee7600"}}>Compagnie</div>
                        </div>
                    </div>
                </div>
            </div>
            {isOpen && <ErrorMessage message={"Votre demande a été envoyée !"} severity="success" />}
            {hasOpenError && <ErrorMessage message={"Il y a eu une erreur, la demande n'a pas été envoyée."} severity="error" />}
        </div>     
    ) ;
}

export default SignUpForm ;
