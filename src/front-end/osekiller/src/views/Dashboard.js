import {useContext, useEffect, useState} from "react";
import {AuthenticatedUserContext} from "../App";
import {getCV} from "../services/CvServices";
import {getStudent} from "../services/UserService";

const Dashboard = () => {
    const [userPdf, setUserPdf] = useState("");
    const [studentInfo, setStudentInfo] = useState({});

    const {authenticatedUser} = useContext(AuthenticatedUserContext);

    useEffect(() => {        
        if (authenticatedUser.role === "STUDENT") {
            getStudent(authenticatedUser.id).then((response) => {
                setStudentInfo(response.data);
            });
            getCV(authenticatedUser.id).then((response) => {
                if (response.status !== 204) {
                    var blob1 = new Blob([response.data], {
                        type: "application/pdf",
                    });
                    var data_url = window.URL.createObjectURL(blob1);
                    setUserPdf(data_url);
                }
            });
        }
    }, [authenticatedUser.id, authenticatedUser.role]);

    return (
        <div className="p-3">
            <h1>{`Bonjour, ${authenticatedUser.name}`}</h1>
            
            <div className="row">
                <div className="col-6"></div>
                <div className="col-6">
                    {authenticatedUser.role === "STUDENT" ? (
                        userPdf !== "" ? (
                            <iframe
                                title="studentCv"
                                src={userPdf}
                                height="600px"
                                width="100%"
                            ></iframe>
                        ) : (
                            <p>Vous n'avez pas téléversé de CV</p>
                        )
                    ) : null}
                    {authenticatedUser.role === "STUDENT" &&
                    studentInfo["cvPresent"] &&
                    (studentInfo["cvRejected"] === true ||
                        studentInfo["cvValidated"] === true) ? (
                        <p>{studentInfo["feedback"]}</p>
                    ) : null}
                </div>
            </div>
        </div>
    );
};

export default Dashboard;
