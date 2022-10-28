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

export const updateStudentSession = async (studentId) => {
    return axios.post(
        `/students/${studentId}/updateSession`,
        {},
        {
            headers: {
                "Content-Type": "application/json",
                Authorization: localStorage.getItem("accessToken"),
            },
        }
    );
};

export const getSessionFromDate = (date) => {
    if (date < new Date(date.getYear() + "-05-31")) {
        return date.getFullYear();
    } else {
        return date.getFullYear() + 1;
    }
};
