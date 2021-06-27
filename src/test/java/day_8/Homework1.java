package day_8;
import dbutils.ConfigurationReader;
import dbutils.ExcelUtil;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class Homework1 {

    @BeforeClass
    public void before(){
        baseURI= ConfigurationReader.get("spartan_api_url");
    }

    @DataProvider
    public Object[][] test(){
        ExcelUtil excelUtil=new ExcelUtil("src/test/resources/mockdata.xlsx", "data");

        Object [][] dataMock=excelUtil.getDataArrayWithoutFirstRow();
        return dataMock;
    }

    @Test(dataProvider = "test")
    public void test2(Object phone,String name, String gender ){

        SpartanPojo spartanPojo=new SpartanPojo(name,gender,phone);

        System.out.println(spartanPojo.toString());

        given().accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .body(spartanPojo)
                .when()
                .post("/api/spartans")
                .then()
                .statusCode(201)
                .and()
                .body("data.name",is(name),"data.phone",is(phone),
                        "data.gender",is(gender));

    }
}
