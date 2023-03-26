import React, { useEffect, useState } from 'react'
import Music from './music'
import _ from 'lodash'
import { useLocation } from 'react-router-dom';

export default function Subscription(props) {

    const [subscriptions, setSubscriptions] = useState([]);
    const [isDataFetched, setIsDataFetched] = useState(false);

    async function handleSubmit(event, title) {
        event.preventDefault();

        let request = {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                email: props.email,
                title: title,
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
            .then((data) => console.log(data))
    }

    useEffect(() => {
        if (!isDataFetched) {
            fetchSubsciptions(props.email);
        }
        setIsDataFetched(true);
    })

    return (
        <div>
            <h3>Subscription Area</h3>
            {
                subscriptions.map((sub, index) => <div key={index}>
                        <Music  email={props.email} data={sub} />
                        <form onSubmit={e => handleSubmit(e, sub.title)}>
                            <button type="submit">Remove</button>
                        </form>
                    </div>
                )
            }
        </div>
    )
}
