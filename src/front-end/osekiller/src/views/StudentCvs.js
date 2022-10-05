import { useEffect, useState  } from "react";
import { getStudents } from "../services/UserServices" ;
import { Link, useNavigate } from "react-router-dom";


const StudentCvs = () => {
    const [students, setStudents] = useState([])

    useEffect(() => {
        getStudents().then((response) => {
            setStudents(response.data)
            console.log(response.data)
        })
        
    }, []) ;



    return (
        <div className="row">
            <div className="col-4"></div>
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