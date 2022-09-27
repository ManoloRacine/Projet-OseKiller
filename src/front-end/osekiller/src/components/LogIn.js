import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { pingToken, userLogin } from "../services/AuthService";
import LogInForm from "./forms/LogInForm";

const LogIn = ({ changeForm, title }) => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [isLoading, setIsLoading] = useState(false);
    const [hasError, setHasError] = useState(false);
    const [errorMessage, setErrorMessage] = useState("");
    const navigate = useNavigate();

    useEffect(() => {
        pingToken()
            .then(() => {
                navigate("/dashboard");
            })
            .catch((err) => {
                if (err.response.status === 403) {
                    console.log("Token expiré");
                }
            });
    }, [navigate]);

    const handleSubmit = (e) => {
        e.preventDefault();
        const userInfo = { email: email, password: password };
        setIsLoading(true);

        userLogin(userInfo)
            .then((response) => {
                setIsLoading(false);
                const accessToken = response.data.accessToken;
                const refreshToken = response.data.refreshToken;
                const tokenType = response.data.tokenType;
                localStorage.setItem(
                    "accessToken",
                    `${tokenType} ${accessToken}`
                );
                localStorage.setItem(
                    "refreshToken",
                    `${tokenType} ${refreshToken}`
                );
                navigate("/dashboard", { state: { email: email } });
            })
            .catch((err) => {
                setIsLoading(false);
                if (!err?.response) {
                    setHasError(true);
                    setErrorMessage("Pas de réponse du serveur");
                } else if (err.response?.status === 400) {
                    setHasError(true);
                    setErrorMessage("Courriel ou mot de passe invalide");
                } else {
                    setHasError(true);
                    setErrorMessage("La connexion à échouée");
                }
            });
    };

    return (
        <LogInForm
            changeForm={changeForm}
            title={title}
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
