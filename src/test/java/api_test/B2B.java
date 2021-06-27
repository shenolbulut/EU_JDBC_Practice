package api_test;

import dbutils.DbUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Type;
import java.util.*;

import static io.restassured.RestAssured.*;

public class B2B {

    String token;

    @BeforeMethod
    public void before() throws Exception {
        baseURI= "https://test-api.biletdukkani.com";

    }

    @Test
    public void test1(){

        Map<String,Object> erlEncode=new HashMap<String, Object>();
        erlEncode.put("grant_type","password");
        erlEncode.put("client_id","agent.web");
        erlEncode.put("client_secret","secret");
        erlEncode.put("username", "admin@roofstacks.com");
        erlEncode.put("password","12345678");
        erlEncode.put("scope", "openid profile email IdentityServerApi role ticket.api permission");

        Response response=given().relaxedHTTPSValidation()
                .accept("*/*")
                .headers("Cach-Control","no-cache")
//                .headers("Host", "<calculated when request is sent>")
//                .contentType("application/x-www-form-urlencoded")
                .basePath("/connect/token")
                .formParams(erlEncode)
                .post().then()
                .assertThat()
                .statusCode(200)
                .extract()
                .response();
        response.prettyPrint();

        String token=response.body().path("access_token");
        System.out.println(token);



    }

    public String getToken(){
        Map<String,Object> erlEncode=new HashMap<String, Object>();
        erlEncode.put("grant_type","password");
        erlEncode.put("client_id","agent.web");
        erlEncode.put("client_secret","secret");
        erlEncode.put("username", "admin@roofstacks.com");
        erlEncode.put("password","12345678");
        erlEncode.put("scope", "openid profile email IdentityServerApi role ticket.api permission");

        Response response=given().relaxedHTTPSValidation()
                .accept("*/*")
                .headers("Cach-Control","no-cache")
                .contentType("application/x-www-form-urlencoded")
                //.basePath("/connect/token")
                .formParams(erlEncode)
                .post("https://test-yetki.biletdukkani.com/connect/token").then()
                .assertThat()
                .statusCode(200)
                .extract()
                .response();

        String token=response.body().path("access_token");
        this.token= "Bearer "+ token;
        return this.token;

    }

    @Test
    public void test2(){
        Map<String,Object> flightDetail=new HashMap<>();
        flightDetail.put("adults",1);
        flightDetail.put("originType","city");
        flightDetail.put("originCode","IST");
        flightDetail.put("destinationType","city");
        flightDetail.put("destinationCode","ADA");
        flightDetail.put("departDate", DbUtils.costumeDate("DAY",25));
        System.out.println(flightDetail);
        System.out.println(getToken());



        System.out.println(token);
        RequestSpecification specification=given()
                .relaxedHTTPSValidation()
                .header("Authorization",token)
                .contentType(ContentType.JSON)
                .basePath("/flights/search");

        Response response= specification
                .queryParams(flightDetail)
                .get()
                .then()
                .statusCode(200)
                .extract().response();

        response.prettyPrint();



    }

    @Test
    public void test4(){


        Response response=given()
                .relaxedHTTPSValidation()
                .header("Authorization",getToken())
                .contentType(ContentType.JSON)
                .when()
                .get("https://test-api.biletdukkani.com/flights/search?cabin=Economy&adults=1&originType=city&destinationType=city&originCode=IST&destinationCode=AYT&departDate=2021-06-30T00:00:00.000Z")
                .then()
                .assertThat().statusCode(200)
                .extract().response();

        //response.prettyPrint();

        String flightId=response.body().path("routes[0].flights[0].id");
        String brandId=response.body().path("routes[0].flights[0].brands[0].id");

        System.out.println(flightId);
        System.out.println(brandId);


        Map<Object,Object> farePost=new HashMap<>();
        List<Map<String,String>> flightBrandList=new ArrayList<>();
        Map<String, String> fBId=new HashMap<>();
        fBId.put("flightId",flightId);
        fBId.put("brandId",brandId);
        flightBrandList.add(fBId);



        String x="{\n" +
                "    \"adults\": 1,    \n" +
                "    \"flightBrandList\": [\n" +
                "        {\n" +
                "            \"flightId\": \""+flightId+"\",\n" +
                "            \"brandId\": \""+brandId+"\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        farePost.put("adults",1);
        farePost.put("flightBrandList",flightBrandList);
        System.out.println(farePost);

        Response response1=given().relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(farePost)
                .basePath("/flights/fare")
                .when()
                .post()
                .then().statusCode(200)
                .extract().response();

        response1.prettyPrint();
        Map<String, ArrayList<String>> fareId = response1.body().as((Type) Map.class);
        System.out.println( fareId.get("fareIds").get(0));

        String fareIds=fareId.get("fareIds").get(0);


        String orders="{\n" +
                "    \"isNew\": true,\n" +
                "    \"fareIds\": [\n" +
                "        \""+fareIds+"\"\n" +
                "    ],\n" +
                "    \"passengers\": [\n" +
                "        {\n" +
                "            \"firstName\": \"Kral\",\n" +
                "\t\t\t\"lastName\": \"King\",\n" +
                "\t\t\t\"birthDate\": \"1988-09-16T00:00:00.000Z\",\n" +
                "\t\t\t\"phoneArea\": \"+90\",\n" +
                "\t\t\t\"phoneNumber\": \"5386618872\",\n" +
                "\t\t\t\"passengerType\": \"adult\",\n" +
                "\t\t\t\"genderType\": \"female\",\n" +
                "\t\t\t\"email\": \"burcu.engin@roofstacks.com\",\n" +
                "\t\t\t\"wheelChairServiceType\": \"none\",\n" +
                "\t\t\t\"countryCode\": \"TR\",\n" +
                "\t\t\t\"passportNumber\": \"1Q2W3E4R\",\n" +
                "\t\t\t\"passportEndDate\": \"2024-03-01T00:00:00.000Z\",\n" +
                "            \"hesCode\": \"1111111111\",\n" +
                "            \"citizenNumber\": \"10367706088\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"bookingType\": \"book\",\n" +
                "    \"isSaga\": false,\n" +
                "    \"orderType\": \"individual\",\n" +
                "    \"phoneNumber\": \"4545454545\",\n" +
                "    \"phoneArea\": \"+90\",\n" +
                "    \"email\": \"admin@roofstacks.com\",\n" +
                "    \"agencyCommission\": 0,\n" +
                "    \"paymentType\": \"creditCard\",\n" +
                "    \"paymentMethod\": \"direct\",\n" +
                "    \"creditCard\": {\n" +
                "        \"cvv\": \"000\",\n" +
                "        \"name\": \"test\",\n" +
                "        \"pan\": \"4355084355084358\",\n" +
                "        \"validMonth\": \"12\",\n" +
                "        \"validYear\": \"26\"\n" +
                "    },\n" +
                "    \"charge\": {\n" +
                "        \"amount\": 226.99,\n" +
                "        \"currencyCode\": \"TRY\",\n" +
                "        \"installmentId\": 23,\n" +
                "        \"redirectUrl\": \"https://test-biletdukkani-b2b.web.app/payment/return\",\n" +
                "        \"ip\": \"51.144.228.152\"\n" +
                "    },\n" +
                "    \"invoicePreference\": \"toPerPassenger\",\n" +
                "    \"invoiceCustomer\": null,\n" +
                "    \"smsRequest\": false\n" +
                "}";

        Response response2=given().relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(orders)
                .basePath("/orders")
                .when()
                .post()
                .then().statusCode(200)
                .extract()
                .response();

        List<Map<String, Object>> orderId=response2.body().as(List.class);
        System.out.println("orderId: "+orderId.get(0).get("id"));

        Response response3=given().relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .header("Authorization",token)
                .pathParam("id", orderId)
                .basePath("/orders/{id}")
                .when()
                .delete()
                .then()
                .extract().response();

        response3.prettyPrint();

    }
}


