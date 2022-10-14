export const OfferCard = ({offer}) => {
    return (
        <div className="card">
            <div className="card-body">
                <div className="card-title">
                    {offer.companyName}
                </div>
                <div className="card-text">
                    <p>Position : {offer.position}</p>
                    <p>Salaire : {offer.salary}$</p>
                    <p>date de début : {offer.startDate}</p>
                    <p>date de fin : {offer.endDate}</p>
                </div>
                <button className="btn btn-primary me-2">
                    Voir infos
                </button>
            </div>
        </div>
    )
}