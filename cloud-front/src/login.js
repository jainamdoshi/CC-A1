import { useState } from 'react';

export default function Login() {

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    
    function handleSubmit(event) {
        event.preventDefault();
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

    return (
        <div>
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