import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
public class basic {
    //save place id and then update the location or address and use get method to see daddress is update or not

    //content of the data in a file so in java first convert content of file in bytes and bytes convert into string
    public static void main(String[] args) throws IOException {
        RestAssured.baseURI="https://rahulshettyacademy.com";
        String res=given().log().all().queryParam("key", "qaclick123").header("content-type","application/json")
                .body(new String(Files.readAllBytes(Paths.get("C:\\Users\\DELL\\Downloads\\addplace.json")))).when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200).body("scope",equalTo("APP")).
                header("Server","Apache/2.4.52 (Ubuntu)").extract().response().asString();//this header as responce
        //as we need place id for that we are extracting from the response
        System.out.println(res);

       // now this is like json then how we can extract values like place id from here
        JsonPath js=new JsonPath(res);
        String placeId=js.getString("place_id");
        System.out.print(placeId);

        //update the address
        String resput = given().log().all().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "\"place_id\":\"" + placeId + "\",\n" +
                        "\"address\":\"70 Summer walk, USA\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}")
                .when().put("maps/api/place/update/json")
                .then().assertThat().statusCode(200)
                .body("msg", equalTo("Address successfully updated")) // small case "successfully"
                .extract().response().asString();

        //validate that address is successfully updated or not

        String resp=given().queryParam("key","qaclick123").queryParam("place_id",placeId).
                when().get("maps/api/place/get/json").
               // .then().assertThat().body("address",equalTo("70 Summer walk, USA"));
        then().assertThat().statusCode(200).extract().response().asString();

        JsonPath jsp=new JsonPath(resp);
        String newaddress=jsp.getString("address");
        System.out.println(newaddress);

        Assert.assertEquals(newaddress,"70 Summer walk, USA");
    }
}
