import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { pingToken } from "../services/AuthService";
import LogIn from "../components/LogIn";
import SignUp from "../components/SignUp";

const Home = () => {
    const [isLogin, setIsLogin] = useState(true);
    const title = "Ose killer";
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

    const handleChangeForm = () => {
        setIsLogin(!isLogin);
    };

    return isLogin ? (
        <LogIn title={title} changeForm={handleChangeForm} />
    ) : (
        <SignUp title={title} changeForm={handleChangeForm} />
    );
};

export default Home;
