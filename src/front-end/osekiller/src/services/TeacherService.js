import axios from "../api/axios";

export const getContractsToEvaluate = async () => {
    return axios.get("/contracts?toEvaluate=true", {
        headers: {
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};

export const evaluateInternship = async (contractId, formData) => {
    return axios.post(
        `/contracts/${contractId}/evaluate-internship`,
        formData,
        {
            headers: {
                "Content-Type": "application/json",
                Authorization: localStorage.getItem("accessToken"),
            },
        }
    );
};
