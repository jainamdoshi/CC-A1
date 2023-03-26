import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

export default function Login(props) {

    const [email, setemail] = useState("");
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [subscriptions, setSubscriptions] = useState("");
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [loggedInFailed, setLoggedInFailed] = useState(false);
    const navigate = useNavigate();
    
    async function handleSubmit(event) {
        event.preventDefault();
    
        if (await authorizeLogin(email, password)) {
            setIsLoggedIn(true);
            setLoggedInFailed(false);
        } else {
            setLoggedInFailed(true);
        }
    
    }

    function emailOnChange(event) {
        event.preventDefault();
        setemail(event.target.value);
    }

    function passwordOnChanage(event) {
        event.preventDefault();
        setPassword(event.target.value);
    }

    async function authorizeLogin(email, password) {
        let request = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                email: email,
                password: password
            })
        };
    
        console.log(request)
        let response = await fetch("https://0py5xk0wp1.execute-api.us-east-1.amazonaws.com/cc-assignment1/login", request);
    
        console.log(response);
        if (response.status == 200) {
            let body = await response.json();
            setUsername(body.username);
            setSubscriptions(body.subscriptions);
            return true;
        }
    
        return false;
    }

    useEffect(() => {
        if (isLoggedIn) {
            navigate("/", {state: {username: username, email: email, subscriptions: subscriptions}});
        }
    })

    return (
        <div>
            { loggedInFailed &&
                <div>Email or Password is incorrect</div>
            }
            <h1>Login</h1>
            <form onSubmit={handleSubmit}>
                <label>
                    Email
                    <input type='email' value={email} onChange={emailOnChange} placeholder="john@doe.com" required></input>
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