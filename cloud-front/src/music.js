import React, { useState } from 'react'

export default function Music(props) {

    return (
        <div>
            <div>
                <p>Title: {props.data.title}</p>
                <p>Artist: {props.data.artist}</p>
                <p>Year: {props.data.year}</p>
                <img src={`https://cc-assignment1.s3.amazonaws.com/${props.data.title.replace(' ', '+')}+-+${props.data.artist.replace(' ', '+')}.jpg`} />
            </div>

        </div>
    )
}
