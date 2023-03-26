import { createContext, useContext, useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import Subscription from './subsription';
import Query from './query';

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
            <div>
                Welcome User: {state && state.username}
            </div>
            <form onSubmit={handleLogout}>
                <button type='submit'>Logout</button>
            </form>
            {state && <Subscription email={state.email}/>}
        </div>
    )
}
