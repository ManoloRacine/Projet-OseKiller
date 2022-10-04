import { validateUser } from "../services/UserService";

export const UserValidationCard = ({user, removeUser}) => {
    return <div  className="card" style={{width: "18rem"}}>
        <div className="card-body">
            <h5 className="card-title">{user.name}</h5>
            <p className="card-text">{user.email}</p>
            <button className="btn btn-primary me-2" onClick={() => {
                validateUser(user.id, true)
                removeUser(user.id)
            }}>
                Accepter
            </button>
            <button className="btn btn-danger" onClick={() => {
                validateUser(user.id, false)
                removeUser(user.id)
            }}>
                Refuser
            </button>
        </div>  
    </div>
}

export default UserValidationCard;