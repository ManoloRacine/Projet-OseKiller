import axios from "../api/axios";

export const getOffers = async (companyId) => {
    return axios.get("/offers", {
        params: {
            accepted: false,
        },
        headers: {
            "Content-Type": "application/json",
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};
