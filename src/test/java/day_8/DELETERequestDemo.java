package day_8;


import dbutils.ConfigurationReader;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class DELETERequestDemo {

    @BeforeClass
    public void before(){
        baseURI= ConfigurationReader.get("spartan_api_url");
    }

    @Test
    public void test1(){
        Random random=new Random();

        int idNum=random.nextInt(200)+1;

        given().pathParam("id",idNum)
                .when()
                .delete("/api/spartans/{id}")
                .then()
                .statusCode(204).log().all();

        given().accept(ContentType.JSON)
                .pathParam("id",idNum)
                .when()
                .get("/api/spartans/{id}")
                .then()
                .statusCode(404)
                .body("error",is("not found"));

    }
}
