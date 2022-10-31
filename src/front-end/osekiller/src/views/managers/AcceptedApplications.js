import { useEffect, useState } from "react";
import { getAllAcceptedApplications } from "../../services/ApplicationService";

const AcceptedApplications = () => {
    const [acceptedApplications, setAcceptedApplications] = useState([]);

    useEffect(()=>{
        getAllAcceptedApplications()
        .then((response) => {
            setAcceptedApplications(response.data);
        })
        .catch((error) => {
            console.error(error);
        })
    },[])

    return <>{console.log(acceptedApplications)}</>
}

export default AcceptedApplications;