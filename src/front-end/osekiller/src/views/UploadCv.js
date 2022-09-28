import { useState } from "react";

const UploadCv = () => {
    const [selectedFiles, setSelectedFiles] = useState([]);

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
                <h1 className="text-center">Téléverser votre CV</h1>
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
                <section id="selectedCv">
                    <ul className="p-0">
                        {selectedFiles.map((fileName, i) => (
                            <li key={i}>{fileName}</li>
                        ))}
                    </ul>
                </section>
                <section className="d-flex flex-row-reverse align-items-bottom mt-auto">
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
