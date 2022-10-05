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

export const validateCv = async (studentId, feedbackText) => {
    return axios.post(
        `/student/${studentId}/cv/validate`, {validation : true, feedBack : feedbackText},
            {headers : {
                "Content-Type" : "application/json",
                Authorization : localStorage.getItem("accessToken"),
            }}, 
    ) ;
}

export const invalidateCv = async (studentId, feedbackText) => {
    return axios.post(
        `/student/${studentId}/cv/validate`, {validation : false, feedBack : feedbackText},
        {headers : {
            "Content-Type" : "application/json",
            Authorization : localStorage.getItem("accessToken"),
        }}, 
    ) ;
}