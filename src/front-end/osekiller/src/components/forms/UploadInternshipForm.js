import PropTypes from "prop-types";

const UploadInternshipForm = ({
    position,
    setPosition,
    salary,
    setSalary,
    startDate,
    setStartDate,
    endDate,
    setEndDate,
}) => {
    return (
        <form>
            <label htmlFor={"position"} className={"form-label"}>
                Position
            </label>
            <input
                id={"position"}
                className={"form-control"}
                type="text"
                value={position}
                onChange={setPosition}
            />

            <label htmlFor="salary" className={"form-label"}>
                Salaire
            </label>
            <input
                type="number"
                className={"form-control"}
                id={"salary"}
                value={salary}
                onChange={setSalary}
            />

            <label htmlFor="startDate" className={"form-label"}>
                Date de début
            </label>
            <input
                type="date"
                className={"form-control"}
                id={"startDate"}
                value={startDate}
                onChange={setStartDate}
            />

            <label htmlFor="endDate" className={"form-label"}>
                Date de fin
            </label>
            <input
                type="date"
                className={"form-control"}
                id={"endDate"}
                value={endDate}
                onChange={setEndDate}
            />
        </form>
    );
};

UploadInternshipForm.propTypes = {
    position: PropTypes.string.isRequired,
    setPosition: PropTypes.func.isRequired,
    salary: PropTypes.string.isRequired,
    setSalary: PropTypes.func.isRequired,
    startDate: PropTypes.string.isRequired,
    setStartDate: PropTypes.func.isRequired,
    endDate: PropTypes.string.isRequired,
    setEndDate: PropTypes.func.isRequired,
};

export default UploadInternshipForm;
