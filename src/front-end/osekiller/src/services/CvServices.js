import axios from "../api/axios";

const token = localStorage.getItem("accessToken");

export const getCV = async (studentId) => {
    return axios.get(
        `/student/${studentId}/cv`, {
            responseType: "arraybuffer",
            headers: {
                "Content-Type" : "application/pdf",
                Authorization: localStorage.getItem("accessToken"),
            },
        });
} ;