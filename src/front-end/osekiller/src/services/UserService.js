import axios from "../api/axios"

export const fetchUsers = () => {
   return axios.get("/users", {
        headers: {
            Authorization: localStorage.getItem("accessToken"),
        },
    });
}

export const validateUser = (userId, valid) => {
    return axios.post("/user/" + userId + "/validate",{validation: valid}, {
         headers: {
             Authorization: localStorage.getItem("accessToken"),
         },
     });
 }