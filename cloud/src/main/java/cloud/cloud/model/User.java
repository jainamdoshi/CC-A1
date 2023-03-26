package cloud.cloud.model;


import java.util.List;

import org.json.simple.JSONArray;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;


@DynamoDbBean
public class User {
    
    private String email;
    private String username;
    private String password;
    private List<Music> subscriptions;

    public User() {
        
    }

    public User(String email) {
        this.email = email;
    }
    
    public User(String email, String username, String password, List<Music> subscriptions) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.subscriptions = subscriptions;
    }

    @DynamoDbPartitionKey
    public String getemail() {
        return this.email;
    }

    public void setemail(String email) {
        this.email = email;
    }

    @DynamoDbAttribute("user_name")
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @DynamoDbAttribute("password")
    public String getpassword() {
        return this.password;
    }

    public void setpassword(String password) {
        this.password = password;
    }

    @DynamoDbAttribute("subscriptions")
    public List<Music> getSubscriptions() {
        return this.subscriptions;
    }

    public JSONArray getSubscriptionJson() {
        JSONArray songs = new JSONArray();

        for (Music song : this.subscriptions) {
            songs.add(song.getJson());
        }

        return songs;
    }

    public void setSubscriptions(List<Music> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public void unscubscribe(String title) {
        int index = findMusic(title);
        if (index != -1) {
            this.subscriptions.remove(index);
        }
    }

    public void subscribe(Music song) {
        int index = findMusic(song.getTitle());
        if (index == -1) {
            this.subscriptions.add(song);
        }
    }

    private int findMusic(String title) {
        Boolean found = false;
        int index = -1;
        for (int i = 0; i < this.subscriptions.size() && !found; i++) {
            if (this.subscriptions.get(i).getTitle().equals(title)) {
                index = i;
                found = true;
            }
        }
        return index;
    }
}

