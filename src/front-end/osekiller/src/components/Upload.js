import PropTypes from "prop-types";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCloudArrowUp } from "@fortawesome/free-solid-svg-icons";
import SelectedCV from "./SelectedCV";
import { useLocation } from "react-router-dom";
import ErrorMessage from "./ErrorMessage";

const Upload = ({
    title,
    subtitle,
    selectedFile,
    onChange,
    onDelete,
    onSubmit,
    isSubmitted,
}) => {
    const location = useLocation();
    const { state } = location;
    const userId = state?.userId;

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
                        <label htmlFor="pdfInput" className="btn text-white">
                            <FontAwesomeIcon
                                data-testid="uploadIcon"
                                icon={faCloudArrowUp}
                                className="fa-10x d-flex mx-auto"
                            />
                            <p>{subtitle}</p>
                        </label>
                        <input
                            type="file"
                            id="pdfInput"
                            data-testid="pdfInput"
                            accept="application/pdf"
                            onChange={onChange}
                            style={{ display: "none" }}
                        />
                    </form>
                ) : (
                    <section id="selectedCv">
                        <SelectedCV
                            fileName={selectedFile.name}
                            fileSize={selectedFile.size}
                            deleteFile={onDelete}
                        />
                        <button
                            className="btn"
                            onClick={() => onSubmit(userId)}
                            style={{ backgroundColor: "#ee7600" }}
                        >
                            Soumettre
                        </button>
                    </section>
                )}
            </div>
            {isSubmitted && (
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
    selectedFile: PropTypes.object.isRequired,
    onChange: PropTypes.func.isRequired,
    onDelete: PropTypes.func.isRequired,
    onSubmit: PropTypes.func.isRequired,
    isSubmitted: PropTypes.bool.isRequired,
};

export default Upload;