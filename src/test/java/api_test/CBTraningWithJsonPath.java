package api_test;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import dbutils.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CBTraningWithJsonPath {

    @BeforeClass
    public void beforeClass(){
        baseURI= ConfigurationReader.get("cbt_api_url");
    }

    @Test
    public void test1(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 17982)
                .when().get("/students/{id}");
        JsonPath jsonPath=response.jsonPath();
        String firstName=jsonPath.getString("students.firstName[0]");
        System.out.println("firstName = " + firstName);

        String lastName=jsonPath.getString("students.lastName[0]");
        assertEquals(lastName,"Mike");

        assertEquals(jsonPath.getString("students.country[0].address.city"),"Chicago");

        String zipcode=jsonPath.getString("students.company[0].address.zipCode");
        assertEquals(zipcode,66000);


        



    }


}
