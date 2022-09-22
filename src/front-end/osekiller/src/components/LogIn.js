import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "../api/axios";
import LogInForm from "./forms/LogInForm";

const LogIn = (props) => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [isLoading, setIsLoading] = useState(false);
    const [hasError, setHasError] = useState(false);
    const [errorMessage, setErrorMessage] = useState("");
    const navigate = useNavigate();

    const handleSubmit = (e) => {
      e.preventDefault();
      const userInfo = { email: email, password: password };
      setIsLoading(true);
      
      axios.post("/sign-in", userInfo).then((response) => {
        setIsLoading(false);
        const accessToken = response.data.accessToken;
        const refreshToken = response.data.refreshToken;
        const tokenType = response.data.tokenType;
        localStorage.setItem("accessToken", `${tokenType} ${accessToken}`);
        localStorage.setItem("refreshToken", `${tokenType} ${refreshToken}`);
        navigate("/dashboard", {state: {"email": email}});
      }).catch((err) => {
        setIsLoading(false);
      if (!err?.response) {
          setHasError(true);
          setErrorMessage("Pas de réponse du serveur");
          //<ErrorMessage message="No Server Response" severity="err" />;
      } else if (err.response?.status === 400) {
          setHasError(true);
          setErrorMessage("Courriel ou mot de passe invalide");
          //<ErrorMessage message="Username Taken" severity="err" />;
      } else {
          setHasError(true);
          setErrorMessage("La connexion à échouée");
          //<ErrorMessage message="Registration Failed" severity="err" />;
      }
      });
            
            
        
    };

    return (
        <LogInForm
            changeForm={props.changeForm}
            title={props.title}
            onSubmit={handleSubmit}
            email={email}
            setEmail={({ target }) => setEmail(target.value)}
            password={password}
            setPassword={({ target }) => setPassword(target.value)}
            isLoading={isLoading}
            hasError={hasError}
            errorMessage={errorMessage}
        />
    );
};

export default LogIn;
