package api_test;
import com.sun.istack.NotNull;
import dbutils.ConfigurationReader;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class HamcrestMatchersApiTest {




    @Test
    public void test1(){
        given().accept(ContentType.JSON).and().pathParam("id",15)
                .when().get("/api/spartans/{id}")
                .then().statusCode(200)
                .and().assertThat().contentType(ConfigurationReader.get("spartans_api_url")+"aplication/json")
                .and().assertThat().body("id",equalTo(15),
                                            "name",equalTo("Meta"),
                                                    "gender",equalTo("Female"),
                                                    "phone",equalTo(23423423));
    }

    @Test
    public void teachersData(){
        given().accept(ContentType.JSON).pathParam("id",8261)
                .when().log().all().get("http://api.cybertektraining.com/teacher/{id}")
                .then().assertThat().statusCode(200).contentType(equalTo("application/json;charset=UTF-8"))
                .and().header("Content-Length",equalTo("240"))
                .and().header("Connection","Keep-Alive")
                .and().header("Date", notNullValue())
                .and().body("teachers.firstName[0] ",equalTo("James"),
                "teachers.lastName[0]",equalTo("Bond"),
                "teachers.gender[0]",equalTo("Male"))
                .log().all();

    }
    @Test
    public void teachersWithDepartments(){

        given().accept(ContentType.JSON)
                .and().pathParam("name","Computer")
                .when().log().all().get("http://api.cybertektraining.com/teacher/department/{name}")
                .then().statusCode(200).and()
                .contentType(equalTo("application/json;charset=UTF-8")).and()
                .body("teachers.firstName",hasItems("Alexander","Marteen"));



    }
}
