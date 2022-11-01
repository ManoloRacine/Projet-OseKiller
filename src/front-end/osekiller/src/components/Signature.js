import React, { useRef, useState } from "react";
import SignaturePad from "react-signature-canvas";
import PropTypes from "prop-types";

const Signature = ({ saveData }) => {
    const sigPad = useRef({});
    const [data, setData] = useState("");

    const handleSave = () => {
        setData(sigPad.current.toDataURL());
        saveData(data);
    };

    return (
        <div>
            <SignaturePad
                ref={sigPad}
                backgroundColor={"white"}
                style={{ backgroundColor: "#2C324C" }}
            />
            <div className={"d-flex"}>
                <button
                    className={"btn btn-primary me-1"}
                    onClick={() => sigPad.current.clear()}
                >
                    Effacer
                </button>
                <button className={"btn btn-primary"} onClick={handleSave}>
                    Sauvegarder
                </button>
            </div>
        </div>
    );
};

Signature.propTypes = {
    saveData: PropTypes.func.isRequired,
};

export default Signature;
