import { useState } from 'react'

export default function Signup(props) {

    const [email, setEmail] = useState("");
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");


    function handleOnSubmit(event) {
        event.preventDefault();
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

    return (
        <div>
            <h1>Register</h1>
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
