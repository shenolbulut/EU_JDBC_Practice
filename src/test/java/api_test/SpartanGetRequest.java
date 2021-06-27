package api_test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
public class SpartanGetRequest {

    String spartanUrl="http://3.80.189.73:8000";
    @Test
    public void test1(){
        Response response=get(spartanUrl+"/api/spartans");
    }

    /* TASK
        When users sends a get request to /api/spartans/3
        Then status code should be 200
        And content type should be application/json;charset=UTF-8
        and json body should contain Fidole
     */

    @Test
    public void test2(){
        Response response = when().get(spartanUrl + "/api/spartans/3");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"aplication/json");
        //verify Fidole

        assertTrue(response.body().asString().contains("Fidole"));
    }

     /*
        Given no headers provided
        When Users sends GET request to /api/hello
        Then response status code should be 200
        And Content type header should be “text/plain;charset=UTF-8”
        And header should contain date
        And Content-Length should be 17
        And body should be “Hello from Sparta"
        */

    @Test
    public void test3(){
        Response response = when().get(spartanUrl + "/api/hello");
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"text/plain;charset=UTF-8");

        assertTrue(response.headers().hasHeaderWithName("Date"));//if date is there, return true

        System.out.println(response.header("Content-Length"));//output is 17

        assertEquals(response.header("Content-Length"),17);

        //verify Hello from Sparta
        assertTrue(response.getBody().asString().contains("Hello from Sparta"));


    }
}
