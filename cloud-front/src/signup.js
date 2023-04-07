import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom';
import './sigup.css';

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
        <div className='signup container-fluid flex'>
            <h1>Register</h1>
            {
                !newUserFailed &&
                <div className='alert alert-danger'>
                    The email already exists
                </div>
            }
            <form className='justify-content-center form' onSubmit={handleOnSubmit}>
                <div className='fields'>
                    <label className='form-group'>
                        Email: 
                        <input className='form-control' type='email' onChange={emailOnChange} required></input>
                    </label>
                    <label className='form-group'>
                        Username:
                        <input className='form-control' type='text' onChange={usernameOnChange} required></input>
                    </label>
                    <label className='form-group'> 
                        Password:
                        <input className='form-control' type='password' onChange={passwordOnChange} required></input>
                    </label>
                </div>
                <button className='btn btn-primary btn-block' type='submit'>Register</button>
            </form>
            <a href='/login'>
                <button className='btn btn-secondary btn-block'>Login</button>
            </a>
        </div>
    )
}
