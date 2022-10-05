import { useEffect, useState  } from "react";
import { getStudents } from "../services/UserServices" ;
import { faArrowLeft, faCloudArrowUp } from "@fortawesome/free-solid-svg-icons";
import { Link, useNavigate, useLocation } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { uploadCv } from "../services/UploadService";


const StudentCvs = () => {
    const [students, setStudents] = useState([])
    const navigate = useNavigate();
    const location = useLocation();

    useEffect(() => {
        getStudents().then((response) => {
            setStudents(response.data)
            console.log(response.data)
        })
        
    }, []) ;



    return (
        <div className="row">
            
            <div className="col-4">
                <button
                    className="btn btn-primary float-left"
                    onClick={() => navigate("/dashboard")}
                >
                    <FontAwesomeIcon icon={faArrowLeft} className="me-2" />
                    Dashboard
                </button>
            </div>
            <div className="col-4">
                <ul className="text-center list-unstyled">
                    {students.map((student,index) =>
                    student["cvPresent"] === true && student["cvRejected"] === false && student["cvValidated"] === false ?
                    <li><Link to={"../validate-cv"} state={{ studentId : student["id"]}} key={index}>{student["name"]}</Link></li>
                    :
                    null)}
                </ul>
            </div>
            <div className="col-4"></div>
        </div>
    )
}

export default StudentCvs ;