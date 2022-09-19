import { useState } from "react";
import LogInForm from "./forms/LogInForm";
import SignUp from "./SignUp";

const Home = () => {
  const [isLogin, setIsLogin] = useState(true);
  const title = "Ose killer";

  const handleChangeForm = () => {
    setIsLogin(!isLogin);
  };

  return isLogin ? (
    <LogInForm title={title} changeForm={handleChangeForm} />
  ) : (
    <SignUp title={title} changeForm={handleChangeForm} />
  );
};

export default Home;
