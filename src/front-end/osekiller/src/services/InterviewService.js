import axios from "../api/axios";

export const getInterviewInfo = async (interviewId) => {
    return axios.get(`/interview/${interviewId}/info`, {
        headers: {
            "Content-Type": "application/json",
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};

export const confirmInterviewDate = async (interviewId, confirmDate) => {
    return axios.post(
        `/interview/${interviewId}/confirm`,
        { chosenDate: confirmDate },
        {
            headers: {
                "Content-Type": "application/json",
                Authorization: localStorage.getItem("accessToken"),
            },
        }
    );
};
