import { useEffect, useState } from "react";
import UserValidationCard from "../../components/UserValidationCard";
import { fetchUsers } from "../../services/UserService";

export const ValidateUsers = () => {
    const [users, setUsers] = useState([]);

    useEffect(() => {
        fetchUsers()
            .then((response) => {
                setUsers(response.data);
            })
            .catch((error) => {
                console.error(error);
            });
    }, []);

    const removeUserFromValidationList = (userId) => {
        setUsers(users.filter((user) => user.id !== userId));
    };

    return (
        <div>
            {users.map(
                (user, key) =>
                    !user.enabled && (
                        <div className="my-1" key={key}>
                            <UserValidationCard
                                key={key}
                                user={user}
                                removeUser={removeUserFromValidationList}
                            />
                        </div>
                    )
            )}
        </div>
    );
};

export default ValidateUsers;
