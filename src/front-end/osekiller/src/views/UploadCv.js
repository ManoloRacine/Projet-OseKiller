import Upload from "../components/Upload";
import { useState } from "react";
import { uploadCv } from "../services/UploadService";

const UploadCv = () => {
    const [selectedFile, setSelectedFile] = useState({});
    const [isCvSubmitted, setIsCvSubmitted] = useState(false);

    const handleSubmit = (userId) => {
        const formData = new FormData();
        formData.append("file", selectedFile);
        uploadCv(formData, userId)
            .then(() => setIsCvSubmitted(true))

            .catch((err) => console.log("Error:", err));
    };

    return (
        <Upload
            subtitle={"Téléverser votre CV"}
            title={"Choisir votre CV"}
            selectedFile={selectedFile}
            onChange={({ target }) =>
                setSelectedFile(Array.from(target.files)[0])
            }
            onDelete={() => setSelectedFile({})}
            onSubmit={handleSubmit}
            isSubmitted={isCvSubmitted}
        />
    );
};

export default UploadCv;
