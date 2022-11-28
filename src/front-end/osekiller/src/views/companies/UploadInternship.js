import Upload from "../../components/Upload";
import { useState, useContext } from "react";
import {
    updateInternshipOffer,
    uploadInternshipOffer,
} from "../../services/CompanyService";
import UploadInternshipForm from "../../components/forms/UploadInternshipForm";
import ErrorMessage from "../../components/ErrorMessage";
import { useLocation } from "react-router-dom";
import { AuthenticatedUserContext } from "../../App";

const UploadInternship = () => {
    const location = useLocation();
    const { state } = location;
    const [position, setPosition] = useState(
        state?.position ? state.position : ""
    );
    const [salary, setSalary] = useState(state?.salary ? String(state.salary) : "");
    const [startDate, setStartDate] = useState(
        state?.startDate ? String(state.startDate) : ""
    );
    const [endDate, setEndDate] = useState(state?.endDate ? String(state.endDate) : "");
    const [selectedFile, setSelectedFile] = useState({});
    const [isOfferSubmitted, setIsOfferSubmitted] = useState(false);
    const [errorMessage, setErrorMessage] = useState("");
    const userId = useContext(AuthenticatedUserContext)?.authenticatedUser?.id;

    const handleSubmit = () => {
        console.log(position);
        console.log(typeof salary)
        if (position.trim() === "") {
            setErrorMessage("La position ne doit pas être vide");
            return;
        } else if (salary.trim() === "") {
            setErrorMessage("Le salaire ne doit pas être vide");
            return;
        } else if (startDate === "") {
            setErrorMessage("Veuillez choisir une date de début valide");
            return;
        } else if (endDate === "") {
            setErrorMessage("Veuillez choisir une date de fin valide");
            return;
        }
        const dateDebut = new Date(startDate);
        const dateFin = new Date(endDate);
        if (dateFin < dateDebut) {
            setErrorMessage(
                "La date de fin ne doit pas être plus petit que la date de début"
            );
            return;
        }

        const dto = {
            position: position,
            salary: parseFloat(salary),
            startDate: startDate,
            endDate: endDate,
        };
        const formData = new FormData();
        formData.append("file", selectedFile);
        formData.append("offerDto", JSON.stringify(dto));
        state.offerId
            ? updateInternshipOffer(formData, userId, state.offerId)
                  .then(() => setIsOfferSubmitted(true))

                  .catch((err) => console.log("Error:", err))
            : uploadInternshipOffer(formData, userId)
                  .then(() => setIsOfferSubmitted(true))

                  .catch((err) => console.log("Error:", err));
    };
    return (
        <div>
            <UploadInternshipForm
                setStartDate={({ target }) => setStartDate(target.value)}
                endDate={endDate}
                position={position}
                salary={salary}
                setEndDate={({ target }) => setEndDate(target.value)}
                setPosition={({ target }) => setPosition(target.value)}
                startDate={startDate}
                setSalary={({ target }) => setSalary(target.value)}
            />
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
                successMessage={"Offre de stage téléversé avec succès !"}
            />
            {errorMessage && (
                <ErrorMessage message={errorMessage} severity={"error"} />
            )}
        </div>
    );
};

export default UploadInternship;
