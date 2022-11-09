import React from "react";
import PropTypes from "prop-types";

const TextInput = ({ label, inputName, inputValue, changeInputValue }) => {
    return (
        <>
            <label htmlFor={inputName} className="form-label">
                {label}
            </label>
            <input
                type={"text"}
                className="form-control mb-2"
                id={inputName}
                value={inputValue}
                onChange={changeInputValue}
            />
        </>
    );
};

TextInput.propTypes = {
    label: PropTypes.string.isRequired,
    inputName: PropTypes.string.isRequired,
    inputValue: PropTypes.any.isRequired,
    changeInputValue: PropTypes.func.isRequired,
};

export default TextInput;
