import { MemoryRouter } from "react-router-dom";
import ApplyOffer from "../ApplyOffer";
import React from "react";

const MockApplyUser = () => {
    return (
        <MemoryRouter
            initialEntries={[
                "apply-offer",
                { state: { companyId: 1, offerId: 1 } },
            ]}
        >
            <ApplyOffer />
        </MemoryRouter>
    );
};

describe("ApplyOffer", () => {
    it("should render pdf", function () {
        //render(<MockApplyUser />);
    });
});
