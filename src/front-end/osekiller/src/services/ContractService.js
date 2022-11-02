import axios from "../api/axios"

export const generateContract = (studentId, offerId, tasks) => {
    return axios.post(`/students/${studentId}/applications/${offerId}/generate-contract`,tasks, {
        responseType: "arraybuffer",
        headers: {
            "Content-Type": "application/json",
            Authorization: localStorage.getItem("accessToken"),
        }
    });
};

export const getContract = (contractId) => {
    return axios.get(`/contracts/${contractId}/pdf`, {
        responseType: "arraybuffer",
        headers: {
            "Content-Type": "application/pdf",
            Authorization: localStorage.getItem("accessToken"),
        }
    })
}
