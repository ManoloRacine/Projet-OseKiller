import { useState } from "react"

const InviteStudent = ({student}) => {
    const [firstDate, setFirstDate] = useState("")
    const [secondDate, setSecondDate] = useState("")
    const [thirdDate, setThirdDate] = useState("")

    return (
        <div>
            Invitations {student.name}
            <form className="row">
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
            </form>
            <div>
                Dates de convocations :
            </div>
            <p>{firstDate}</p>
        </div>
    )
}

export default InviteStudent;