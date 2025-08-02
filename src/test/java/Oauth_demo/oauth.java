package Oauth_demo;


import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;

public class oauth {
    public static void main(String[] args)
    {
       String responce= given().formParam("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParam("client_secret","erZOWM9g3UtwNRj340YYaK_W")
                .formParam("grant_type","client_credentials")
                .formParam("scope","trust")
                .when().log().all().post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();
       //System.out.print(responce);

        JsonPath path=new JsonPath(responce);
        String token=path.get("access_token");


       String res= given().queryParam("access_token",token).when().log().all().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
                .then().assertThat().statusCode(401).extract().asString();
       System.out.println(res);
    }

}
