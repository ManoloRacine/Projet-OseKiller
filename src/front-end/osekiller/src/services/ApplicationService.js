import axios from "../api/axios";

export const getAllAcceptedApplications = async () => {
    return axios.get("/applications",{
        params: {
            accepted: true
        },
        headers: {
            "Content-Type": "application/json",
            Authorization: localStorage.getItem("accessToken"),
        },
    });
}