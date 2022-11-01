import axios from "../api/axios";

export const applyContract = async (contractId, data) => {
    return axios.post(`/contracts/${contractId}/sign`, data, {
        headers: {
            "Content-Type": "image/png",
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};
