package day_8;
import dbutils.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class JsonSchemaValidationDemo {

    @BeforeClass
    public void before(){
        baseURI= ConfigurationReader.get("spartan_api_url");
    }

    @Test
    public void schemaValidation(){
        given().accept(ContentType.JSON)
                .pathParam("id",402)
                .when()
                .get("/api/spartans/{id}")
                .then()
                .statusCode(200)
                .and()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SingleSpartanSchema.json"));
    }
}
