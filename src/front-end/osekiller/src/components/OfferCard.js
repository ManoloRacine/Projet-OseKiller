import { Link } from "react-router-dom";
import React from "react";

export const OfferCard = ({ offer, redirectTo }) => {
    return (
        <div
            className={
                "offer d-flex justify-content-evenly align-items-center text-white my-4 py-4 rounded"
            }
            style={{ backgroundColor: "#2C324C" }}
            data-testid={"offer-card"}
        >
            {offer.companyName && (
                <div>
                    <p className={"fs-4 text-decoration-underline"}>
                        Nom de la compagnie
                    </p>
                    <p>{offer.companyName}</p>
                </div>
            )}
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

            <Link
                to={redirectTo}
                className={"btn btn-primary"}
                state={{ companyId: offer.companyId, offerId: offer.offerId }}
            >
                Détail
            </Link>
        </div>
    );
};
