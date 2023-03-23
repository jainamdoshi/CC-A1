package cloud.cloud.lambda;

import org.json.JSONException;
import org.json.JSONObject;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;

public class Login implements RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {

    @Override
    public APIGatewayV2HTTPResponse handleRequest(APIGatewayV2HTTPEvent event, Context context) {

        APIGatewayV2HTTPResponse response = new APIGatewayV2HTTPResponse();
        event.getBody();
        System.out.println("test debug");
        LambdaLogger logger = context.getLogger();
        logger.log("log message");
        logger.log(event.getBody());
        if (event.getHeaders() != null) {
            logger.log(Integer.toString(event.getHeaders().size()));
        }
        response.setBody(event.getBody());
        return response;
    }
    
}
