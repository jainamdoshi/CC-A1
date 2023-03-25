import { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

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
        </div>
    )
}
