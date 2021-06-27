package api_test;

import dbutils.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
import static org.hamcrest.Matchers.*;

public class HomeworkSpartan {

    @BeforeClass
    public void before(){
        baseURI= ConfigurationReader.get("sparta_api_url");
    }

//    Given accept type is json
//    And path param id is 20
//    When user sends a get request to "/spartans/{id}"
//    Then status code is 200
//    And content-type is "application/json;char"
//    And response header contains Date
//    And Transfer-Encoding is chunked
//    And response payload values match the following:
//    id is 20,
//    name is "Lothario",
//    gender is "Male",
//    phone is 7551551687

    @Test
    public void test1(){
        Response response = given().accept(ContentType.JSON).and().pathParam("id", 20)
                .when().get("/spartans/{id}");
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"aplication/json;char");
        assertNotNull(response.header("Date"));
        assertEquals(response.header("Transfer-Encoding"),"chunked");

        JsonPath jsonPath = response.jsonPath();

        assertEquals(jsonPath.getInt("id"),20);
        assertEquals(jsonPath.getString("name"),"Lothario");
        assertEquals(jsonPath.getString("gender" ), "Male"  );
        assertEquals(jsonPath.getInt("phone"),"7551551687");


    }
    @Test
    public void hamcrestTest(){
        given().accept(ContentType.JSON).and().pathParam("id",20)
                .when().get("/spartans/{id}")
                .then().statusCode(200)
                .and().assertThat().header("Date", notNullValue())
                .and().header("Transfer-Encoding","chunked")
                .and().contentType("aplication/json;char")
                .and().body("id",equalTo(20),
                "name",equalTo("Lothario"),"gender",equalTo("Male"),
                "phone",equalTo("7551551687 "));

    }

//    Given accept type is json
//    And query param gender = Female
//    And queary param nameContains = r
//    When user sends a get request to "/spartans/search"
//    Then status code is 200
//    And content-type is "application/json;char"
//    And all genders are Female
//    And all names contains r
//    And size is 20
//    And totalPages is 1
//    And sorted is false
    @Test
    public void test2(){
        Response response = given().accept(ContentType.JSON).and().queryParam("gender", "Female")
                .and().queryParam("nameContains", "r")
                .when().get("/spartans/search");
        JsonPath jsonPath = response.jsonPath();

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"aplication/json;char");

        List<String> genders=jsonPath.getList("gender");
        for(String gender:genders){
            assertEquals(gender,"Female");
        }
        List<String> names=jsonPath.getList("name");
        for(String name:names){
            assertTrue(name.contains("r"));
        }



    }
}
