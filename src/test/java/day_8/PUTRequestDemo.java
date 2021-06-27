package day_8;

import dbutils.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class PUTRequestDemo {

    @BeforeClass
    public void before(){
        baseURI= "http://34.228.41.120:8000";
    }

    @Test
    public void test1(){
            //Create one map for the put request json body
            Map<String,Object> putRequestMap = new HashMap<>();
            putRequestMap.put("name","senol");
            putRequestMap.put("gender","Male");
            putRequestMap.put("phone",1231312321l);

            given().log().all()
                    .and()
                    .contentType(ContentType.JSON)
                    .and()
                    .pathParam("id",379)
                    .and()
                    .body(putRequestMap).
                    when()
                    .put("/api/spartans/{id}")
                    .then().log().all()
                    .assertThat().statusCode(204);

            //verify the updated spartan
        Response response=given().accept(ContentType.JSON)
                        .and()
                        .pathParam("id",379)
                        .when()
                        .get("api/spartans/{id}");

        putRequestMap.put("id",379);

        Assert.assertEquals(putRequestMap,response.as(Map.class));

        }
    @Test
    public void test2(){
        Map<String, Object> patchRequestMap=new HashMap<>();
        patchRequestMap.put("phone",34534532);

        given().accept(ContentType.JSON)
                .and()
                .pathParam("id","379")
                .and()
                .body(patchRequestMap)
                .when()
                .patch("/api/spartans{id}")
                .then()
                .statusCode(204)
                .body("phone",equalTo(34534532  ));


    }
}
