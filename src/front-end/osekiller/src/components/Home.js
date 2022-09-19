import { useState } from "react";
import LogInForm from "./forms/LogInForm"
import SignUp from "./SignUp"

const Home = () => {
  const [isLogin, setIsLogin] = useState(true);
  const title = "Ose killer";

  const handleChangeForm = () => {
    setIsLogin(!isLogin);
  };

  return isLogin ? (
    <SignUp title={title} changeForm={handleChangeForm}/>
    
  ) : (
    <LogInForm title={title} changeForm={handleChangeForm} />
  );
};

export default Home;
