import React from "react";
import PropTypes from "prop-types";

const SelectInput = ({
    label,
    inputName,
    inputValue,
    changeInputValue,
    optionsValues,
}) => {
    return (
        <>
            <label htmlFor={inputName} className="form-label">
                {label}
            </label>
            <select
                id={inputName}
                className="form-select mb-2"
                value={inputValue}
                onChange={changeInputValue}
                required
            >
                {optionsValues.map((option, index) =>
                    option[1] === "" ? (
                        <option key={index} value={option[1]} disabled hidden>
                            {option[0]}
                        </option>
                    ) : (
                        <option key={index} value={option[1]}>
                            {option[0]}
                        </option>
                    )
                )}
            </select>
        </>
    );
};

SelectInput.propTypes = {
    label: PropTypes.string.isRequired,
    inputName: PropTypes.string.isRequired,
    inputValue: PropTypes.any.isRequired,
    changeInputValue: PropTypes.func.isRequired,
    optionsValues: PropTypes.array,
};

SelectInput.defaultProps = {
    optionsValues: [
        ["Veuillez choisir une réponse", ""],
        ["Totalement en accord", 0],
        ["Plutôt en accord", 1],
        ["Plutôt en désaccord", 2],
        ["Totalement en désaccord", 3],
        ["N/A", 4],
    ],
};

export default SelectInput;
