package api_test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class SimpleApiTest {
    String hrUrl="http://api.cybertektraining.com/swagger-ui.html#/user-controller/retrieveAllStudentsUsingGET";

    @Test
    public void test1(){
        Response response = RestAssured.get(hrUrl);

        System.out.println(response.statusCode());  // out put is 200

        response.prettyPrint();//pring as the same as json file of api wep page
    }

     /*
        Given accept type is json
        When user sends get request to regions endpoint
        Then response status code must be 200
        and body is json format
     */
    @Test
    public void test2(){
        Response response= given().accept(ContentType.JSON)
                        .when().get(hrUrl);

        Assert.assertEquals(response.getStatusCode(), 200);

        Assert.assertEquals(response.contentType(), "application/json");
    }

    @Test
    public void test3(){
        given().accept(ContentType.JSON).when()
                .get(hrUrl).then().assertThat()
                .statusCode(200).and().contentType("application/json");
    }
    @Test
    public void test4(){
        Response response=given().accept(ContentType.JSON).when()
                .get(hrUrl+"/2");
        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(), "application/json");

        Assert.assertTrue(response.body().asString().contains("Americans"));

    }


}
