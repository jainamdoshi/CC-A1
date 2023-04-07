import { createContext, useContext, useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import Subscription from './subsription';
import './home.css'

export default function Home(props) {

    const { state } = useLocation();
    const navigate = useNavigate();


    function handleLogout(event) {
        event.preventDefault();
        state.username = null;
        navigate("/login")
    }

    useEffect(() => {
        if (!state || !state.username) {
            navigate("/login");
        }
    })


    return (
        <div>
            <div className='username'>
                {state && <p> Welcome {state.username} </p> }
                <form onSubmit={handleLogout}>
                    <button className='btn btn-primary btn-block' type='submit'>Logout</button>
                </form>
            </div>
            
            {state && <Subscription email={state.email}/>}
        </div>
    )
}
