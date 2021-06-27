package api_test;
import dbutils.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class hrApiWithPath {

    @BeforeClass
    public void before(){
        baseURI= "34.228.41.120:1000/ords/hr";
    }

    @Test
    public void test1(){
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"region_id\":2}")
                .when().get("/countries");

        assertEquals(response.statusCode(),200);

        //print limit value
        System.out.println("response.path(\"limit\") = " + response.path("limit"));

        System.out.println("response.path(\"hashMore\") = " + response.path("hashMore"));

        //get all countries

        List<String> countryName=response.path("items.country_name");

        //assert that all region ids equals to 2

        List<Integer> regionId=response.path("items.region_id");

        for(int id:regionId){
            assertEquals(id,2);
        }


    }

    @Test
    public void Test2(){
        Response response = given().accept(ContentType.JSON)
                .queryParam("q", "{\"job_id\":\"IT_PROG\"}")
                .when().get("/employees");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"aplication/json");

        //make sure we have only IT Programır
        List<String> jopıD=response.path("items.job_id");
        for(String id:jopıD){
            assertEquals(id,"IT_PROG");
        }
    }
}
