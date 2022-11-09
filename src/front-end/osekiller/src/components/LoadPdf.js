import PropTypes from "prop-types";

const LoadPdf = ({ title, type, src, height, width }) => {
    return (
        <object
            title={title}
            type={type}
            data={src}
            height={height}
            width={width}
            data-testid={"pdf"}
        ></object>
    );
};

LoadPdf.propTypes = {
    title: PropTypes.string.isRequired,
    type: PropTypes.string.isRequired,
    src: PropTypes.string.isRequired,
    height: PropTypes.string.isRequired,
    width: PropTypes.string.isRequired,
};

export default LoadPdf;
