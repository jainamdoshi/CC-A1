package cloud.cloud.model;


import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public class Music {
    
    private String title;
    private String artist;
    private int year;
    private String web_url;
    private String image_url;

    public Music() {
        
    }
    
    public Music(String title, String artist, int year, String web_url, String image_url) {
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.web_url = web_url;
        this.image_url = image_url;
    }

    @DynamoDbPartitionKey
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @DynamoDbSortKey
    public String getArtist() {
        return this.artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @DynamoDbAttribute("year")
    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    
    @DynamoDbAttribute("web_url")
    public String getWeb_url() {
        return this.web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    @DynamoDbAttribute("image_url")
    public String getimage_url() {
        return this.image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}

