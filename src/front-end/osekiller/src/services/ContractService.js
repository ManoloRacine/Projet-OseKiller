import axios from "../api/axios";

export const generateContract = (studentId, offerId, tasks) => {
    return axios.post(
        `/students/${studentId}/applications/${offerId}/generate-contract`,
        tasks,
        {
            responseType: "arraybuffer",
            headers: {
                "Content-Type": "application/json",
                Authorization: localStorage.getItem("accessToken"),
            },
        }
    );
};

export const getContract = (contractId) => {
    return axios.get(`/contracts/${contractId}/pdf`, {
        responseType: "arraybuffer",
        headers: {
            "Content-Type": "application/pdf",
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};

export const signContract = async (contractId, data) => {
    return axios.post(`/contracts/${contractId}/sign`, data, {
        headers: {
            "Content-Type": "multipart/form-data",
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};

export const evaluateIntern = async (contractId, formData) => {
    return axios.post(`/contracts/${contractId}/evaluate-intern`, formData, {
        headers: {
            "Content-Type": "application/json",
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};
