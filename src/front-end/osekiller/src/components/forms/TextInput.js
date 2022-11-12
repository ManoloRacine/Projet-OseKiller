import React from "react";
import PropTypes from "prop-types";

const TextInput = ({
    label,
    inputName,
    inputType,
    inputValue,
    changeInputValue,
}) => {
    return (
        <>
            <label htmlFor={inputName} className="form-label">
                {label}
            </label>
            <input
                type={inputType}
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
    inputType: PropTypes.string,
};

TextInput.defaultProps = {
    inputType: "text",
};

export default TextInput;
