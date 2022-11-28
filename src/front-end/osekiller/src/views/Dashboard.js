import { useContext, useEffect, useState } from "react";
import { AuthenticatedUserContext } from "../App";
import StudentDashboard from "../components/dashboard/student/StudentDashboard";
import CompanyDashboard from "../components/dashboard/company/CompanyDashboard";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { getNotifications, removeNotification } from "../services/UserService";
import { Modal } from "react-bootstrap";
import { faBell } from "@fortawesome/free-solid-svg-icons";

const Dashboard = () => {
    const { authenticatedUser } = useContext(AuthenticatedUserContext);

    const [notifications, setNotifications] = useState([]);
    const [showModal, setShowModal] = useState(false);

    useEffect(() => {
        getNotifications()
            .then((response) => {
                console.log(response.data);
                setNotifications(response.data);
            })
            .catch();
    }, [authenticatedUser.id]);

    const handleRemoveNotif = (id) => {
        removeNotification(id).then(
            setNotifications(
                notifications.filter((notif) => notif.notificationId !== id)
            )
        );
    };

    return (
        <div>
            <div className={"d-flex align-items-center"}>
                <h1
                    className={"m-0 me-3"}
                >{`Bonjour, ${authenticatedUser.name}`}</h1>

                <button
                    className="btn ms-auto d-flex align-items-center"
                    style={{ backgroundColor: "#2C324C" }}
                    onClick={() => setShowModal(true)}
                >
                    <FontAwesomeIcon
                        icon={faBell}
                        className="fa-2x text-white me-3"
                    />
                    <p className={"fs-5 text-white m-0 me-1"}>Notifications</p>
                    <span className="badge bg-danger mt-auto">
                        {notifications.length}
                    </span>
                </button>
            </div>
            {authenticatedUser.role === "STUDENT" && <StudentDashboard />}
            {authenticatedUser.role === "COMPANY" && <CompanyDashboard />}
            <Modal
                show={showModal}
                onHide={() => setShowModal(false)}
                size="xl"
            >
                <Modal.Header closeButton>
                    <Modal.Title>Notifications</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {notifications.map((notif, key) => (
                        <div className="row p-4 border">
                            <div className="col-11">
                                {notif.notificationMessage}
                            </div>
                            <div className="col-1">
                                <button
                                    type="button"
                                    className="btn-close"
                                    onClick={() =>
                                        handleRemoveNotif(notif.notificationId)
                                    }
                                ></button>
                            </div>
                        </div>
                    ))}
                </Modal.Body>
            </Modal>
        </div>
    );
};

export default Dashboard;
