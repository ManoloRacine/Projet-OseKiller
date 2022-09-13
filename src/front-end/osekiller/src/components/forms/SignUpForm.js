
const SignUpForm = (props) => {

    
    return (
        <div className="container">
            <h1>{props.title}</h1>
            <div className="row">
                <div className="col sm-2"></div>
                <div className="col sm-8">
                    <div className="input-group pb-2">
                        <input className="form-control" placeholder="E-MAIL"></input>
                    </div>
                    <div className="input-group pb-2">
                        <input className="form-control" placeholder="MOT DE PASSE"></input>
                    </div>
                    <div className="input-group pb-2">
                        <input className="form-control" placeholder="CONFIRMATION MOT DE PASSE"></input>
                    </div>
                </div>
                <div className="col sm-2"></div>
            </div>
        </div>
        
    ) ;
}

export default SignUpForm ;