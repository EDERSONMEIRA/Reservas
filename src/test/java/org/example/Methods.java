package org.example;

import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class Methods {

    public static Integer retornarRandomIdBooking(){

        List<Integer> listReservas =
                given()
                .contentType(ContentType.JSON)
                .when()
                .get("https://restful-booker.herokuapp.com/booking")
                .then()
                .body("$", Matchers.notNullValue())
                .body("$.size()", Matchers.greaterThan(0))
                .extract().path("bookingid")
        ;

        Random rand = new Random();

        return listReservas.get(rand.nextInt(listReservas.size()));
    }
}
