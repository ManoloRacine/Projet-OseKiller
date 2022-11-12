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
            >
                {optionsValues.map((option, index) => (
                    <option value={index}>{option}</option>
                ))}
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
        "Totalement en accord",
        "Plutôt en accord",
        "Plutôt en désaccord",
        "Totalement en désaccord",
        "N/A",
    ],
};

export default SelectInput;
