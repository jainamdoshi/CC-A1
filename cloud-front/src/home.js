
export default function Home() {

    function handleLogout(event) {
        event.preventDefault();
    }

    return (
        <div>
            <form onSubmit={handleLogout}>
                <button type='submit'>Logout</button>
            </form>
        </div>
    )
}
