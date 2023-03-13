package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static io.restassured.RestAssured.filters;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DZTests {

    // GET
    @Test
    void listUsersPositiveGetTest() {
        Integer expectedTotal = 12;
        Integer actualTotal = given()
                .log().uri()
                .contentType(JSON)
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract()
                .path("total");

        assertEquals(expectedTotal, actualTotal);
    }

    @Test
    void listUsersPositiveGetTest2() {
        String expectedEmail = "byron.fields@reqres.in";
        Response response = given()
                .log().uri()
                .contentType(JSON)
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract()
                .response();
        String actualEmails = response.path("data.email").toString();
        assertTrue(actualEmails.contains(expectedEmail));
    }

    // POST
    @Test
    void registerPositivePostTest() {
        String body = "{ \"email\": \"eve.holt@reqres.in\", " +
                "\"password\": \"pistol\" }"; // todo bad practice

        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(body)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void registerNegativePostTest() {
        String body = "{ \"email\": \"eve.holt@reqres.in\"}"; // todo bad practice

        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(body)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    // PUT
    @Test
    void updatePutTest() {
        String body = "{ \"name\": \"morpheus\", " +
                "\"job\": \"zion resident\" }"; // todo bad practice

        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(body)
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("updatedAt", notNullValue());
    }

    // PATCH
    @Test
    void updatePatchTest() {
        String body = "{ \"name\": \"morpheus\", " +
                "\"job\": \"zion resident\" }"; // todo bad practice

        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(body)
                .when()
                .patch("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("updatedAt", notNullValue());
    }
}
