import { useState } from "react";

const SignUpForm = (props) => {
    const [userType, setUserType] = useState("etudiant");

    function formType(value) {
        switch (value) {
            case "etudiant":
            case "gestionnaire":
                return <div className="col sm-8">
                        <div className="input-group pb-2">
                            <input className="form-control" placeholder="NOM"></input>
                        </div>
                        <div className="input-group pb-2">
                            <input className="form-control" placeholder="PRÉNOM"></input>
                        </div>
                        <div className="input-group pb-2">
                            <input className="form-control" placeholder="E-MAIL"></input>
                        </div>
                        <div className="input-group pb-2">
                            <input className="form-control" placeholder="MOT DE PASSE"></input>
                        </div>
                        <div className="input-group pb-2">
                            <input className="form-control" placeholder="CONFIRMATION MOT DE PASSE"></input>
                        </div>
                        <div className="my-3">
                            <p>Vous avez déjà un compte?</p>
                            <button className="btn btn-primary" onClick={props.changeForm}>
                                Se connecter
                            </button>
                        </div>
                    </div>
            case "compagnie":
                return <div className="col sm-8">
                    <div className="input-group pb-2">
                        <input className="form-control" placeholder="NOM DE LA COMPAGNIE"></input>
                    </div>
                    <div className="input-group pb-2">
                        <input className="form-control" placeholder="E-MAIL"></input>
                    </div>
                    <div className="input-group pb-2">
                        <input className="form-control" placeholder="MOT DE PASSE"></input>
                    </div>
                    <div className="input-group pb-2">
                        <input className="form-control" placeholder="CONFIRMATION MOT DE PASSE"></input>
                    </div>
                    <div className="my-3">
                        <p>Vous avez déjà un compte?</p>
                        <button className="btn btn-primary" onClick={props.changeForm}>
                            Se connecter
                        </button>
                    </div>
                </div>
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
        <div className="container" style={{textAlign: "center"}}>
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