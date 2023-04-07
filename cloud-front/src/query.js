import React, { useEffect, useState } from 'react'
import Music from './music';

export default function Query(props) {

    const [title, setTitle] = useState("")
    const [artist, setArtist] = useState("")
    const [year, setYear] = useState("")
    const [isQueried, setIsQueried] = useState(false);
    const [songs, setSongs] = useState([]);

    function titleOnChange(event) {
        event.preventDefault();
        setTitle(event.target.value);
    }

    function artistOnChange(event) {
        event.preventDefault();
        setArtist(event.target.value);
    }

    function yearOnChange(event) {
        event.preventDefault();
        setYear(event.target.value);
    }

    async function handleSubmit(event) {
        event.preventDefault();
        let request = {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            },
        };
        
        let query = `?title=${title}&artist=${artist}&year=${year}`
        console.log(request, query)
        let response = await fetch('https://0py5xk0wp1.execute-api.us-east-1.amazonaws.com/cc-assignment1/music' + query, request)
        setIsQueried(true);
        console.log(response);
        let body = await response.json();
        console.log(body);
        setSongs(body.songs);
    }

    async function handleSubSubmit(event, title, artist) {
        event.preventDefault();

        let request = {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                email: props.email,
                title: title,
                artist: artist,
                message: "subscribe"
            })
        };
    
        console.log(request)
        let response = await fetch("https://0py5xk0wp1.execute-api.us-east-1.amazonaws.com/cc-assignment1/subscription", request);
        console.log(response);
        
        if (response.status == 200) {
            // setSubscriptions(_.reject(subscriptions, sub => sub.title == title));
            let body = await response.json();
            console.log(body);
            props.updateSub();
        }

    }


    return (
        <div className='mb-5'>
            <h3>Query Area</h3>
            {
                songs.length == 0 ? (isQueried ? <div className='alert alert-warning'>No result is retrieved. Please query again</div> : <div></div>) : <div></div>
            }
            <form className='container-fluid flex' onSubmit={handleSubmit}>
                <label className='form-group'>
                    Title:
                    <input className='form-control' type="text" value={title} onChange={titleOnChange}></input>
                </label>
                <label className='form-group'>
                    Artist:
                    <input className='form-control' type="text" value={artist} onChange={artistOnChange}></input>
                </label>
                <label className='form-group'>
                    Year:
                    <input className='form-control' type="number" value={year} onChange={yearOnChange}></input>
                </label>
                <button className='btn btn-primary btn-block' type="submit">Query</button>
            </form>
            <Music  songs={songs} email={props.email} add={handleSubSubmit}/>
        </div>
    )
}
