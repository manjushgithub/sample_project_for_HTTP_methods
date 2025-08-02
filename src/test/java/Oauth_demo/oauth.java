package Oauth_demo;


import POJO.getcourses;
import POJO.webAutomation;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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


       getcourses res= given().queryParam("access_token",token).when().log().all().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
               .as(getcourses.class);
       String val=res.getLinkedIn();
       System.out.println(val);

       //scenario 2" how to find out the price of any course

//       List<Api> cour= res.getCourses().getApi();
//       for(int i=0;i<cour.size();i++)
//       {
//           if(cour.get(i).getCourseTitle().equals("Rest Assured Automation using Java"))
//           {
//               System.out.println(cour.get(i).getPrice());
//
//           }
//       }
       //all course title in webautomation
        String[] coursetitle={"Selenium Webdriver Javac","Cypress","Protractor"};
        List<String> a=new ArrayList<>();

      List<webAutomation> webcourse= res.getCourses().getWebAutomation();

       for(int i=0;i<webcourse.size();i++)
       {
           a.add(webcourse.get(i).getCourseTitle());
       }
       List<String> expectedvalues= Arrays.asList(coursetitle);
        Assert.assertTrue(a.equals(expectedvalues));
    }


}
