package api_test;

import dbutils.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

public class HomeworkOrds {

    @BeforeClass
    public void before(){
        baseURI= ConfigurationReader.get("hr_api");

    }

//- Given accept type is Json
//- Path param value- US
//- When users sends request to /countries
//- Then status code is 200
//            - And Content - Type is Json
//- And country_id is US
//- And Country_name is United States of America
//- And Region_id is
    @Test
    public void test1(){
        Response response = given().accept(ContentType.JSON).and().pathParam("value", "US")
                .when().get("/countries");
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"aplication/json");
        JsonPath jsonPath = response.jsonPath();
        assertEquals(response.path("country_id"),"US");
        assertEquals(response.path("country_name"),"United States fo America");
        assertEquals(jsonPath.getInt("region_id"),3 );

    }

    public void hamcrestTest(){
        given().accept(ContentType.JSON).and().pathParam("value","US")
                .when().get("/countries").then().statusCode(200)
                .and().contentType("aplication/json").and().assertThat()
                .body("country_id",equalTo("US"),
                        "country_name",equalTo("United States of America"),
                        "region_id",equalTo(3));
    }

//- Given accept type is Json
//- Query param value - q={"department_id":80}
//- When users sends request to /employees
//- Then status code is 200
// - And Content - Type is Json
//- And all job_ids start with 'SA'
//- And all department_ids are 80
//- Count is 25

    @Test
    public void test2(){
        Response response = given().accept(ContentType.JSON).and().queryParam("q", "{\"department_id\":80")
                .when().get("employees");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"aplication/json");

        JsonPath jsonPath=response.jsonPath();
        List<String> jobIDs=jsonPath.getList("job_id");
        for (String jobID:jobIDs){
            assertTrue(jobID.startsWith("SA"));
        }
        List<String> departmnets=jsonPath.getList("department_id");
        for(String department:departmnets){
            assertEquals(department,80);
        }

    }


//- Given accept type is Json
//-Query param value q= region_id 3
//- When users sends request to /countries
//- Then status code is 200
//- And all regions_id is 3
//- And count is 6
//- And hasMore is false
//- And Country_name are;
// Australia,China,India,Japan,Malaysia,Singapore

    @Test
    public void test3(){
        Response response = given().accept(ContentType.JSON).and().queryParam("g", "{\"region_id\":3")
                .when().get("/countries");
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"aplication/json");
    }
}
