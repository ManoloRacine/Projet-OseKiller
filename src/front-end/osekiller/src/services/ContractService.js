import axios from "../api/axios"

const generateContract = (studentId, offerId, tasks) => {
    return axios.post(`/students/${studentId}/applications/${offerId}/generate-contract`,tasks, {
        headers: {
            "Content-Type": "application/json",
            Authorization: localStorage.getItem("accessToken"),
        }
    });
};

 export default generateContract;