import {faTrash} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

const SelectedCV = ({ fileName, fileSize, deleteFile }) => {
    return (
        <div className="d-flex justify-content-between align-items-center form-control my-2">
            <div className="ms-2 me-auto">
                <div className="fw-bold">{fileName}</div>
                <span className="badge bg-primary rounded-pill">
                    {(fileSize / 1000).toFixed(1)} Ko
                </span>
            </div>
            <button
                className="btn btn-danger mx-2"
                onClick={() => deleteFile(fileName)}
            >
                <FontAwesomeIcon icon={faTrash} />
            </button>
        </div>
    );
};

export default SelectedCV;
