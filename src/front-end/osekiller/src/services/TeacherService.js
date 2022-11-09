import axios from "../api/axios";

export const getContractsToEvaluate = async () => {
    return axios.get("/teacher/contracts-to-evaluate", {
        headers: {
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};

export const evaluateInternship = async (contractId, formData) => {
    return axios.post(`/teacher/${contractId}/evaluateInternship`, formData, {
        headers: {
            "Content-Type": "application/json",
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};
