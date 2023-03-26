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
public class Signup implements RequestStreamHandler {

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

            User newUser = new User((String) requestBody.get("email"), (String) requestBody.get("username"), (String) requestBody.get("password"), null);
            User existingUser = users.getItem(newUser);
            

            if (existingUser == null) {
                users.addItem(newUser);
                System.out.println("New user added");
                response.put("statusCode", 201);
                responseBody.put("username", newUser.getUsername());
            } else {
                System.out.println("User already exists");
                response.put("statusCode", 406);
                responseBody.put("message", "User already exists");
            }

            response.put("body", responseBody.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        

        System.out.println(response);
        Util.writeJSONInStream(output, response);
    }
    
}
