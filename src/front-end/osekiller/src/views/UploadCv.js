import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { faArrowLeft, faCloudArrowUp } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const UploadCv = () => {
    const [selectedFiles, setSelectedFiles] = useState([]);
    const navigate = useNavigate();

    const showSelectedFiles = ({ target }) => {
        const uploadedFiles = Array.from(target.files);
        const uploadedFilesName = [];
        uploadedFiles.forEach((file) => {
            uploadedFilesName.push(file.name);
        });
        setSelectedFiles(uploadedFilesName);
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
                            {selectedFiles.map((fileName, i) => (
                                <li key={i}>{fileName}</li>
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
                    >
                        Envoyer
                    </button>
                </section>
            </main>
        </div>
    );
};

export default UploadCv;
