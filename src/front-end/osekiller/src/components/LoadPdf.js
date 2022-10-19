import PropTypes from "prop-types";

const LoadPdf = ({ title, type, src, height, width }) => {
    return (
        <iframe
            title={title}
            type={type}
            src={src}
            height={height}
            width={width}
            data-testid={"pdf"}
        ></iframe>
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
