package MesutAbi;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class Basics {

    @Test
    public void test1(){
        Response response= RestAssured.get("https://age-of-empires-2-api.herokuapp.com/api/v1/civilizations" );

        //System.out.println(response.prettyPrint());

        List<Object> list=response.body().as(List.class);

        Map<String, Object> map= response.as(Map.class);



        //System.out.println(map.get("name"));

        String as=response.path("civilizations.name[0]");
        System.out.println(as);

        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json");

        JsonPath jsonPath=response.jsonPath();

    }

    @Test
    public void test2(){

        Response response=RestAssured.get("http://34.228.41.120:8000/api/spartans");

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.contentType(), "application/json");

        //System.out.println(response.body().prettyPrint());

        List<Map<String, String>> list=response.body().as(List.class);

        list.get(0).get("id");//609

        Assert.assertEquals(list.get(1).get("name"), "zeynepS");

        System.out.println(list);



    }





}
