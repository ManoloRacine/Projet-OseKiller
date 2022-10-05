import axios from "../api/axios";

const token = localStorage.getItem("accessToken");

export const getStudents = async () => {
    return axios.get(`/students`, {
        headers: {
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};

export const getStudent = async (studentId) => {
    return axios.get(`/student/${studentId}`, {
        headers: {
            "Content-Type" : "application/json",
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};