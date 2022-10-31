import axios from "../api/axios";

export const uploadInternshipOffer = async (data, companyId) => {
    return axios.post(`/companies/${companyId}/offers`, data, {
        headers: {
            "Content-Type": "multipart/form-data",
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};

export const updateInternshipOffer = async (data, companyId, offerId) => {
    return axios.put(`/companies/${companyId}/offers/${offerId}`, data, {
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
    return axios.post(
        `/students/${studentId}/interviews?offerId=${data.offerId}`,
        data.dates,
        {
            headers: {
                "Content-Type": "application/json",
                Authorization: localStorage.getItem("accessToken"),
            },
        }
    );
};

export const acceptStudentApplication = async (
    companyId,
    offerId,
    applicantId
) => {
    return axios.post(
        `/companies/${companyId}/offers/${offerId}/applicants/${applicantId}/accept`,
        {},
        {
            headers: {
                "Content-Type": "multipart/form-data",
                Authorization: localStorage.getItem("accessToken"),
            },
        }
    );
};
