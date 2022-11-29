import Upload from "../../components/Upload";
import { useContext, useState } from "react";
import { uploadCv } from "../../services/StudentService";
import { AuthenticatedUserContext } from "../../App";

const UploadCv = () => {
    const [selectedFile, setSelectedFile] = useState({});
    const [isCvSubmitted, setIsCvSubmitted] = useState(false);
    const userId = useContext(AuthenticatedUserContext)?.authenticatedUser?.id;

    const handleSubmit = () => {
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
            onChange={({ target }) => {
                console.log(target.files);
                setSelectedFile(Array.from(target.files)[0]);
            }}
            onDelete={() => setSelectedFile({})}
            onSubmit={handleSubmit}
            isSubmitted={isCvSubmitted}
            successMessage={"CV téléversé avec succès !"}
        />
    );
};

export default UploadCv;
