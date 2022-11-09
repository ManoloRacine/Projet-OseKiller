const AgreeDisagree = ({ position, setFormData, formData }) => {
    return (
        <div>
            <label
                htmlFor={"evaluation-" + position}
                className="form-label mt-3"
            >
                {formData.evaluation[position].question}
            </label>
            <select
                onChange={(e) => {
                    let updatedEvaluation = [...formData.evaluation];
                    if (e.target.value === "") {
                        updatedEvaluation[position].answer = null;
                    } else {
                        updatedEvaluation[position].answer = e.target.value;
                    }

                    setFormData({ ...formData, evaluation: updatedEvaluation });
                }}
                className="form-select"
                id={"evaluation-" + position}
                required
            >
                <option selected value={""}>
                    Choix
                </option>
                <option value={0}>Impossible de se prononcer</option>
                <option value={1}>Totalement en désaccord</option>
                <option value={2}>Plutôt en désaccord</option>
                <option value={3}>Plutôt en accord</option>
                <option value={4}>Totalement en accord</option>
            </select>
        </div>
    );
};

export default AgreeDisagree;
