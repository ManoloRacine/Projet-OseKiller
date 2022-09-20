import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import LogInForm from "./forms/LogInForm";

const LogIn = (props) => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [hasError, setHasError] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    const userInfo = { email: email, password: password };
    setIsLoading(true);
    axios
      .post(`https://${process.env.REACT_APP_SERVER_ADRESS}/sign-in`, userInfo)
      .then((response) => {
        setIsLoading(false);
        navigate("/dashboard", { state: response.data });
      })
      .catch((error) => {
        setIsLoading(false);
        console.log(error.response);
        setHasError(true);
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
    />
  );
};

export default LogIn;
