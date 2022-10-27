import axios from "../api/axios";

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
