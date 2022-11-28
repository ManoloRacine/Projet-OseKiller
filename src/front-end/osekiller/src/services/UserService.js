import axios from "../api/axios";

export const fetchUsers = () => {
    return axios.get("/users", {
        headers: {
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};

export const validateUser = (userId, valid) => {
    return axios.post(
        "/users/" + userId + "/validate",
        { validation: valid },
        {
            headers: {
                Authorization: localStorage.getItem("accessToken"),
            },
        }
    );
};

export const getNotifications = () => {
    return axios.get("/notifications", {
        headers: {
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};

export const removeNotification = (id) => {
    return axios.delete(`/notifications/${id}`, {
        headers: {
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};
