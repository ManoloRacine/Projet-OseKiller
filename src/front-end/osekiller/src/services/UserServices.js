import axios from "../api/axios";

const token = localStorage.getItem("accessToken");

export const getStudents = async () => {
    return axios.get(`/students`, {
        headers: {
            Authorization: token,
        },
    });
};