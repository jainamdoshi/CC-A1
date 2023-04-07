package cloud.cloud.lambda;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import cloud.cloud.Util;
import cloud.cloud.dao.DynamoDB;
import cloud.cloud.model.User;

@SuppressWarnings("unchecked")
public class Login implements RequestStreamHandler {
            
    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
        JSONObject response = new JSONObject();
        JSONObject header = Util.getResponseHeader();
        response.put("headers", header);
        
        
        DynamoDB<User> users = new DynamoDB<>("login", User.class);
        
        try {
            
            JSONObject request = Util.getJSONObject(input);
            JSONParser parser = new JSONParser();
            JSONObject requestBody = (JSONObject) parser.parse((String) request.get("body"));
            JSONObject responseBody = new JSONObject();
            
            User tempUser = new User((String) requestBody.get("email"));
            User user = users.getItem(tempUser);

            if (user != null && user.getpassword().equals((String) requestBody.get("password"))) {
                System.out.println("Valid username and password");
                response.put("statusCode", 200);
                responseBody.put("username", (String) user.getUsername());

            } else {
                response.put("statusCode", 401);
                responseBody.put("message", "Invalid username or password");
                System.out.println("Invalid username or password");
            }
            
            response.put("body", responseBody.toString());
        } catch (Exception e) { 
            e.printStackTrace();
            response.put("statusCode", 200);
        }
        
        System.out.println(response);
        Util.writeJSONInStream(output, response);
    }


    
}
