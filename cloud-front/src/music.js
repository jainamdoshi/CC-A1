import './music.css'

export default function Music(props) {

    return (
        <div className='songs'>
            {
                props.songs.map(song => (
                    <div key={song.title + song.artist} className='music-card'>
                        <div className='song-content'>
                            <img src={`https://cc-assignment1.s3.amazonaws.com/${song.title.replace('#', '%23').replace(' ', '+')}+-+${song.artist.replace(' ', '+')}.jpg`} />
                            <div>
                                <p>Title: {song.title}</p>
                                <p>Artist: {song.artist}</p>
                                <p>Year: {song.year}</p>
                                {
                                    props.remove && <form onSubmit={e => props.remove(e, song.title, song.artist)}>
                                        <button className='btn btn-primary btn-block' type="submit">Remove</button>
                                    </form>
                                }
                                {
                                    props.add && <form onSubmit={e => props.add(e, song.title, song.artist)}>
                                        <button className='btn btn-primary btn-block' type="submit">Subscribe</button>
                                    </form>
                                }
                            </div>
                        </div>
                    </div>
                ))
            }
        </div>
    )
}
