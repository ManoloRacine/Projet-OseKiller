import { Link, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import calLogo from "../assets/calLogo.jpg";
import { pingToken } from "../services/AuthService";

const Dashboard = () => {
    const [userName, setUserName] = useState("");

    //Pas vraiment optimale parce que faudrait faire un call dans chaque component qui à besoins des information de l'utilisateur
    // Faudrait qu'on ait un global state
    useEffect(() => {
        pingToken()
            .then((response) => {
                setUserName(response.data.name);
            })
            .catch((err) => {
                console.log(err);
            });
    }, []);

    return (
        <div className="p-3">
            <h1>{`Bonjour, ${userName}`}</h1>
        </div>
    );
};

export default Dashboard;
