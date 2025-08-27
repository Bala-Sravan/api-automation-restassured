package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserApiTests {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test
    public void testGetUsers() {
        given()
            .when()
            .get("/users?page=2")
            .then()
            .statusCode(200)
            .body("data", not(empty()))
            .body("data[0].email", containsString("@reqres.in"));
    }

    @Test
    public void testGetSingleUser() {
        given()
            .when()
            .get("/users/2")
            .then()
            .statusCode(200)
            .body("data.id", equalTo(2))
            .body("data.email", containsString("@reqres.in"));
    }

    @Test
    public void testCreateUser() {
        String requestBody = "{ \"name\": \"Sravan\", \"job\": \"SDET\" }";

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .post("/users")
            .then()
            .statusCode(201)
            .body("name", equalTo("Sravan"))
            .body("job", equalTo("SDET"));
    }

    @Test
    public void testUpdateUser() {
        String requestBody = "{ \"name\": \"Sravan\", \"job\": \"QA Lead\" }";

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .put("/users/2")
            .then()
            .statusCode(200)
            .body("job", equalTo("QA Lead"));
    }

    @Test
    public void testDeleteUser() {
        given()
            .when()
            .delete("/users/2")
            .then()
            .statusCode(204);
    }
}
