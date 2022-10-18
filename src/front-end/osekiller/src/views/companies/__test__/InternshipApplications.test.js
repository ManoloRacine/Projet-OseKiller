import axios from "../../../api/axios.js";
import { act } from "react-dom/test-utils";
import { render, screen } from "@testing-library/react";
import React from "react";
import { BrowserRouter } from "react-router-dom";
import InternshipApplications from "../InternshipApplications";

jest.mock("../../../api/axios.js");

describe("InternshipApplications", () => {
    it("should render one StudentCard", async () => {
        const response = {
            data: [
                {
                    name: "Chuck Norris",
                    email: "chuck@norris.com",
                },
            ],
        };

        axios.get.mockImplementation(() => Promise.resolve(response));

        await act(async () => {
            render(<InternshipApplications />, { wrapper: BrowserRouter });
        });

        const offerCard = screen.getByTestId("student-card");

        expect(offerCard).toBeInTheDocument();
    });

    it("should render multiple StudentCard", async () => {
        const response = {
            data: [
                {
                    name: "Chuck Norris",
                    email: "chuck@norris.com",
                },
                {
                    name: "Joe Biden",
                    email: "chuck@norris.com",
                },
            ],
        };

        axios.get.mockImplementation(() => Promise.resolve(response));

        await act(async () => {
            render(<InternshipApplications />, { wrapper: BrowserRouter });
        });

        const offerCard = screen.getAllByTestId("student-card");

        expect(offerCard.length).toBe(2);
    });

    it("should not render OfferCard", async () => {
        render(<InternshipApplications />, { wrapper: BrowserRouter });

        const offerCard = screen.queryByTestId("student-card");

        expect(offerCard).not.toBeInTheDocument();
    });
});
