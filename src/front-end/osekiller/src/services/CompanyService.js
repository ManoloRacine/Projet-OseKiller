import axios from "../api/axios";

export const uploadInternshipOffer = async (data, companyId) => {
    return axios.post(`/companies/${companyId}/offers`, data, {
        headers: {
            "Content-Type": "multipart/form-data",
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};

export const getOffers = async (companyId) => {
    return axios.get(`/companies/${companyId}/offers`, {
        headers: {
            "Content-Type": "multipart/form-data",
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};

export const getOffer = async (companyId, offerId) => {
    return axios.get(`companies/${companyId}/offers/${offerId}`, {
        headers: {
            "Content-Type": "multipart/form-data",
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};
