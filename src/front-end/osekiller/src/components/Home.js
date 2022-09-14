import { useState } from "react";
import LogInForm from "./forms/LogInForm";
import SignUpForm from "./forms/SignUpForm";

const Home = () => {
  const [isLogin, setIsLogin] = useState(true);
  const title = "Ose killer";

  const handleChangeForm = () => {
    setIsLogin(!isLogin);
  };

  return isLogin ? (
    <LogInForm title={title} changeForm={handleChangeForm} />
    
  ) : (
    <SignUpForm title={title} changeForm={handleChangeForm} />
  );
};

export default Home;
