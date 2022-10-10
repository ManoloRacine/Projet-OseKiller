import axios from "../api/axios";

export const uploadCv = async (data, studentId) => {
    return axios.put(`/student/${studentId}/cv`, data, {
        headers: {
            "Content-Type": "multipart/form-data",
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};

export const uploadInternshipOffer = async (data, companyId) => {
    return axios.post(`/compagnies/${companyId}/offers`, data, {
        headers: {
            "Content-Type": "multipart/form-data",
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};
