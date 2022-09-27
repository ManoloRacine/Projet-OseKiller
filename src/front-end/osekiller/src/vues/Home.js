import { useState } from "react";
import LogIn from "../components/LogIn";
import SignUp from "../components/SignUp";

const Home = () => {
    const [isLogin, setIsLogin] = useState(true);
    const title = "Ose killer";

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
