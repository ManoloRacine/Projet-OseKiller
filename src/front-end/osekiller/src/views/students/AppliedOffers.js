import {useContext, useEffect, useState} from "react";
import {AuthenticatedUserContext} from "../App";
import { getOffersStudent } from "../../services/StudentService"


const AppliedOffers = () => {
    const [offers, setOffers] = useState([]);
    const {authenticatedUser} = useContext(AuthenticatedUserContext);

    useEffect(() => {
        getOffersStudent(authenticatedUser.id).then((response) => {
            setOffers(response.data) ;
        })
    }, [authenticatedUser.id]) ;

    return (
        
    )
}