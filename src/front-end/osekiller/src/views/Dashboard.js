import { useContext } from "react";
import { AuthenticatedUserContext } from "../App";

const Dashboard = () => {

    const {authenticatedUser} = useContext(AuthenticatedUserContext);

    return (
        <div className="p-3">
            <h1>{`Bonjour, ${authenticatedUser.name}`}</h1>
        </div>
    );
};

export default Dashboard;
