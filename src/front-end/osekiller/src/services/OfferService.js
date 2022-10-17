import axios from "../api/axios";

export const getOffers = async (isAccepted) => {
    return axios.get("/offers", {
        params: {
            accepted: isAccepted,
        },
        headers: {
            "Content-Type": "application/json",
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};

export const getOfferPdf = async (companyId, offerId) => {
    return axios.get(`companies/${companyId}/offers/${offerId}/pdf`, {
        responseType: "arraybuffer",
        headers: {
            "Content-Type": "application/json",
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};

export const validateOffer = async (companyId, offerId, dto) => {
    return axios.post(
        `companies/${companyId}/offers/${offerId}/validate`,
        { validation: dto.validation, feedBack: dto.feedBack },
        {
            headers: {
                "Content-Type": "application/json",
                Authorization: localStorage.getItem("accessToken"),
            },
        }
    );
};

export const applyToInternship = async (offerId) => {
    return axios.post(`/offers/${offerId}/apply`, {
        headers: {
            "Content-Type": "application/json",
            Authorization: localStorage.getItem("accessToken"),
        },
    });
};
