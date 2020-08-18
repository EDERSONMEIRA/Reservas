package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;

//import static org.example.Authorization.token;
import static org.example.Authorization.token;
import static org.example.Methods.retornarRandomIdBooking;
import static org.hamcrest.Matchers.*;


import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class CheckStatus {


    private static String token;
    /**------------- Config -------------------*/

    @BeforeClass
    public static  void setup(){
        RestAssured.baseURI ="https://restful-booker.herokuapp.com/";
         token = token();
    }

    /**-------------Retorna Lista de Reserva-------------------*/
    @Test
    public void checkStatusBooking(){

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("https://restful-booker.herokuapp.com/booking")
                .then()
                .statusCode(200)
        ;
    }

    /**-------------Retorna Lista de Reserva-------------------*/
    @Test
    public void checkStatusCreateBooking(){
        String reserva ="{\n" +
                "    \"firstname\" : \"Ederson\",\n" +
                "    \"lastname\" : \"ghgj\",\n" +
                "    \"totalprice\" : 111,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(reserva)
                .when()
                .post("https://restful-booker.herokuapp.com/booking")
                .then()
                .statusCode(200)
        ;
    }


    /**-------------Validar Reserva Criada----------*---------*/
    @Test
    public void checkStatusCreatedBooking(){
        String reserva ="{\n" +
                "    \"firstname\" : \"Ederson\",\n" +
                "    \"lastname\" : \"ghgj\",\n" +
                "    \"totalprice\" : 111,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";

        Integer idReserva =
                given()
                        .contentType(ContentType.JSON)
                        .body(reserva)
                        .when()
                        .post("https://restful-booker.herokuapp.com/booking")
                        .then()
                        .statusCode(200)
                        .extract().path("bookingid")
                ;

        given()
                .contentType(ContentType.JSON)
                .when()
                .log().all()
                .get("https://restful-booker.herokuapp.com/booking/{idReserva}",idReserva)
                .then()
                .statusCode(200)
        ;
    }

    @Test
    public void checkStatusDeleteBooking(){
        Integer idReserva = retornarRandomIdBooking();

        given()
                .contentType(ContentType.JSON)
                .header("Cookie", "token="+token)
                .when()
                .delete("https://restful-booker.herokuapp.com/booking/{idReserva}",idReserva)
                .then()
                .statusCode(201)
        ;
    }

}
