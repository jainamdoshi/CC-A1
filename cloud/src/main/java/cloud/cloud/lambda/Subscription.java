package cloud.cloud.lambda;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import cloud.cloud.Util;
import cloud.cloud.dao.DynamoDB;
import cloud.cloud.model.Music;
import cloud.cloud.model.User;

@SuppressWarnings("unchecked")
public class Subscription implements RequestStreamHandler {

    private DynamoDB<User> users;

    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
        JSONObject response = new JSONObject();
        JSONObject header = Util.getResponseHeader();
        JSONObject responseBody = new JSONObject();
        JSONParser parser = new JSONParser();

        response.put("headers", header);
        users = new DynamoDB<>("login", User.class);

        try {
            JSONObject request = Util.getJSONObject(input);
            String methodType = (String) request.get("httpMethod");
            
            if (methodType.equals("GET")) {
                String email = (String) ((JSONObject) request.get("queryStringParameters")).get("email");
                responseBody.put("subscriptions", this.getSubscriptions(email).toString());

            } else if (methodType.equals("PATCH")) {
                JSONObject requestBody = (JSONObject) parser.parse((String) request.get("body"));
                User user = users.getItem(new User((String) requestBody.get("email")));
                System.out.println(user);
                String message = (String) requestBody.get("message");
                String title = (String) requestBody.get("title");
                if (message.equals("unsubscribe")) {
                    this.unsubscribe(user, title);
                    response.put("statusCode", 200);
                    responseBody.put("message", "Unsubscribed");
                } else if (message.equals("subscribe")) {
                    this.subscribe(user, title);
                    response.put("statusCode", 200);
                    responseBody.put("message", "Subscribed");
                }
            }

            response.put("body", responseBody.toString());
        } catch (Exception e) {
                response.put("statusCode", 304);
                responseBody.put("message", "Error");
            e.printStackTrace();
        }

        System.out.println(response);
        Util.writeJSONInStream(output, response);
    }

    private void subscribe(User user, String title) {
        DynamoDB<Music> songs = new DynamoDB<>("music", Music.class);
        Music song = songs.getItem(new Music(title));
        user.subscribe(song);
        users.addItem(user);
    }

    private void unsubscribe(User user, String title) {
        user.unscubscribe(title);
        users.addItem(user);
        System.out.println("Unsubscribed");
    }

    private JSONArray getSubscriptions(String email) {
        User user = users.getItem(new User(email));
        return user.getSubscriptionJson();
    }
    
}
