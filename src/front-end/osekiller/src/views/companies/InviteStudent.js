import { useEffect, useState } from "react"
import { useLocation } from "react-router-dom";
import LoadPdf from "../../components/LoadPdf"
import { sendConvocation } from "../../services/CompanyService";
import { getCv } from "../../services/StudentService"
import ErrorMessage from "../../components/ErrorMessage";

const InviteStudent = () => {
    const [firstDate, setFirstDate] = useState("")
    const [secondDate, setSecondDate] = useState("")
    const [thirdDate, setThirdDate] = useState("")
    const [pdf, setPdf] = useState("")
    const [isOpen, setIsOpen] = useState(false);
    const [hasOpenError, setHasOpenError] = useState(false);
    const location = useLocation();
    const { studentEmail, studentId, offerId } = location.state ;

    useEffect(() => {
        getCv(studentId).then((response) => {
            const blob1 = new Blob([response.data], {
                type: "application/pdf",
            });
            const data_url = window.URL.createObjectURL(blob1);
            setPdf(data_url);
        }) ;
    }, [])

    const handleSubmit = (e) => {
        e.preventDefault() ;
        sendConvocation({offerId : offerId, dates : [firstDate, secondDate, thirdDate]}, studentId).then(() => setIsOpen(true)).catch(() => setHasOpenError(true));
    }

    return (
        <div>
            <div className="row">
                <div className="col-2"></div>
                <div className="col-8">
                    <LoadPdf
                        src={pdf}
                        width={"100%"}
                        title={`${studentEmail}-cv`}
                        type={"application/pdf"}
                        height={"500px"}
                    />
                </div>
                <div className="col-2"></div>
            </div>
            
            <form className="row" onSubmit={handleSubmit}>
                <div className="col-4">
                    <label htmlFor="firstDate" className={"form-label"}>
                        Première date d'entrevue
                    </label>
                    <input
                        type="date"
                        className={"form-control"}
                        id={"firstDate"}
                        value={firstDate}
                        onChange={e => setFirstDate(e.target.value)}
                        required
                    />
                </div>
                <div className="col-4">
                    <label htmlFor="secondDate" className={"form-label"}>
                        Deuxième date d'entrevue
                    </label>
                    <input
                        type="date"
                        className={"form-control"}
                        id={"secondDate"}
                        value={secondDate}
                        onChange={e => setSecondDate(e.target.value)}
                        required
                    />
                </div>
                <div className="col-4">
                    <label htmlFor="thirdDate" className={"form-label"}>
                        Troisième date d'entrevue
                    </label>
                    <input
                        type="date"
                        className={"form-control"}
                        id={"thirdDate"}
                        value={thirdDate}
                        onChange={e => setThirdDate(e.target.value)}
                        required
                    />
                </div>
                <input type={"submit"}
                    className={"btn btn-primary"}
                    disabled={firstDate === "" || secondDate === "" || thirdDate === ""}
                ></input>
            </form>
            {isOpen && (
                <ErrorMessage
                    message={"Votre convocation a été envoyée !"}
                    severity="success"
                />
            )}
            {hasOpenError && (
                <ErrorMessage
                    message={"Votre convocation n'as pas été envoyée !"}
                    severity="error"
                />
            )}
        </div>
    )
}

export default InviteStudent;