import { useEffect, useState  } from "react";
import { getStudents } from "../services/UserServices" ;


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
                <ul className="text-center">
                    {students.map((student,index) =>
                    student["cvPresent"] === true && student["cvRejected"] === false && student["cvValidated"] === false ?
                    <li key={index}>{student["name"]}</li>
                    :
                    null)}
                </ul>
            </div>
            <div className="col-4"></div>
        </div>
    )
}

export default StudentCvs ;