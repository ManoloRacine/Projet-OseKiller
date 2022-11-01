import InputGroup from 'react-bootstrap/InputGroup';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faDeleteLeft } from '@fortawesome/free-solid-svg-icons';

const ContractTask = ({index, task, handleDeleteTask, handleChangeTask}) => {
    return(
        <InputGroup className="mb-3">
            <Form.Control
                aria-label="Tache ou responsabilité"
                aria-describedby="basic-addon1"
                value={task}
                onChange={(e) => handleChangeTask(index, e.target.value)}
            />
            <Button variant="danger" onClick={() => handleDeleteTask(index)}>
                <FontAwesomeIcon icon={faDeleteLeft} size="xl" />
            </Button>
        </InputGroup>  
    );
}
export default ContractTask;