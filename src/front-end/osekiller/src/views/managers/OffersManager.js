import { useEffect, useState } from "react";
import { getOffers } from "../../services/OfferService";
import { OfferCard } from "../../components/OfferCard";
import { getSessionFromDate } from "../../services/StudentService";
import { Dropdown } from "bootstrap";
import { ReactDOM } from "react";

const OffersManager = () => {
    const [offers, setOffers] = useState([]);
    const [sessions, setSessions] = useState([]);
    const [sessionFilter, setSessionFilter] = useState("");

    useEffect(() => {
        getOffers(false)
            .then(({ data }) => {
                setOffers(data);
                console.log(data);
                let sessionSet = new Set();
                data.forEach((offer) => {
                    let startDate = new Date(offer.startDate);
                    sessionSet.add(getSessionFromDate(startDate));
                });
                setSessions(Array.from(sessionSet));
            })
            .catch((err) => {
                console.log(err);
            });
    }, []);

    const handleChangeSession = (e) => {
        console.log(e.target.text);
        setSessionFilter(e.target.text);
    };

    const resetSession = () => {
        setSessionFilter("");
    };

    return (
        <div>
            <div className="dropdown mt-3">
                <button
                    aria-expanded="false"
                    data-bs-toggle="dropdown"
                    id="session-dropdown"
                    className="btn btn-secondary dropdown-toggle"
                    type="button"
                >
                    {sessionFilter === ""
                        ? "Toutes les sessions"
                        : sessionFilter}
                </button>
                <ul
                    className="dropdown-menu dropdown-menu-dark"
                    aria-labelledby="session-dropdown"
                >
                    <li>
                        <a className="dropdown-item" onClick={resetSession}>
                            Toutes les sessions
                        </a>
                    </li>
                    {sessions.map((session, index) => (
                        <li key={index}>
                            <a
                                onClick={handleChangeSession}
                                className={
                                    "dropdown-item " +
                                    (session.toString() === sessionFilter
                                        ? "active"
                                        : "")
                                }
                            >
                                {session}
                            </a>
                        </li>
                    ))}
                </ul>
            </div>
            {offers
                .filter((session) =>
                    sessionFilter === ""
                        ? true
                        : sessionFilter.toString() ===
                          getSessionFromDate(
                              new Date(session.startDate)
                          ).toString()
                )
                .map((offer, index) => (
                    <OfferCard
                        key={index}
                        offer={offer}
                        redirectTo={"/validate-offer"}
                    />
                ))}
        </div>
    );
};

OffersManager.propTypes = {};

export default OffersManager;
