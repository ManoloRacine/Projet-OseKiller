import axios from "../api/axios";


export const uploadCv = async (data, studentId) => {
    return axios.put(`/students/${studentId}/cv`, data, {
        headers: {
            "Content-Type": "multipart/form-data",
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};
