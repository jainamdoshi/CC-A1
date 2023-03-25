import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom';

export default function Signup(props) {

    const [email, setEmail] = useState("");
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [newUserFailed, setNewUserFailed] = useState(false);
    const [newUserSuccess, setNewUserSuccess] = useState(false);
    const navigate = useNavigate();


    async function handleOnSubmit(event) {
        event.preventDefault();

        if (await createNewUser(email, username, password)) {
            setNewUserSuccess(true);
            setNewUserFailed(false);
        } else {
            setNewUserFailed(true);
        }
    }

    async function createNewUser(email, username, password) {
        let request = {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                email: email,
                username: username,
                password: password
            })
        };

        console.log(request);
        let response = await fetch('https://0py5xk0wp1.execute-api.us-east-1.amazonaws.com/cc-assignment1/register', request);
        if (response.status == 201) {
            return true;
        }
        return false;
    }

    function emailOnChange(event) {
        event.preventDefault();
        setEmail(event.target.value);
    }

    function usernameOnChange(event) {
        event.preventDefault();
        setUsername(event.target.value);
    }

    function passwordOnChange(event) {
        event.preventDefault();
        setPassword(event.target.value);
    }
    
    useEffect(() => {
        if (newUserSuccess) {
            navigate("/login");
        }
    })

    return (
        <div>
            <h1>Register</h1>
            {
                newUserFailed &&
                <div>
                    The email already exists
                </div>
            }
            <form onSubmit={handleOnSubmit}>
                <label>
                    Email: 
                    <input type='email' onChange={emailOnChange} placeholder='john@doe.com' required></input>
                </label>
                <label>
                    Username:
                    <input type='text' onChange={usernameOnChange} placeholder='johndoe' required></input>
                </label>
                <label>
                    Password:
                    <input type='password' onChange={passwordOnChange} required></input>
                </label>
                <button type='submit'>Register</button>
            </form>
            <a href='/login'>
                <button>Login</button>
            </a>
        </div>
    )
}
