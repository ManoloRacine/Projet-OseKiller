import React from "react";

import { useState } from "react";
import Snackbar from "@mui/material/Snackbar";
import MuiAlert from "@mui/material/Alert";

const ErrorMessage = (props) => {
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
      <Alert onClose={handleClose} severity={props.severity} sx={{ width: "100%" }}>
        {props.message}
      </Alert>
    </Snackbar>
  );
};

export default ErrorMessage;
