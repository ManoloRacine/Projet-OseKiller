export const OfferCard = ({offer}) => {
    return (
        <div
            className={
                "offer d-flex justify-content-evenly align-items-center text-white my-4 py-4 rounded"
            }
            style={{ backgroundColor: "#2C324C" }}
        >
            <div>
                <p className={"fs-4 text-decoration-underline"}>Position</p>
                <p>{offer.position}</p>
            </div>
            <div>
                <p className={"fs-4 text-decoration-underline"}>Salaire</p>
                <p>{offer.salary}$</p>
            </div>
            <div>
                <p className={"fs-4 text-decoration-underline"}>
                    Date de début
                </p>
                <p>{offer.startDate}</p>
            </div>
            <div>
                <p className={"fs-4 text-decoration-underline"}>Date de fin</p>
                <p>{offer.endDate}</p>
            </div>

            <button className={"btn btn-primary"}>Détails</button>
        </div>
    );
};
