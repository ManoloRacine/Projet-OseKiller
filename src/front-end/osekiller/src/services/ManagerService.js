import axios from "../api/axios";

export const getEvaluationPdf = async (contractId) => {
    return axios.get(`/manager/${contractId}/evaluation-pdf`, {
        responseType: "arraybuffer",
        headers: {
            "Content-Type": "application/pdf",
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};

export const getEvaluations = async () => {
    return axios.get(`/manager/evaluations`, {
        headers: {
            "Content-Type": "application/json",
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};
