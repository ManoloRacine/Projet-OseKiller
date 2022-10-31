import React, { useState } from "react";
import Snackbar from "@mui/material/Snackbar";
import MuiAlert from "@mui/material/Alert";
import PropTypes from "prop-types";

const ErrorMessage = ({ message, severity }) => {
    const [isOpen, setIsOpen] = useState(true);
    const Alert = React.forwardRef(function Alert(props, ref) {
        return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
    });

    const handleClose = (reason) => {
        if (reason === "clickaway") {
            return;
        }
        setIsOpen(false);
    };

    return (
        <Snackbar open={isOpen} autoHideDuration={6000} onClose={handleClose}>
            <Alert
                onClose={handleClose}
                severity={severity}
                sx={{ width: "100%" }}
            >
                {message}
            </Alert>
        </Snackbar>
    );
};

ErrorMessage.propTypes = {
    message: PropTypes.string.isRequired,
    severity: PropTypes.string.isRequired,
};

export default ErrorMessage;
