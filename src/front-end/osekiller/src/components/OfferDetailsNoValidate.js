import PropTypes from "prop-types";
import LoadPdf from "./LoadPdf";
const OfferDetailsNoValidate = ({ pdf }) => {
    return (
        <>
            <div className="row">
                <LoadPdf
                    src={pdf}
                    width={"50%"}
                    title={"student-cv"}
                    type={"application/pdf"}
                    height={"500px"}
                />
            </div>
        </>
    );
};

OfferDetailsNoValidate.propTypes = {
    pdf: PropTypes.string.isRequired,
};

export default OfferDetailsNoValidate;
