package day_8;

import com.github.javafaker.Faker;
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

public class SpartanFlow {


    Response response;
    int id;
    Faker faker=new Faker();
    Map<String, Object> spartanMap =new HashMap<>();// expected data

    @BeforeClass
    public void before(){
        baseURI= ConfigurationReader.get("spartan_api_url");
    }

    @Test(priority = 1)
    public void postSparta(){
        spartanMap.put("gender",faker.demographic().sex());
        spartanMap.put("name",faker.name().firstName());
        spartanMap.put("phone",1234567895l);

        response=given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .body(spartanMap)
                .when()
                .post("/api/spartans");

        Assert.assertEquals(response.statusCode(),201);
        id=response.path("id");
        get("/api/spartans/"+id)
                .then()
                .statusCode(200)
                .body("name",equalTo(spartanMap.get("name")),
                        "gender",is(spartanMap.get("gender")),
                        "phone",is(spartanMap.get("phone")));



    }
    @Test(priority = 2)
    public void putSpartan(){
        spartanMap.replace("name","shenol");

        given().accept(ContentType.JSON)
                .contentType("application/json")
                .pathParam("id",id)
                .and()
                .body(spartanMap)
                .when()
                .put("/api/spartans/{id}")
                .then()
                .statusCode(204)
                .and()
                .body("name",is(spartanMap.get("name")),
                        "gender",is(spartanMap.get("gender")),
                        "phone",is(spartanMap.get("phone")));
    }

    @Test(priority = 3)
    public void patchSpartan(){
        spartanMap.replace("phone",faker.phoneNumber().cellPhone());

        given().accept(ContentType.JSON)
                .and()
                .contentType("application/json")
                .pathParam("id",id)
                .body(spartanMap.get("phone"))
                .when()
                .patch("/api/spartans/{id}")
                .then()
                .statusCode(201)
                .and()
                .body("phone",is(spartanMap.get("phone")));
    }

    @Test(priority = 4)
    public void deleteSpartan(){
        given().pathParam("id",id)
                .when()
                .delete("api/spartans/{id}")
                .then()
                .statusCode(201);

        given().accept(ContentType.JSON)
                .pathParam("id",id)
                .get("/api/spartans/")
                .then()
                .statusCode(404)
                .body("error",is("not found"));
    }
}
