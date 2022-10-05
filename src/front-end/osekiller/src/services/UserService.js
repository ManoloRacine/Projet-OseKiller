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

export const getStudents = async () => {
    return axios.get(`/students`, {
        headers: {
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};

export const getStudent = async (studentId) => {
    return axios.get(`/student/${studentId}`, {
        headers: {
            "Content-Type" : "application/json",
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};
