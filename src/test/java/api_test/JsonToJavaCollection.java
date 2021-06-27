package api_test;
import dbutils.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
public class JsonToJavaCollection {

    @BeforeClass
    public void before(){
        baseURI= ConfigurationReader.get("spartans_api_url");
    }

    @Test
    public void test1(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 15)
                .when().get("/api/spartans/{id}");

        //we will convert json body to java collection

        Map<String, Object> jsonDataMap=response.body().as(Map.class);
        System.out.println("jsonDataMap = " + jsonDataMap);

        String name= (String) jsonDataMap.get("name");
        assertEquals(name,"Meta");

        BigDecimal phone=new BigDecimal(String.valueOf(jsonDataMap.get("phone")));
        System.out.println("phone = " + phone);
    }

    @Test
    public void test2(){
        Response response = given().accept(ContentType.JSON)
                .when().get("/api/spartans");

        //we need to de-serialize Json response to list of map

        List<Map<String, Object>> allSpartanList=response.body().as(List.class);

        System.out.println("allSpartanList = " + allSpartanList);

        //pring second spartan firstname
        System.out.println(allSpartanList.get(1).get("firstname"));
        //save spartan 3 in a map
        Map<String, Object> spartan2=allSpartanList.get(2);

    }
    @Test
    public void test3(){
        Response response = given().accept(ContentType.JSON)
                .and().get("http://52.55.102.92:1000/ords/hr/regions");
        assertEquals(response.statusCode(),200);

        //we de-serialize json body to map
        Map<String, Object> regionMap=response.as(Map.class);
        System.out.println(regionMap.get("count"));
        System.out.println(regionMap.get("hasMore"));
        System.out.println(regionMap.get("items"));

        List<Map<String, Object>> itemsList=(List<Map<String, Object>>) regionMap.get("items");//item is json object of map key.
                                //so need to cast it for list of map.
        System.out.println(itemsList);
    }
}
