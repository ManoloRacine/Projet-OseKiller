import { useState } from "react";
import React from "react";
import { useFormik } from 'formik';
import * as Yup from "yup"

const SignUpForm = (props) => {
    const [userType, setUserType] = useState("etudiant");
    
    const formikNormal = useFormik({
        initialValues : {
            nom : "",
            prenom : "",
            email : "",
            password : "",
            passwordConfirmation : ""
        },
        validationSchema : Yup.object({
            nom : Yup.string().required("Requis"),
            prenom : Yup.string().required("Requis"),
            email : Yup.string().required("Requis").email("Pas un email valide"),
            password : Yup.string().required("Requis"),
            passwordConfirmation : Yup.string().required("Requis").oneOf([Yup.ref('password')], "le mot de passe n'est pas le même"),
        }),
        onSubmit: values => {
            console.log(`${process.env.REACT_APP_SERVER_ADRESS}`)
          },
    });

    const formikCompany = useFormik({
        initialValues : {
            nom : "",
            email : "",
            password : "",
            passwordConfirmation : ""
        },
        validationSchema : Yup.object({
            nom : Yup.string().required("Requis"),
            email : Yup.string().required("Requis").email("Pas un email valide"),
            password : Yup.string().required("Requis"),
            passwordConfirmation : Yup.string().required("Requis").oneOf([Yup.ref('password')], "le mot de passe n'est pas le même"),
        }),
        onSubmit: values => {
            alert(JSON.stringify(values, null, 2));
          },
    });


    function formType(value) {
        switch (value) {
            case "etudiant":
            case "gestionnaire":
                return <form className="col sm-8" onSubmit={formikNormal.handleSubmit}>
                        <div className="input-group pb-2">
                            <input name="nom"
                            id="nom"
                            type="text"
                            className={`form-control ` + (formikNormal.touched.nom && formikNormal.errors.nom ? `is-invalid` : ``) + (formikNormal.touched.nom && !formikNormal.errors.nom ? `is-valid` : ``)}
                            onChange={formikNormal.handleChange}
                            value={formikNormal.values.nom} 
                            onBlur={formikNormal.handleBlur}
                            placeholder="NOM">
                            </input>
                            {formikNormal.touched.nom && formikNormal.errors.nom ? <div className="invalid-feedback">{formikNormal.errors.nom}</div> : null}
                        </div>
                        <div className="input-group pb-2">
                        <input name="prenom"
                            id="prenom"
                            type="text"
                            className={`form-control ` + (formikNormal.touched.prenom && formikNormal.errors.prenom ? `is-invalid` : ``) + (formikNormal.touched.prenom && !formikNormal.errors.prenom ? `is-valid` : ``)}
                            onChange={formikNormal.handleChange}
                            value={formikNormal.values.prenom} 
                            onBlur={formikNormal.handleBlur}
                            placeholder="PRÉNOM">
                            </input>
                            {formikNormal.touched.prenom && formikNormal.errors.prenom ? <div className="invalid-feedback">{formikNormal.errors.prenom}</div> : null}
                        </div>
                        <div className="input-group pb-2">
                        <input name="email"
                            id="email"
                            type="text"
                            className={`form-control ` + (formikNormal.touched.email && formikNormal.errors.email ? `is-invalid` : ``) + (formikNormal.touched.email && !formikNormal.errors.email ? `is-valid` : ``)}
                            onChange={formikNormal.handleChange}
                            value={formikNormal.values.email} 
                            onBlur={formikNormal.handleBlur}
                            placeholder="E-MAIL">
                            </input>
                            {formikNormal.touched.email && formikNormal.errors.email ? <div className="invalid-feedback">{formikNormal.errors.email}</div> : null}
                        </div>
                        <div className="input-group pb-2">
                        <input name="password"
                            id="password"
                            type="password"
                            className={`form-control ` + (formikNormal.touched.password && formikNormal.errors.password ? `is-invalid` : ``) + (formikNormal.touched.password && !formikNormal.errors.password ? `is-valid` : ``)}
                            onChange={formikNormal.handleChange}
                            value={formikNormal.values.password} 
                            onBlur={formikNormal.handleBlur}
                            placeholder="MOT DE PASSE">
                            </input>
                            {formikNormal.touched.password && formikNormal.errors.password ? <div className="invalid-feedback">{formikNormal.errors.password}</div> : null}
                        </div>
                        <div className="input-group pb-2">
                        <input name="passwordConfirmation"
                            id="passwordConfirmation"
                            type="password"
                            className={`form-control ` + (formikNormal.touched.passwordConfirmation && formikNormal.errors.passwordConfirmation ? `is-invalid` : ``) + 
                            (formikNormal.touched.passwordConfirmation && !formikNormal.errors.passwordConfirmation ? `is-valid` : ``)}
                            onChange={formikNormal.handleChange}
                            value={formikNormal.values.passwordConfirmation} 
                            onBlur={formikNormal.handleBlur}
                            placeholder="CONFIRMATION MOT DE PASSE">
                            </input>
                            {formikNormal.touched.passwordConfirmation && formikNormal.errors.passwordConfirmation ? <div className="invalid-feedback">{formikNormal.errors.passwordConfirmation}</div> : null}
                        </div>
                        <div className="input-group">
                            <button className="btn btn-primary" type="submit">Soumettre</button>
                        </div>
                    </form>
            case "compagnie":
                return <form className="col sm-8" onSubmit={formikCompany.handleSubmit}>
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
                    </div>
                    <div className="input-group pb-2">
                    <input name="email"
                        id="email"
                        type="text"
                        className={`form-control ` + (formikCompany.touched.email && formikCompany.errors.email ? `is-invalid` : ``) + (formikCompany.touched.email && !formikCompany.errors.email ? `is-valid` : ``)}
                        onChange={formikCompany.handleChange}
                        value={formikCompany.values.email} 
                        onBlur={formikCompany.handleBlur}
                        placeholder="E-MAIL">
                        </input>
                        {formikCompany.touched.email && formikCompany.errors.email ? <div className="invalid-feedback">{formikCompany.errors.email}</div> : null}
                    </div>
                    <div className="input-group pb-2">
                    <input name="password"
                        id="password"
                        type="password"
                        className={`form-control ` + (formikCompany.touched.password && formikCompany.errors.password ? `is-invalid` : ``) + (formikCompany.touched.password && !formikCompany.errors.password ? `is-valid` : ``)}
                        onChange={formikCompany.handleChange}
                        value={formikCompany.values.password} 
                        onBlur={formikCompany.handleBlur}
                        placeholder="MOT DE PASSE">
                        </input>
                        {formikCompany.touched.password && formikCompany.errors.password ? <div className="invalid-feedback">{formikCompany.errors.password}</div> : null}
                    </div>
                    <div className="input-group pb-2">
                    <input name="passwordConfirmation"
                        id="passwordConfirmation"
                        type="password"
                        className={`form-control ` + (formikCompany.touched.passwordConfirmation && formikCompany.errors.passwordConfirmation ? `is-invalid` : ``) +
                         (formikCompany.touched.passwordConfirmation && !formikCompany.errors.passwordConfirmation ? `is-valid` : ``)}
                        onChange={formikCompany.handleChange}
                        value={formikCompany.values.passwordConfirmation} 
                        onBlur={formikCompany.handleBlur}
                        placeholder="CONFIRMATION MOT DE PASSE">
                        </input>
                        {formikCompany.touched.passwordConfirmation && formikCompany.errors.passwordConfirmation ? <div className="invalid-feedback">{formikCompany.errors.passwordConfirmation}</div> : null}
                    </div>
                    <div className="input-group">
                        <button className="btn btn-primary" type="submit">Soumettre</button>
                    </div>
                </form>
            default:
                return <h1>ERROR</h1>
        }
    }

    function userTypesInFrench(type) {
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
        <div className="container">
            <h1>{props.title}</h1>
            {userTypesInFrench(userType)}
            <div className="row">
                <div className="col sm-2"></div>
                {formType(userType)}
                <div className="col sm-2">
                    <div className="row">
                        <div onClick={() => setUserType("etudiant")} className="mx-3 mb-2 btn btn-primary">Étudiant</div>
                    </div>
                    <div className="row">
                        <div onClick={() => setUserType("gestionnaire")} className="mx-3 mb-2 btn btn-primary">Gestionnaire</div>
                    </div>
                    <div className="row">
                        <div onClick={() => setUserType("compagnie")} className="mx-3 mb-2 btn btn-primary">Compagnie</div>
                    </div>
                </div>
            </div>
        </div>
        
    ) ;
}

export default SignUpForm ;