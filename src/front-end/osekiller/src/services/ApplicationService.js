import axios from "../api/axios";

export const getAllAcceptedApplications = async () => {
    return axios.get("/applications");
}