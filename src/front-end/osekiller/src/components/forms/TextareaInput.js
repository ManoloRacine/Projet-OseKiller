import React from "react";
import PropTypes from "prop-types";

const TextareaInput = ({ inputName, inputValue, changeInputValue }) => {
    return (
        <>
            <label htmlFor={inputName}>Commentaire</label>
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
};

export default TextareaInput;
