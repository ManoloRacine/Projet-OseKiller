import { useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { faArrowLeft, faCloudArrowUp } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { uploadCv } from "../services/UploadService";
import SelectedCV from "../components/SelectedCV";

const UploadCv = () => {
    const [selectedFiles, setSelectedFiles] = useState([]);
    const navigate = useNavigate();
    const location = useLocation();
    const { userId } = location.state;

    const showSelectedFiles = ({ target }) => {
        const uploadedFiles = Array.from(target.files[0]);
        const uploadedFilesName = [];
        uploadedFiles.forEach((file) => {
            uploadedFilesName.push(file);
        });
        setSelectedFiles(uploadedFiles);
    };

    const handleSubmit = () => {
        uploadCv(selectedFiles, userId)
            .then((response) => console.log("Success:", response))
            .catch((err) => console.log("Error:", err));
    };

    const handleDelete = (name) => {
        setSelectedFiles(selectedFiles.filter((file) => file.name !== name));
    };

    return (
        <div className="d-flex justify-content-center  p-3">
            {console.log(selectedFiles)}
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
                    <label htmlFor="cvInput" className="btn text-white">
                        <FontAwesomeIcon
                            icon={faCloudArrowUp}
                            className="fa-10x d-flex mx-auto"
                        />
                    </label>
                    <input
                        type="file"
                        id="cvInput"
                        accept="application/pdf"
                        onChange={showSelectedFiles}
                        style={{ display: "none" }}
                    />
                </form>
                <section id="selectedCv">
                    {selectedFiles.map((file, i) => (
                        <SelectedCV
                            key={i}
                            fileName={file.name}
                            fileSize={file.size}
                            deleteFile={handleDelete}
                        />
                        // <li key={i}>
                        //     <div>
                        //         <p>{file.name}</p>
                        //         <span className="badge bg-primary rounded-pill">
                        //             {(file.size / 1000).toFixed(1)} Ko
                        //         </span>
                        //     </div>
                        //     <button
                        //         className="btn"
                        //         style={{ backgroundColor: "#ee7600" }}
                        //         onClick={handleSubmit}
                        //     >
                        //         Envoyer
                        //     </button>
                        // </li>
                    ))}
                </section>
                {/*  À décommenter si le prof veut plusieurs upload de CV
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
                </section> */}
            </main>
        </div>
    );
};

export default UploadCv;
