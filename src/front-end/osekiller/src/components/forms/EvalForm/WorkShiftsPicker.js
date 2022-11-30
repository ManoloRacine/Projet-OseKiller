import TimePicker from "react-time-picker";

const WorkShiftsPicker = ({ setFormData, formData, position }) => {
    return (
        <div className="row">
            <div className="col-3">
                <TimePicker
                    onChange={(value) => {
                        let updatedworkShifts = [...formData.workShifts];
                        updatedworkShifts[position][0] = value;
                        setFormData({
                            ...formData,
                            workShifts: updatedworkShifts,
                        });
                    }}
                    value={formData.workShifts[position][0]}
                />
            </div>
            <div className="col-3">
                <TimePicker
                    className={"text-white"}
                    onChange={(value) => {
                        let updatedworkShifts = [...formData.workShifts];
                        updatedworkShifts[position][1] = value;
                        setFormData({
                            ...formData,
                            workShifts: updatedworkShifts,
                        });
                    }}
                    value={formData.workShifts[position][1]}
                />
            </div>
        </div>
    );
};

export default WorkShiftsPicker;
