package ecommerseApi;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class ecommerseapitest {
    public static void main(String[] args) {
        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();
        LoginObject loginobject = new LoginObject();
        loginobject.setUserEmail("manjushborse123@gmail.com");
        loginobject.setUserPassword("Manjush@2016");
        RequestSpecification reqgiven = given().log().all().spec(req).body(loginobject);
        LoginResponse loginResponse = reqgiven.when().post("/api/ecom/auth/login").then().extract().as(LoginResponse.class);
        String token = loginResponse.getToken();
        String id = loginResponse.getUserId();
        System.out.println(token);

        //noe to create a new product for that create request post request
        RequestSpecification createproductspec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").
                addHeader("authorization", token).build();

        RequestSpecification addpr = given().spec(createproductspec).param("productName", "test").param("productAddedBy", id)
                .param("productCategory", "fashion")
                .param("productSubCategory", "shirtse")
                .param("productPrice", "11700")
                .param("productDescription", "dell Originals").param("productFor", "women")
                .multiPart("productImage", new File("C:\\Users\\DELL\\Downloads\\test-upload.png"));

        String addproductres = addpr.when().post("/api/ecom/product/add-product").then().extract().response().asString();
        JsonPath path = new JsonPath(addproductres);
        String productid = path.getString("productId");
        System.out.println(productid);

        //create order for added product above
        RequestSpecification addproductbase = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("authorization", token).setContentType(ContentType.JSON).build();

        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setCountry("IN");
        orderDetails.setProductOrderedId(productid);
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        orderDetailsList.add(orderDetails);

        Order order = new Order();
        order.setOrders(orderDetailsList);

        RequestSpecification createorder=given().spec(addproductbase).body(order);
        String responceorder=createorder.when().log().all().post("/api/ecom/order/create-order")
                .then().extract().response().asString();

        System.out.println(responceorder);

        //delete the product of which just you have newly added and craeted the order for that

       RequestSpecification deleteproductrequestspec =new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("authorization", token).build();

        RequestSpecification deletreq= given().log().all().spec(deleteproductrequestspec).pathParam("productId",productid);
              String deletres= deletreq.when().delete("/api/ecom/product/delete-product/{productId}").then().log().all().extract().response().asString();

              JsonPath p=new JsonPath(deletres);
            String m=  p.get("message");
        Assert.assertEquals("Product Deleted Successfully",m);



    }
}
