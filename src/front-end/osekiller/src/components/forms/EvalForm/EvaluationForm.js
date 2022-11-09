import AgreeDisagree from "./AgreeDisagree";
import WorkShiftsPicker from "./WorkShiftsPicker";
import { evaluateInternship } from "../../../services/TeacherService";

const EvaluationForm = ({
    formData,
    setFormData,
    evaluationQuestions,
    contractInfo,
}) => {
    const nbEvaluations = [...formData.evaluation];

    const handleSubmit = (e) => {
        e.preventDefault();
        evaluateInternship(contractInfo.contractId, formData);
    };

    return (
        <form onSubmit={handleSubmit}>
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
                required
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
                required
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
                required
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
                required
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
                required
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
                required
            />
            <label htmlFor="internshipNo" className="form-label mt-3">
                Le stagiaire est à quel stage ?
            </label>
            <select
                onChange={(e) =>
                    setFormData({ ...formData, internshipNo: e.target.value })
                }
                className="form-select"
                required
            >
                <option selected>Stage ?</option>
                <option value={1}>Premier Stage</option>
                <option value={2}>Deuxième Stage</option>
            </select>
            {nbEvaluations.map((value, index) => (
                <AgreeDisagree
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
                required
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
                required
            >
                <option selected>Stage ?</option>
                <option value={1}>Premier Stage</option>
                <option value={2}>Deuxième Stage</option>
            </select>
            <label htmlFor="internNo" className="form-label mt-3">
                le nombre de stagiaires que la compagnie est ouverte à
                accueillir
            </label>
            <input
                id="internNo"
                className="form-control"
                type="number"
                value={formData.internNbs}
                onChange={(e) =>
                    setFormData({ ...formData, internNo: e.target.value })
                }
                required
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

            {formData.variableWorkShifts
                ? formData.workShifts.map((value, index) => (
                      <WorkShiftsPicker
                          position={index}
                          formData={formData}
                          setFormData={setFormData}
                      />
                  ))
                : null}

            <input type={"submit"} />
        </form>
    );
};

export default EvaluationForm;
