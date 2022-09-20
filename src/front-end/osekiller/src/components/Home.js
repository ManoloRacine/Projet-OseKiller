import { useState } from "react";
import LogIn from "./LogIn";
import SignUp from "./SignUp";

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
