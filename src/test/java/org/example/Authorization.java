package org.example;

import io.restassured.http.ContentType;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

public class Authorization {
    private  static String token;

    public static String token(){

        Map<String, String> params = new HashMap<>();
        params.put("username", "admin");
        params.put("password", "password123");


         token = given()
                .contentType(ContentType.JSON)
                .body(params)
                .when()
                .post("https://restful-booker.herokuapp.com/auth")
                .then()
                .statusCode(200)
                .body("token", notNullValue())
                .extract().path("token")
         ;
        return token;
    }
}
