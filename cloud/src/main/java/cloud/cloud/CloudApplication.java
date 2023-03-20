package cloud.cloud;


import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cloud.cloud.dao.DynamoDB;
import cloud.cloud.model.Music;

@SpringBootApplication
public class CloudApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(CloudApplication.class, args);
	}

	public static List<Music> loadMusicData(String path) {
		List<Music> songList = new ArrayList<Music>();

		try {
            FileReader reader = new FileReader(path);
            Gson gson = new Gson();
            JsonObject jsonObj = gson.fromJson(reader, JsonObject.class);
			System.out.println(jsonObj.get("songs").getClass());
            JsonElement songs = jsonObj.get("songs");

			for (JsonElement song : songs.getAsJsonArray()) {
				JsonObject songObj = song.getAsJsonObject();
				songList.add(new Music(songObj.get("title").getAsString(), songObj.get("artist").getAsString(), songObj.get("year").getAsInt(), songObj.get("web_url").getAsString(), songObj.get("img_url").getAsString()));
			}
			
        } catch (Exception e) {
            e.printStackTrace();
        }

		return songList;
	}

	public static void createMusicTable() {
		DynamoDB<Music> musicClient = new DynamoDB<Music>("music", Music.class);
		musicClient.createTable();
	}

	public static void addMusicData() {
		DynamoDB<Music> musicClient = new DynamoDB<Music>("music", Music.class);
		List<Music> songs = loadMusicData("/Users/jainamdoshi/Library/CloudStorage/OneDrive-RMITUniversity/University/Australia/RMIT/Course Work/Semester 5/Cloud Computing/Assignments/cloud/src/main/resources/static/a1.json");
		musicClient.addItems(songs);
	}

}
