import axios from "../api/axios";

export const confirmInterviewDate = async (
    interviewId,
    studentId,
    confirmDate
) => {
    return axios.post(
        `/students/${studentId}/interviews/${interviewId}/confirm`,
        { chosenDate: confirmDate },
        {
            headers: {
                "Content-Type": "application/json",
                Authorization: localStorage.getItem("accessToken"),
            },
        }
    );
};
