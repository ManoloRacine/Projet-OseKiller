import axios from "../api/axios";

export const getCv = async (studentId) => {
    return axios.get(`/students/${studentId}/cv`, {
        responseType: "arraybuffer",
        headers: {
            "Content-Type": "application/pdf",
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};

export const validateCv = async (studentId, isValid, feedbackText) => {
    return axios.post(
        `/students/${studentId}/cv/validate`,
        { validation: isValid, feedBack: feedbackText },
        {
            headers: {
                "Content-Type": "application/json",
                Authorization: localStorage.getItem("accessToken"),
            },
        }
    );
};

export const uploadCv = async (data, studentId) => {
    return axios.put(`/students/${studentId}/cv`, data, {
        headers: {
            "Content-Type": "multipart/form-data",
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};

export const getStudents = async () => {
    return axios.get(`/students`, {
        headers: {
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};

export const getStudent = async (studentId) => {
    return axios.get(`/students/${studentId}`, {
        headers: {
            "Content-Type": "application/json",
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};

export const getOffersStudent = async (studentId) => {
    return axios.get(`/students/${studentId}/applications`, {
        headers: {
            "Content-Type": "application/json",
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};

export const getStudentConvocations = async (studentId) => {
    return axios.get(`/students/${studentId}/convocations`, {
        headers: {
            "Content-Type": "application/json",
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};
