import axios from "../api/axios";

export const uploadCv = async (data, studentId) => {
    return axios.put(`/students/${studentId}/cv`, data, {
        headers: {
            "Content-Type": "multipart/form-data",
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};

export const uploadInternshipOffer = async (dto, data, companyId) => {
    return axios.post(
        `/companies/${companyId}/offers`,
        { offerDto: JSON.stringify(dto), file: data },
        {
            headers: {
                "Content-Type": "multipart/form-data",
                Authorization: localStorage.getItem("accessToken"),
            },
        }
    );
};
