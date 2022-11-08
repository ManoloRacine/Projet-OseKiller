const AccordDesaccord = ({ position, setFormData, formData }) => {
    return (
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
    );
};

const EvaluationForm = ({ formData, setFormData }) => {
    const nbEvaluations = [...formData.evaluation];

    return (
        <form>
            <label htmlFor="companyContact" className="form-label">
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
            <label htmlFor="adress" className="form-label">
                Adresse
            </label>
            <input
                id="adress"
                className="form-control"
                type="text"
                value={formData.adress}
                onChange={(e) =>
                    setFormData({ ...formData, adress: e.target.value })
                }
            />
            <label htmlFor="city" className="form-label">
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
            <label htmlFor="postalCode" className="form-label">
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
            <label htmlFor="phoneNumber" className="form-label">
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
            <label htmlFor="fax" className="form-label">
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
                />
            ))}
            <label htmlFor="comment" className="form-label">
                Commentaire
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
            <button
                onClick={(e) => {
                    e.preventDefault();
                    console.log(formData);
                }}
            >
                test
            </button>
            <select
                onChange={(e) =>
                    setFormData({
                        ...formData,
                        preferredInternship: e.target.value,
                    })
                }
                className="form-select"
            >
                <option selected>Stage ?</option>
                <option value={1}>Premier Stage</option>
                <option value={2}>Deuxième Stage</option>
            </select>
            <label htmlFor="internNb" className="form-label">
                Ville
            </label>
            <input
                id="city"
                className="form-control"
                type="number"
                value={formData.city}
                onChange={(e) =>
                    setFormData({ ...formData, city: e.target.value })
                }
            />
        </form>
    );
};

export default EvaluationForm;
