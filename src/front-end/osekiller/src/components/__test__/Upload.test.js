import { fireEvent, render, screen } from "@testing-library/react";
import Upload from "../Upload";
import { BrowserRouter } from "react-router-dom";
import UploadCv from "../../views/students/UploadCv";

describe("Upload", () => {
    it("should render same text passed into props", function () {
        render(
            <Upload
                selectedFile={{}}
                onDelete={() => console.log("deleting...")}
                onChange={() => console.log("Changing")}
                isSubmitted={false}
                onSubmit={() => console.log("Submitting")}
                subtitle={"Choisir votre CV"}
                title={"Téléverser votre CV"}
                successMessage={"Offre de stage téléversée avec succès!"}
            />,
            { wrapper: BrowserRouter }
        );

        const subtitle = screen.getByText(/Choisir votre CV/i);
        const title = screen.getByRole("heading", {
            name: "Téléverser votre CV",
        });

        expect(title).toBeInTheDocument();
        expect(subtitle).toBeInTheDocument();
    });

    it("should not render subtitle", function () {
        render(
            <Upload
                selectedFile={{ name: "Mon CV", size: 10000 }}
                onDelete={() => console.log("deleting...")}
                onChange={() => console.log("Changing")}
                isSubmitted={false}
                onSubmit={() => console.log("Submitting")}
                subtitle={"Choisir votre CV"}
                title={"Téléverser votre CV"}
                successMessage={"Offre de stage téléversée avec succès!"}
            />,
            { wrapper: BrowserRouter }
        );

        const subtitle = screen.queryByText(/Choisir votre CV/i);
        const title = screen.getByRole("heading", {
            name: "Téléverser votre CV",
        });

        expect(title).toBeInTheDocument();
        expect(subtitle).not.toBeInTheDocument();
    });

    it("should be able to add file", function () {
        render(<UploadCv />, { wrapper: BrowserRouter });

        const inputElement = screen.getByTestId("pdfInput");
        const file = new File(["(⌐□_□)"], "chucknorris.pdf", {
            type: "application/pdf",
        });
        fireEvent.change(inputElement, { target: { files: [file] } });

        const uploadIcon = screen.queryByTestId("uploadIcon");

        expect(uploadIcon).not.toBeInTheDocument();
    });
});
