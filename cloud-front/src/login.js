import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

export default function Login(props) {

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [loggedInFailed, setLoggedInFailed] = useState(false);
    const navigate = useNavigate();
    
    async function handleSubmit(event) {
        event.preventDefault();
    
        if (await authorizeLogin(username, password)) {
            setIsLoggedIn(true)
            setLoggedInFailed(false);
        }
    
        setUsername("");
        setPassword("");
    }

    function usernameOnChange(event) {
        event.preventDefault();
        setUsername(event.target.value);
        return username;
    }

    function passwordOnChanage(event) {
        event.preventDefault();
        setPassword(event.target.value);
        return password;
    }

    useEffect(() => {
        if (isLoggedIn) {
            // navigate("/");
        }
    })

    return (
        <div>
            { loggedInFailed &&
                <div>Username or Password is incorrect</div>
            }
            <h1>Login</h1>
            <form onSubmit={handleSubmit}>
                <label>
                    Username
                    <input type='text' value={username} onChange={usernameOnChange} required></input>
                </label>
                <label>
                    Password
                    <input type='password' value={password} onChange={passwordOnChanage} required></input>
                </label>
                <button type='submit'>Login</button>
            </form>
            <a href='/register'>
                <button>Register</button>
            </a>
        </div>
    );
}

async function authorizeLogin(username, password) {
    let request = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            username: username,
            password: password
        })
    };

    let response = await fetch("https://0py5xk0wp1.execute-api.us-east-1.amazonaws.com/cc-assignment1/login", request);

    if (response.status == 200) {
        let body = await response.json();
        console.log(body);
        return true;
    }

    return false;
}