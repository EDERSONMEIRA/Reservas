package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.example.Authorization.token;

import static org.example.Methods.retornarRandomIdBooking;
import static org.hamcrest.Matchers.*;

public class TestBooking {

    private  static String token;
    @BeforeClass
    public static  void setup(){
        RestAssured.baseURI ="https://restful-booker.herokuapp.com/";
        token = token();

    }

    @Test
    public void retornarListaReserva(){

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("https://restful-booker.herokuapp.com/booking")
                .then()
                .body("$", Matchers.notNullValue())
                .body("$.size()", Matchers.greaterThan(0))
        ;
    }

    @Test
    public void criarReserva(){
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
                .body("bookingid", Matchers.notNullValue())
                .body("booking.firstname", is("Ederson"))
                .log().body()
        ;
    }

    @Test
    public void validarReservaCriada(){
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
                        .body("bookingid",notNullValue())
                        .extract().path("bookingid")
                ;

        given()
                .contentType(ContentType.JSON)
                .when()
                .log().all()
                .get("https://restful-booker.herokuapp.com/booking/{idReserva}",idReserva)
                .then()
                .body("firstname", is("Ederson"))
                .log().body()
        ;
    }


    @Test
    public void deletarReserva(){

        Integer idReserva = retornarRandomIdBooking();

        given()
                .contentType(ContentType.JSON)
                .header("Cookie", "token="+token)
                .log().all()
                .when()
                .delete("https://restful-booker.herokuapp.com/booking/{idReserva}",idReserva)
                .then()
        ;
    }


}
