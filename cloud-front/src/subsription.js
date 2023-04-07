import React, { useContext, useEffect, useState } from 'react'
import Music from './music'
import _ from 'lodash'
import { useLocation } from 'react-router-dom';
import Query from './query';

export default function Subscription(props) {

    const [subscriptions, setSubscriptions] = useState([]);
    const [isDataFetched, setIsDataFetched] = useState(false);

    async function handleSubmit(event, title, artist) {
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
                message: "unsubscribe"
            })
        };
    
        console.log(request)
        let response = await fetch("https://0py5xk0wp1.execute-api.us-east-1.amazonaws.com/cc-assignment1/subscription", request);
        console.log(response);
        
        if (response.status == 200) {
            setSubscriptions(_.reject(subscriptions, sub => sub.title == title));
            let body = await response.json();
            console.log(body);
        }
        setIsDataFetched(false);
    }

    function fetchSubsciptions(email) {
        let request = {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            },
        };

        let query = `?email=${email}`
        console.log(request, query);
        fetch('https://0py5xk0wp1.execute-api.us-east-1.amazonaws.com/cc-assignment1/subscription' + query, request)
            .then(async (response) => {
                console.log(response); 
                let body = await response.json();
                console.log(body)
                setSubscriptions(JSON.parse(body.subscriptions));
            })
    }

    useEffect(() => {
        if (!isDataFetched) {
            fetchSubsciptions(props.email);
        }
        setIsDataFetched(true);
    })

    function updateSub() {
        setIsDataFetched(false);
    }

    return (
        <div className='subscribtion'>
            <h3>Subscription Area</h3>
            <Music  songs={subscriptions} email={props.email} remove={handleSubmit} />
            <Query email={props.email} updateSub={updateSub}/>
        </div>
    )
}
