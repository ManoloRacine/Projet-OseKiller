import { Link, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import calLogo from "../assets/calLogo.jpg";
import { pingToken } from "../services/AuthService";

const Dashboard = () => {
    const [userName, setUserName] = useState("");
    
    useEffect(() => {
        pingToken()
            .then((response) => {
                setUserName(response.data.name);
            })
            .catch((err) => {
                console.error(err);
            });
    }, []);

    return (
        <div className="p-3">
            <h1>{`Bonjour, ${userName}`}</h1>
        </div>
    );
};

export default Dashboard;
