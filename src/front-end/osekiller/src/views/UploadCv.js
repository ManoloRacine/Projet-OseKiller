import { useState } from "react";
import { useLocation } from "react-router-dom";
import { faCloudArrowUp } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { uploadCv } from "../services/UploadService";
import SelectedCV from "../components/SelectedCV";

const UploadCv = () => {
    const [selectedFile, setSelectedFile] = useState({});
    const location = useLocation();
    const { userId } = location.state;

    const handleSubmit = () => {
        const formData = new FormData();
        formData.append("file", selectedFile);
        uploadCv(formData, userId)
            .then((response) => console.log("Success:", response))
            .catch((err) => console.log("Error:", err));
    };

    return (
        <div className="d-flex justify-content-center align-items-center p-3 vh-100">
            <main
                className="d-flex flex-column col-sm-10 p-4 rounded text-white"
                style={{ backgroundColor: "#2C324C" }}
            >
                <section className="mb-4">
                    <h1 className="text-center">Téléverser votre CV</h1>
                </section>
                {selectedFile.name == null ? (
                    <form className="d-flex justify-content-center">
                        <label htmlFor="cvInput" className="btn text-white">
                            <FontAwesomeIcon
                                icon={faCloudArrowUp}
                                className="fa-10x d-flex mx-auto"
                            />
                            <p>Choisir votre CV</p>
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
            </main>
        </div>
    );
};

export default UploadCv;
