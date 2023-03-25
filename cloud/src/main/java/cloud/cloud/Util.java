package cloud.cloud;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@SuppressWarnings("unchecked")
public class Util {
 
    public static JSONObject getJSONObject(InputStream stream) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(new InputStreamReader(stream, "UTF-8"));
    }

    public static void writeJSONInStream(OutputStream output, JSONObject response) throws UnsupportedEncodingException, IOException {
        OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
        writer.write(response.toString());
        writer.close();
    }

    public static JSONObject getResponseHeader() {
        JSONObject header = new JSONObject();
        header.put("Access-Control-Allow-Origin", "*");
        header.put("Access-Control-Allow-Methods", "OPTIONS,POST,GET");
        header.put("Access-Control-Allow-Headers", "Access-Control-Allows-Headers, Access-Control-Allow-Origin, Origin,Accept, X-Requested-With, contenttype, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
        return header;
    }
}
