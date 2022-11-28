import { useContext, useEffect, useState } from "react";
import { AuthenticatedUserContext } from "../App";
import StudentDashboard from "../components/dashboard/student/StudentDashboard";
import CompanyDashboard from "../components/dashboard/company/CompanyDashboard";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { getNotifications, removeNotification } from "../services/UserService";
import { Modal } from "react-bootstrap";
import { faBell } from "@fortawesome/free-solid-svg-icons";
import { faX } from "@fortawesome/free-solid-svg-icons";

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
            <h1>{`Bonjour, ${authenticatedUser.name}`}</h1>
            <FontAwesomeIcon
                icon={faBell}
                className="fa-2x"
                style={
                    notifications.length === 0
                        ? { color: "black" }
                        : { color: "red" }
                }
                onClick={() => setShowModal(true)}
            />
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
