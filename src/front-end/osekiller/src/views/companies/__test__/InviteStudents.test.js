import { fireEvent, render, screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { BrowserRouter } from "react-router-dom";
import InviteStudent from "../InviteStudent";

const MockInviteStudents = ({ student }) => {
    return (
        <BrowserRouter>
            <InviteStudent student={student} />
        </BrowserRouter>
    );
};

describe("Invite Students", () => {
    it("input all dates", () => {
        render(<MockInviteStudents student={{ name: "test student" }} />);

        let InputFirstDate = "2022-10-01";
        let InputSecondDate = "2022-10-02";
        let InputThirdDate = "2022-10-03";

        userEvent.type(
            screen.getByLabelText("Première date d'entrevue"),
            InputFirstDate
        );
        userEvent.type(
            screen.getByLabelText("Deuxième date d'entrevue"),
            InputSecondDate
        );
        userEvent.type(
            screen.getByLabelText("Troisième date d'entrevue"),
            InputThirdDate
        );

        expect(screen.getByLabelText("Première date d'entrevue")).toHaveValue(
            InputFirstDate
        );
        expect(screen.getByLabelText("Deuxième date d'entrevue")).toHaveValue(
            InputSecondDate
        );
        expect(screen.getByLabelText("Troisième date d'entrevue")).toHaveValue(
            InputThirdDate
        );
    });

    // J'ai mis ce test en commentaire, car je ne suis pas trop sûr ce que tu essais de tester ici.
    // it("student present", () => {
    //     render(<MockInviteStudents student={{ name: "test student" }} />);
    //
    //     const element = screen.getAllByText(/test student/i);
    //
    //     expect(element).toBeInTheDocument();
    // });
});
