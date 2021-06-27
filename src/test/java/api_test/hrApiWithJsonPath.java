package api_test;

import dbutils.ConfigurationReader;
import groovy.json.JsonParser;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
public class hrApiWithJsonPath {
    @BeforeClass
    public void before(){
        baseURI= ConfigurationReader.get("hr_api");

    }
    @Test
    public void test1(){
        Response response = given().accept(ContentType.JSON)
                .get("/countries");
        JsonPath jsonPath=response.jsonPath();

        String secondCountryName=jsonPath.getString("items.country_name[1]");
        System.out.println(secondCountryName);

        //get all country ids
        List<String> allCountries=jsonPath.getList("items.country_id");
        System.out.println(allCountries);

        //get all countries where region id is equal to 2

        List<String> allCountriesWhichsIDTwo=jsonPath.getList("items.findAll {it.region_id==2}.country_name");
    }

    @Test
    public void test2(){
        Response response = given().queryParam("limit", 107)
                .get("/employees");
        JsonPath jsonPath = response.jsonPath();

        //get me all email of employees who is working as IT_PROG
        List<String> ITProgsEmails=jsonPath.getList("items.findAll {it.job_id==\"IT_PROG\"}.email");
        System.out.println("ITProgsEmails = " + ITProgsEmails);

        //get me all firstname of emplyoees who is making more than 10000
        List<String> firstNames=jsonPath.getList("items.findAll {it.salary>10000}.first_name");

        //get me first name of who is making highest salary

        String firstnameMaxSalary=jsonPath.getString("item.max{it.salary}.first_name");


    }
}
