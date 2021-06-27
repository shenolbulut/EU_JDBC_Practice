package api_test;
import dbutils.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
public class SpartanTestWithJsonPath {

    @BeforeClass
    public void before(){
        baseURI= ConfigurationReader.get("spartan_api_url");
    }

    @Test
    public void test(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 11)
                .when().get("/api/spartan/{id}");
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"aplicaiton/json");
        //verify id and name with path()

        int id=response.path("id");
        String name=response.path("name");

        assertEquals(id,11);
        assertEquals(name,"Nona");

        //assign response to jsonpath
        JsonPath jsonpath=response.jsonPath();

        int idJson=jsonpath.getInt("id");
        String nameJson=jsonpath.getString("name");
        String gender=jsonpath.get("gender" );
        long phone=jsonpath.getLong("phone");

        //print values
        System.out.println("phone= "+phone);
        System.out.println("gender= "+gender);
        System.out.println("id json= "+idJson);
        System.out.println("name json= "+nameJson);

        //verification
        assertEquals(idJson,11);
        assertEquals(nameJson, "Nona");
        assertEquals(gender,"Female");
        assertEquals(phone,12314234);
    }
}
