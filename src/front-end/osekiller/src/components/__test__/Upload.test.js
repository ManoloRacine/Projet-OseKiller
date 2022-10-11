import { fireEvent, render, screen } from "@testing-library/react";
import Upload from "../Upload";
import { BrowserRouter } from "react-router-dom";
import UploadCv from "../../views/UploadCv";

const WrappingComponent = ({
    selectedFile,
    onDelete,
    onChange,
    isSubmitted,
    onSubmit,
    subtitle,
    title,
}) => {
    return (
        <BrowserRouter>
            <Upload
                selectedFile={selectedFile}
                onDelete={onDelete}
                onChange={onChange}
                isSubmitted={isSubmitted}
                onSubmit={onSubmit}
                subtitle={subtitle}
                title={title}
            />
        </BrowserRouter>
    );
};

const MockUploadCv = () => {
    return (
        <BrowserRouter>
            <UploadCv />
        </BrowserRouter>
    );
};

describe("Upload", () => {
    it("should render same text passed into props", function () {
        render(
            <WrappingComponent
                selectedFile={{}}
                onDelete={() => console.log("deleting...")}
                onChange={() => console.log("Changing")}
                isSubmitted={false}
                onSubmit={() => console.log("Submitting")}
                subtitle={"Choisir votre CV"}
                title={"Téléverser votre CV"}
            />
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
            <WrappingComponent
                selectedFile={{ name: "Mon CV", size: 10000 }}
                onDelete={() => console.log("deleting...")}
                onChange={() => console.log("Changing")}
                isSubmitted={false}
                onSubmit={() => console.log("Submitting")}
                subtitle={"Choisir votre CV"}
                title={"Téléverser votre CV"}
            />
        );

        const subtitle = screen.queryByText(/Choisir votre CV/i);
        const title = screen.getByRole("heading", {
            name: "Téléverser votre CV",
        });

        expect(title).toBeInTheDocument();
        expect(subtitle).not.toBeInTheDocument();
    });

    it("should be able to add file", function () {
        render(<MockUploadCv />);

        const inputElement = screen.getByTestId("cvInput");
        const file = new File(["(⌐□_□)"], "chucknorris.pdf", {
            type: "application/pdf",
        });
        fireEvent.change(inputElement, { target: { files: [file] } });

        const uploadIcon = screen.queryByTestId("uploadIcon");

        expect(uploadIcon).not.toBeInTheDocument();
    });
});
