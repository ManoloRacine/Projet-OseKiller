
import React from "react";

import Snackbar from '@mui/material/Snackbar';
import MuiAlert from '@mui/material/Alert';

const Alert = React.forwardRef(function Alert(props, ref) {
    return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
  });

const SignUpForm = ({formikCompany, formikStudentOrManager, userType, isOpen, hasOpenError, handleClose, setUserType, title}, props) => {

    function formType(value) {
        switch (value) {
            case "etudiant":
            case "gestionnaire":
                return <form className="col sm-8" onSubmit={formikStudentOrManager.handleSubmit}>
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
                        <button className="btn" style={{backgroundColor : "#ee7600"}} type="submit">Soumettre</button>
                    </div>
                </form>
            default:
                return <h1>ERROR</h1>
        }
    }

    function userTypesUpperCase(type) {
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
            <div>
                <h1 className="display-1">{title}</h1>
                {userTypesUpperCase(userType)}
            </div>
            
            <div className="container py-5 text-white rounded" style={{backgroundColor : "#2C324C"}}>
                <div className="row">
                    <div className="col sm-2"></div>
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
            <Snackbar open={isOpen} autoHideDuration={6000} onClose={handleClose}>
                <Alert onClose={handleClose} severity="success" sx={{ width: '100%' }}>
                Votre demande a été envoyé !
                </Alert>
            </Snackbar>
            <Snackbar open={hasOpenError} autoHideDuration={6000} onClose={handleClose}>
                <Alert onClose={handleClose} severity="error" sx={{ width: '100%' }}>
                Il y a eu une erreur, la demande n'as pas été envoyé
                </Alert>
            </Snackbar>
        </div>
        
    ) ;
}

export default SignUpForm ;