import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { faArrowLeft, faCloudArrowUp } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { uploadCv } from "../services/UploadService";

const UploadCv = () => {
    const [selectedFiles, setSelectedFiles] = useState([]);
    const navigate = useNavigate();

    const showSelectedFiles = ({ target }) => {
        const uploadedFiles = Array.from(target.files);
        const uploadedFilesName = [];
        uploadedFiles.forEach((file) => {
            uploadedFilesName.push(file);
        });
        setSelectedFiles(uploadedFilesName);
    };

    const handleSubmit = () => {
        uploadCv(selectedFiles, 1) // TODO : Changer le 1 pour le id du user
            .then((response) => console.log("Success:", response))
            .catch((err) => console.log("Error:", err));
    };

    return (
        <div className="d-flex justify-content-center  p-3">
            <main
                className="d-flex flex-column col-sm-10 p-4 rounded text-white"
                style={{ backgroundColor: "#2C324C", minHeight: "90vh" }}
            >
                <section className="mb-4">
                    <button
                        className="btn btn-primary float-left"
                        onClick={() => navigate("/dashboard")}
                    >
                        <FontAwesomeIcon icon={faArrowLeft} className="me-2" />
                        Dashboard
                    </button>
                    <h1 className="text-center">Téléverser votre CV</h1>
                </section>
                <form className="mb-3">
                    <label
                        htmlFor="formFileMultiple"
                        className="form-label fs-5"
                    >
                        Veuillez choisir un ou plusieurs CV
                    </label>
                    <input
                        className="form-control"
                        type="file"
                        id="formFileMultiple"
                        onChange={showSelectedFiles}
                        multiple
                    />
                </form>
                <section
                    id="selectedCv"
                    className={selectedFiles.length === 0 ? "my-auto" : ""}
                >
                    {selectedFiles.length === 0 ? (
                        <FontAwesomeIcon
                            icon={faCloudArrowUp}
                            className="fa-10x d-flex mx-auto"
                        />
                    ) : (
                        <ul className="p-0">
                            {selectedFiles.map((file, i) => (
                                <li key={i}>
                                    <div className="d-flex justify-content-between">
                                        <p>{file.name}</p>
                                        <p>
                                            {(file.size / 1000).toFixed(1)} Ko
                                        </p>
                                    </div>
                                </li>
                            ))}
                        </ul>
                    )}
                </section>
                <section
                    className={
                        selectedFiles.length === 0
                            ? "d-flex flex-row-reverse align-items-bottom"
                            : "d-flex flex-row-reverse align-items-bottom mt-auto"
                    }
                >
                    <button
                        className="btn"
                        style={{ backgroundColor: "#ee7600" }}
                        onClick={handleSubmit}
                    >
                        Envoyer
                    </button>
                </section>
            </main>
        </div>
    );
};

export default UploadCv;
