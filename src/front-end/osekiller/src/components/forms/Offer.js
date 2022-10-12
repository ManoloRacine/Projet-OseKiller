import React from "react";
import PropTypes from "prop-types";

const Offer = ({ position, salary, startDate, endDate }) => {
    return (
        <div>
            <p>{position}</p>
            <p>{salary}</p>
            <p>{startDate}</p>
            <p>{endDate}</p>
        </div>
    );
};

Offer.propTypes = {
    position: PropTypes.string.isRequired,
    salary: PropTypes.string.isRequired,
    startDate: PropTypes.string.isRequired,
    endDate: PropTypes.string.isRequired,
};

export default Offer;
