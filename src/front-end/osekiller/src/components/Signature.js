import React, { useRef, useState } from "react";
import SignaturePad from "react-signature-canvas";
import PropTypes from "prop-types";

const Signature = ({ saveData }) => {
    const sigPad = useRef({});
    const [data, setData] = useState("");

    const handleSave = () => {
        setData(sigPad.current.toDataURL());
        saveData(sigPad.current.toDataURL());
    };

    return (
        <div>
            <SignaturePad
                ref={sigPad}
                backgroundColor={"#D3D3D3"}
                style={{ border: "1px solid black" }}
            />
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
