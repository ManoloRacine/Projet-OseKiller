import axios from "../api/axios";

const token = localStorage.getItem("accessToken");

export const uploadCv = async (data, studentId) => {
    return axios.put(`/student/${studentId}/cv`, data, {
        headers: {
            "Content-Type": "multipart/form-data",
            Authorization: token,
        },
    });
};