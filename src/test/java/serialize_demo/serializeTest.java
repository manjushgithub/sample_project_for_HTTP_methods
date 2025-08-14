package serialize_demo;

import io.restassured.RestAssured;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;


public class serializeTest {

    public static void main(String[] args) {

        RestAssured.baseURI="https://rahulshettyacademy.com";
        addplace p=new addplace();
        p.setAccuracy(50);
        p.setAddress("29, side layout, cohen 09");
        p.setLanguage("French-IN");
        p.setName("Frontline house");
        p.setPhone_number("(+91) 983 893 3937");
        List<String> mylist=new ArrayList<>();
        mylist.add("shoe park");
        mylist.add("shop");
        p.setTypes(mylist);
        Location l=new Location();
        l.setLat(-38.383494);
        l.setLng(33.427362);
        p.setLocation(l);

       String res= given().queryParam("key ","qaclick123").body(p)
                .when().post("/maps/api/place/add/json").then().assertThat().statusCode(200).extract().asString();

       System.out.println(res);
    }
}
