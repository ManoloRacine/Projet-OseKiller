import React, { useRef, useState } from "react";
import SignaturePad from "react-signature-canvas";
import PropTypes from "prop-types";

const Signature = ({ saveData }) => {
    const sigPad = useRef({});

    const handleSave = () => {
        saveData(sigPad.current.toDataURL());
    };

    return (
        <div>
            <SignaturePad ref={sigPad} backgroundColor={"#D3D3D3"} />
            <div className={"d-flex"}>
                <button className={"btn btn-primary me-1"} onClick={handleSave}>
                    Sauvegarder
                </button>
                <button
                    className={"btn btn-primary"}
                    onClick={() => sigPad.current.clear()}
                >
                    Effacer
                </button>
            </div>
        </div>
    );
};

Signature.propTypes = {
    saveData: PropTypes.func.isRequired,
};

export default Signature;
