import axios from "../api/axios";

export const uploadInternshipOffer = async (data, companyId) => {
    return axios.post(`/companies/${companyId}/offers`, data, {
        headers: {
            "Content-Type": "multipart/form-data",
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};

export const applyToInternship = async (companyId, offerId) => {
    return axios.post(`/companies/${companyId}/offers/${offerId}/apply`, {
        headers: {
            "Content-Type": "multipart/form-data",
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};
