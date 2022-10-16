import { BrowserRouter } from "react-router-dom";
import ApplyOffer from "../ApplyOffer";
import React from "react";
import { render, screen } from "@testing-library/react";

describe("ApplyOffer", () => {
    it("should render pdf", function () {
        render(<ApplyOffer />, { wrapper: BrowserRouter });

        const button = screen.getByText(/Appliquer/i);
        const pdf = screen.getByTestId("pdf");

        expect(button).toBeInTheDocument();
        expect(pdf).toBeInTheDocument();
    });
});
