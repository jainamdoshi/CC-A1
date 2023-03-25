package cloud.cloud.lambda;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import cloud.cloud.Util;
import cloud.cloud.dao.DynamoDB;
import cloud.cloud.model.User;

@SuppressWarnings("unchecked")
public class Login implements RequestStreamHandler {
            
    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
        LambdaLogger logger = context.getLogger();
        JSONObject response = new JSONObject();
        JSONObject header = Util.getResponseHeader();
        
        
        DynamoDB<User> users = new DynamoDB<>("login", User.class);
        
        try {
            response.put("headers", header);
            response.put("statusCode", 200);

            JSONObject request = Util.getJSONObject(input);
            JSONParser parser = new JSONParser();
            JSONObject requestBody = (JSONObject) parser.parse((String) request.get("body"));
            JSONObject responseBody = new JSONObject();


            response.put("body", responseBody.toString());
        } catch (Exception e) { 
            e.printStackTrace();
        }
        
        
        logger.log(response.toString());
        Util.writeJSONInStream(output, response);
    }
    
}
