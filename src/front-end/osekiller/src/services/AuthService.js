import axios from "../api/axios";

export const userLogin = async (userInfo) => {
    return axios.post("/sign-in", userInfo);
};

export const userSignUp = async (userInfo) => {
    return axios.post("/sign-up", userInfo);
};

export const pingToken = async () => {
    return axios.get("/ping/token", {
        headers: {
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};
