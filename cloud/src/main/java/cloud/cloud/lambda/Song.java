package cloud.cloud.lambda;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import cloud.cloud.Util;
import cloud.cloud.dao.DynamoDB;
import cloud.cloud.model.Music;

@SuppressWarnings("unchecked")
public class Song implements RequestStreamHandler {

    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {

        JSONObject response = new JSONObject();
        JSONObject header = Util.getResponseHeader();
        JSONObject responseBody = new JSONObject();

        response.put("headers", header);

        DynamoDB<Music> music = new DynamoDB<Music>("music", Music.class);

        try {
            JSONObject request = Util.getJSONObject(input);

            JSONObject query = (JSONObject) request.get("queryStringParameters");
            String title = (String) query.get("title");
            String artist = (String) query.get("artist");
            String year = (String) query.get("year");

            List<Music> songs = music.getAllItems();
            if (!title.equals("")) {
                songs = this.filterByTitle(songs, title);
            }

            if (!artist.equals("")) {
                songs = this.filterByArtist(songs, artist);
            }
            
            if (!year.equals("")) {
                songs = this.filterByYear(songs, year);
            }
            

            JSONArray songJson = new JSONArray();
            System.out.println(songs.size());
            
            for (Music song : songs) {
                songJson.add(song.getJson());
            }
            
            responseBody.put("songs", songJson);
            // Music song = music.getItem(new Music("Watching the Wheels", "John Lennon"));
            // responseBody.put("songs", song.getJson());
            response.put("body", responseBody.toString());
        } catch (Exception e) {
            responseBody.put("message", "error");
            e.printStackTrace();
        }
        
        System.out.println(response);
        Util.writeJSONInStream(output, response);
    }

    private List<Music> filterByYear(List<Music> songs, String year) {
        List<Music> result = new ArrayList<Music>();
        int y = Integer.parseInt(year);
        for (Music song : songs) {
            if (song.getYear() == y) {
                result.add(song);
            }
        }

        return result;
    }

    private List<Music> filterByArtist(List<Music> songs, String artist) {
        List<Music> result = new ArrayList<Music>();
        for (Music song : songs) {
            if (song.getArtist().equals(artist)) {
                result.add(song);
            }
        }

        return result;
    }

    private List<Music> filterByTitle(List<Music> songs, String title) {
        List<Music> result = new ArrayList<Music>();
        for (Music song : songs) {
            if (song.getTitle().equals(title)) {
                result.add(song);
            }
        }

        return result;
    }

    
}
