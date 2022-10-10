import Upload from "../components/Upload";
import { useState } from "react";
import { uploadInternshipOffer } from "../services/UploadService";

const UploadInternship = () => {
    const [selectedFile, setSelectedFile] = useState({});
    const [isOfferSubmitted, setIsOfferSubmitted] = useState(false);

    const handleSubmit = (userId) => {
        const formData = new FormData();
        formData.append("file", selectedFile);
        uploadInternshipOffer(formData, userId)
            .then(() => setIsOfferSubmitted(true))

            .catch((err) => console.log("Error:", err));
    };
    return (
        <Upload
            subtitle={"Téléverser une offre de stage"}
            title={"Choisir l'offre de stage"}
            selectedFile={selectedFile}
            onChange={({ target }) =>
                setSelectedFile(Array.from(target.files)[0])
            }
            onDelete={() => setSelectedFile({})}
            onSubmit={handleSubmit}
            isSubmitted={isOfferSubmitted}
        />
    );
};

export default UploadInternship;
