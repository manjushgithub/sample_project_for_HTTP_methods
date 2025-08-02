package files;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DynamicJson {

    @Test(dataProvider = "getBookdataprovider")
    public void AddBook(String isbn,String aisle)
    {
        RestAssured.baseURI="http://216.10.245.166";

        String res=given().log().all().header("content-type","application/json").body(payload.addbook(isbn, aisle))
                .when().post("/Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath path=Reusable.rawtojson(res);
        String id=path.get("ID");
        System.out.println(id);
    }

    @DataProvider(name="getBookdataprovider")
    public Object[][] getbookdata()
    {
       return  new Object[][]{{"asfd","2545"},{"aserf","5689"},{"asty","5897"}};
    }

}
