import { useState } from "react";
import { useLocation } from "react-router-dom";
import Upload from "../../components/Upload";
import { uploadReport } from "../../services/ContractService";

export const UploadReport = () => {
    const [selectedFile, setSelectedFile] = useState({});
    const [isReportSubmitted, setIsReportSubmitted] = useState(false);
    const location = useLocation();
    const contractId = location?.state?.contractId;

    const handleSubmit = () => {
        const formData = new FormData();
        formData.append("file", selectedFile);
        console.log(location.state)
        uploadReport(contractId, formData)
            .then(() => setIsReportSubmitted(true))

            .catch((err) => console.log("Error:", err));
    };

    return (
        <Upload
            subtitle={"Téléverser votre rapport de stage"}
            title={"Choisir votre rapport de stage"}
            selectedFile={selectedFile}
            onChange={({ target }) => {
                console.log(target.files);
                setSelectedFile(Array.from(target.files)[0]);
            }}
            onDelete={() => setSelectedFile({})}
            onSubmit={handleSubmit}
            isSubmitted={isReportSubmitted}
            successMessage={"Rapport téléversé avec succès !"}
        />
    );
};

export default UploadReport;