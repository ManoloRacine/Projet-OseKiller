import axios from "../api/axios";

export const uploadInternshipOffer = async (data, companyId) => {
    return axios.post(`/companies/${companyId}/offers`, data, {
        headers: {
            "Content-Type": "multipart/form-data",
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};

export const getOffersByCompany = async (companyId) => {
    return axios.get(`/companies/${companyId}/offers`, {
        headers: {
            "Content-Type": "application/json",
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};

export const getApplicantsByOffer = async (companyId, offerId) => {
    return axios.get(`/companies/${companyId}/offers/${offerId}/applicants`, {
        headers: {
            "Content-Type": "application/json",
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};

export const sendConvocation = async (data, studentId) => {
    return axios.post(`/students/${studentId}/interviews`, data, {
        headers: {
            "Content-Type": "application/json",
            Authorization: localStorage.getItem("accessToken"),
        },
    });
}