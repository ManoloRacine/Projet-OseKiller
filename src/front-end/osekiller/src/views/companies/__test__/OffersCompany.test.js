import axios from "../../../api/axios.js";
import { act } from "react-dom/test-utils";
import { render, screen } from "@testing-library/react";
import React from "react";
import OffersCompany from "../OffersCompany";
import { BrowserRouter } from "react-router-dom";

jest.mock("../../../api/axios.js");

describe("OffersCompany", () => {
    it("should render one OfferCard", async () => {
        const response = {
            data: [
                {
                    position: "Développeur Java",
                    salary: 123,
                    endDate: "2022-10-18",
                    startDate: "2022-10-25",
                },
            ],
        };

        axios.get.mockImplementation(() => Promise.resolve(response));

        await act(async () => {
            render(<OffersCompany />, { wrapper: BrowserRouter });
        });

        const offerCard = screen.getByTestId("offer-card");

        expect(offerCard).toBeInTheDocument();
    });

    it("should render multiple OfferCard", async () => {
        const response = {
            data: [
                {
                    position: "Développeur Java",
                    salary: 123,
                    endDate: "2022-10-18",
                    startDate: "2022-10-25",
                },
                {
                    position: "Développeur Python",
                    salary: 100,
                    endDate: "2022-09-18",
                    startDate: "2022-11-18",
                },
            ],
        };

        axios.get.mockImplementation(() => Promise.resolve(response));

        await act(async () => {
            render(<OffersCompany />, { wrapper: BrowserRouter });
        });

        const offerCard = screen.getAllByTestId("offer-card");

        expect(offerCard.length).toBe(2);
    });

    it("should not render OfferCard", async () => {
        render(<OffersCompany />, { wrapper: BrowserRouter });

        const offerCard = screen.queryByTestId("offer-card");

        expect(offerCard).not.toBeInTheDocument();
    });
});
