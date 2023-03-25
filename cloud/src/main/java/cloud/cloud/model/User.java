package cloud.cloud.model;


import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;


@DynamoDbBean
public class User {
    
    private String email;
    private String username;
    private String password;

    public User() {
        
    }

    public User(String email) {
        this.email = email;
    }
    
    public User(String email, String username, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
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
}

