package day_8;

import org.testng.annotations.Test;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
public class SpartanBasicAuth {

    @Test
    public void test1(){
        given()
                .accept(ContentType.JSON)
                .and()
                .auth().basic("admin","admin")
        .when()
                .get("http://34.228.41.120:7000/api/spartans")
        .then().log().all()
                .statusCode(200);


    }
}
