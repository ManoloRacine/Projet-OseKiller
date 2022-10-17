import { useContext, useEffect, useState } from "react";
import { OfferCard } from "../../components/OfferCard";
import {AuthenticatedUserContext} from "../../App";
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
        <div className="row">
            <div className="col-12">{offers.map((offer, index) => (<OfferCard offer={offer}></OfferCard>))}</div>
        </div>
    )
}

export default AppliedOffers ;