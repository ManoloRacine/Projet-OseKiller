import {useContext, useEffect, useState} from "react";
import {AuthenticatedUserContext} from "../App";
import {getCv, getStudent} from "../services/StudentService";


const Dashboard = () => {
    const [userPdf, setUserPdf] = useState("");
    const [studentInfo, setStudentInfo] = useState({});

    const {authenticatedUser} = useContext(AuthenticatedUserContext);

    useEffect(() => {
        if (authenticatedUser.role === "STUDENT") {
            getStudent(authenticatedUser.id).then((response) => {
                setStudentInfo(response.data);
            });
            getCv(authenticatedUser.id).then((response) => {
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
                <div className="col-6">
                    {authenticatedUser.role === "STUDENT" && studentInfo["cvValidated"] ? (<h3 className="text-success">CV est valide</h3>) : null}
                    {authenticatedUser.role === "STUDENT" && studentInfo["cvRejected"] ? (<h3 className="text-danger">CV n'est pas valide</h3>) : null}
                    {authenticatedUser.role === "STUDENT" && studentInfo["cvPresent"] && (studentInfo["cvRejected"] || studentInfo["cvValidated"]) ?
                    <div><h4>Feedback :</h4><p>{studentInfo["feedback"]}</p></div> :
                    studentInfo['cvPresent'] === true ? <h4 className="text-warning">CV en attente de validation</h4> : null }
                </div>
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
                </div>
            </div>
        </div>
    );
};

export default Dashboard;
