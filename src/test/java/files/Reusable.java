package files;

import io.restassured.path.json.JsonPath;

public class Reusable {
    public static JsonPath rawtojson(String res)
    {

        JsonPath path=new JsonPath(res);
        return path;
    }

}
