import React from "react";
import PropTypes from "prop-types";

const SelectInput = ({ label, inputName, inputValue, changeInputValue }) => {
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
                <option>Totalement en accord</option>
                <option>Plutôt en accord</option>
                <option>Plutôt en désaccord</option>
                <option>Totalement en désaccord</option>
                <option>N/A</option>
            </select>
        </>
    );
};

SelectInput.propTypes = {
    label: PropTypes.string.isRequired,
    inputName: PropTypes.string.isRequired,
    inputValue: PropTypes.any.isRequired,
    changeInputValue: PropTypes.func.isRequired,
};

export default SelectInput;
