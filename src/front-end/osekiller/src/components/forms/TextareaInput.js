import React from "react";
import PropTypes from "prop-types";

const TextareaInput = ({ label, inputName, inputValue, changeInputValue }) => {
    return (
        <>
            <label htmlFor={inputName}>{label}</label>
            <textarea
                name={inputName}
                id={inputName}
                className={"form-control mb-2"}
                value={inputValue}
                onChange={changeInputValue}
            ></textarea>
        </>
    );
};

TextareaInput.propTypes = {
    inputName: PropTypes.string.isRequired,
    inputValue: PropTypes.any.isRequired,
    changeInputValue: PropTypes.func.isRequired,
    label: PropTypes.string,
};

TextareaInput.defaultProps = {
    label: "Commentaire",
};

export default TextareaInput;
