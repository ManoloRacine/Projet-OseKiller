import TimePicker from "react-time-picker";

const AccordDesaccord = ({
    position,
    setFormData,
    formData,
    evaluationQuestions,
}) => {
    return (
        <div>
            <label
                htmlFor={"evaluation-" + position}
                className="form-label mt-3"
            >
                {evaluationQuestions[position]}
            </label>
            <select
                onChange={(e) => {
                    let updatedEvaluation = [...formData.evaluation];
                    if (e.target.value === "null") {
                        updatedEvaluation[position] = null;
                    } else {
                        updatedEvaluation[position] = e.target.value;
                    }

                    setFormData({ ...formData, evaluation: updatedEvaluation });
                }}
                className="form-select"
                id={"evaluation-" + position}
            >
                <option selected value={"null"}>
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

const WorkShiftsPicker = ({ setFormData, formData, position }) => {
    return (
        <div className="row">
            <div className="col-3">
                <TimePicker
                    onChange={(value) => {
                        let updatedworkShifts = [...formData.workShifts];
                        updatedworkShifts[0][0] = value;
                        setFormData({
                            ...formData,
                            workShifts: updatedworkShifts,
                        });
                    }}
                    value={formData.workShifts[position][0]}
                />
            </div>
            <div className="col-3">
                <TimePicker
                    onChange={(value) => {
                        let updatedworkShifts = [...formData.workShifts];
                        updatedworkShifts[0][0] = value;
                        setFormData({
                            ...formData,
                            workShifts: updatedworkShifts,
                        });
                    }}
                    value={formData.workShifts[position][1]}
                />
            </div>
        </div>
    );
};

const EvaluationForm = ({ formData, setFormData, evaluationQuestions }) => {
    const nbEvaluations = [...formData.evaluation];

    return (
        <form>
            <label htmlFor="companyContact" className="form-label mt-3">
                Personne contact
            </label>
            <input
                id="companyContact"
                className="form-control"
                type="text"
                value={formData.companyContact}
                onChange={(e) =>
                    setFormData({ ...formData, companyContact: e.target.value })
                }
            />
            <label htmlFor="address" className="form-label mt-3">
                Adresse
            </label>
            <input
                id="address"
                className="form-control"
                type="text"
                value={formData.adress}
                onChange={(e) =>
                    setFormData({ ...formData, address: e.target.value })
                }
            />
            <label htmlFor="city" className="form-label mt-3">
                Ville
            </label>
            <input
                id="city"
                className="form-control"
                type="text"
                value={formData.city}
                onChange={(e) =>
                    setFormData({ ...formData, city: e.target.value })
                }
            />
            <label htmlFor="postalCode" className="form-label mt-3">
                Code postal
            </label>
            <input
                id="postalCode"
                className="form-control"
                type="text"
                value={formData.postalCode}
                onChange={(e) =>
                    setFormData({ ...formData, postalCode: e.target.value })
                }
            />
            <label htmlFor="phoneNumber" className="form-label mt-3">
                Téléphone
            </label>
            <input
                id="phoneNumber"
                className="form-control"
                type="text"
                value={formData.phoneNumber}
                onChange={(e) =>
                    setFormData({ ...formData, phoneNumber: e.target.value })
                }
            />
            <label htmlFor="fax" className="form-label mt-3">
                Télécopieur
            </label>
            <input
                id="fax"
                className="form-control"
                type="text"
                value={formData.fax}
                onChange={(e) =>
                    setFormData({ ...formData, fax: e.target.value })
                }
            />
            <label htmlFor="internshipNo" className="form-label mt-3">
                Le stagiaire est à quel stage ?
            </label>
            <select
                onChange={(e) =>
                    setFormData({ ...formData, internshipNo: e.target.value })
                }
                className="form-select"
            >
                <option selected>Stage ?</option>
                <option value={1}>Premier Stage</option>
                <option value={2}>Deuxième Stage</option>
            </select>
            {nbEvaluations.map((value, index) => (
                <AccordDesaccord
                    position={index}
                    formData={formData}
                    setFormData={setFormData}
                    evaluationQuestions={evaluationQuestions}
                />
            ))}
            <label htmlFor="comment" className="form-label mt-3">
                Commentaires
            </label>
            <textarea
                id="comment"
                className="form-control"
                type="text"
                value={formData.comment}
                onChange={(e) =>
                    setFormData({ ...formData, comment: e.target.value })
                }
            />
            <label htmlFor="preferredInternship" className="form-label mt-3">
                Ce milieu est à privilégier pour le
            </label>
            <select
                onChange={(e) =>
                    setFormData({
                        ...formData,
                        preferredInternship: e.target.value,
                    })
                }
                className="form-select"
                id="preferredInternship"
            >
                <option selected>Stage ?</option>
                <option value={1}>Premier Stage</option>
                <option value={2}>Deuxième Stage</option>
            </select>
            <label htmlFor="internNbs" className="form-label mt-3">
                le nombre de stagiaires que la compagnie est ouverte à
                accueillir
            </label>
            <input
                id="internNbs"
                className="form-control"
                type="number"
                value={formData.internNbs}
                onChange={(e) =>
                    setFormData({ ...formData, city: e.target.value })
                }
            />
            <div class="form-check mt-3">
                <input
                    class="form-check-input"
                    type="checkbox"
                    value={formData.keepIntern}
                    onChange={(e) => {
                        setFormData({
                            ...formData,
                            keepIntern: e.target.checked,
                        });
                    }}
                    id="keepIntern"
                />
                <label class="form-check-label" for="keepIntern">
                    La compagnie veut-elle garder le stagiaire ?
                </label>
            </div>

            <div class="form-check mt-3">
                <input
                    class="form-check-input"
                    type="checkbox"
                    value={formData.keepIntern}
                    onChange={(e) => {
                        setFormData({
                            ...formData,
                            variableWorkShifts: e.target.checked,
                        });
                    }}
                    id="variableWorkShifts"
                />
                <label class="form-check-label" for="keepIntern">
                    Ce milieu offre des quarts de travail variables ?
                </label>
            </div>

            {formData.variableWorkShifts == true
                ? formData.workShifts.map((value, index) => (
                      <WorkShiftsPicker
                          position={index}
                          formData={formData}
                          setFormData={setFormData}
                      />
                  ))
                : null}

            <button
                onClick={(e) => {
                    e.preventDefault();
                    console.log(formData);
                }}
            >
                test
            </button>
        </form>
    );
};

export default EvaluationForm;
