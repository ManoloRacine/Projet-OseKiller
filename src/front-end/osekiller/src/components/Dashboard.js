import { useLocation, Link } from "react-router-dom";
import calLogo from "../calLogo.jpg" 

const Dashboard = () => {
  const location = useLocation();
  return (
    <div className="p-3">
      <nav className="d-flex p-2 rounded" style={{ backgroundColor: "#2C324C" }}>
        <div className="header d-flex align-items-center text-white">
          <img src={calLogo} alt="Logo du Cégep André-Laurendeau" />
          <h1 className="ps-4 display-4">Ose killer</h1>
        </div>
        <div className="links d-flex mx-auto">
          <Link to="/" className="m-4 fs-2 d-flex align-items-center">Link 1</Link>
          <Link to="/" className="m-4 fs-2 d-flex align-items-center">Link 2</Link>
          <Link to="/" className="m-4 fs-2 d-flex align-items-center">Link 3</Link>
        </div>
      </nav>
      <h1>{`Bonjour, ${location.state.email}`}</h1>
    </div>
  );
};

export default Dashboard;
