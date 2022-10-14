import axios from "../api/axios";

export const getOffers = async () => {
    return axios.get("/offers?accepted=true", {
        headers: {
            Authorization : localStorage.getItem("accessToken")
        }
    }) ;
}