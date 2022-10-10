import PropTypes from "prop-types";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCloudArrowUp } from "@fortawesome/free-solid-svg-icons";
import SelectedCV from "./SelectedCV";
import { useState } from "react";
import { useLocation } from "react-router-dom";
import { uploadCv } from "../services/UploadService";
import ErrorMessage from "./ErrorMessage";

const Upload = ({ title, subtitle }) => {
    const [selectedFile, setSelectedFile] = useState({});
    const [isCvSubmitted, setIsCvSubmitted] = useState(false);

    const location = useLocation();
    const { state } = location;
    const userId = state?.userId;

    const handleSubmit = () => {
        const formData = new FormData();
        formData.append("file", selectedFile);
        uploadCv(formData, userId)
            .then(() => setIsCvSubmitted(true))

            .catch((err) => console.log("Error:", err));
    };
    return (
        <main className="d-flex justify-content-center align-items-center p-3 vh-100">
            <div
                className="d-flex flex-column col-sm-10 p-4 rounded text-white"
                style={{ backgroundColor: "#2C324C" }}
            >
                <section className="mb-4">
                    <h1 className="text-center">{title}</h1>
                </section>
                {selectedFile.name == null ? (
                    <form className="d-flex justify-content-center">
                        <label htmlFor="cvInput" className="btn text-white">
                            <FontAwesomeIcon
                                icon={faCloudArrowUp}
                                className="fa-10x d-flex mx-auto"
                            />
                            <p>{subtitle}</p>
                        </label>
                        <input
                            type="file"
                            id="cvInput"
                            accept="application/pdf"
                            onChange={({ target }) =>
                                setSelectedFile(Array.from(target.files)[0])
                            }
                            style={{ display: "none" }}
                        />
                    </form>
                ) : (
                    <section id="selectedCv">
                        <SelectedCV
                            fileName={selectedFile.name}
                            fileSize={selectedFile.size}
                            deleteFile={() => setSelectedFile({})}
                        />
                        <button
                            className="btn"
                            onClick={handleSubmit}
                            style={{ backgroundColor: "#ee7600" }}
                        >
                            Soumettre
                        </button>
                    </section>
                )}
            </div>
            {isCvSubmitted && (
                <ErrorMessage
                    message={"CV téléversé avec succès !"}
                    severity="success"
                />
            )}
        </main>
    );
};

Upload.propTypes = {
    title: PropTypes.string.isRequired,
    subtitle: PropTypes.string.isRequired,
};

export default Upload;
