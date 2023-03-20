package cloud.cloud;


import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
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
		downloadAndUploadAllArtistsImages();
	}

	public static void downloadAndUploadAllArtistsImages() {
		DynamoDB<Music> musicClient = new DynamoDB<Music>("music", Music.class);
		List<Music> songs = musicClient.getAllItems();
		for (Music song : songs) {
			String image_url = song.getimage_url();
			String artist = song.getArtist();

			try {
				URL url = new URL(image_url);
				InputStream inputStream = url.openConnection().getInputStream();
				OutputStream outputStream = new FileOutputStream(String.format("/Users/jainamdoshi/Library/CloudStorage/OneDrive-RMITUniversity/University/Australia/RMIT/Course Work/Semester 5/Cloud Computing/Assignments/CC-A1/cloud/src/main/resources/static/Artists Images/%s.jpg", artist));

				byte[] buffer = new byte[4096];
                int read = -1;
				while ((read = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, read);
                }

				inputStream.close();
				outputStream.close();

			} catch (Exception error) {
				error.printStackTrace();
			}
		}

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
			
        } catch (Exception error) {
            error.printStackTrace();
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
