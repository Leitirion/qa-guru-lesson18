import models.ResourceData;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.hasItem;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReqresApiTests {
    @Test
    void getListOfUsersPerPageTest() {
        given()
                .spec(Specs.request)
                .when()
                .get("users?page=2")
                .then()
                .spec(Specs.response)
                .body("per_page", is(6));
    }

    @Test
    void getSingleUserTotalTest() {
        given()
                .spec(Specs.request)
                .when()
                .get("/users?page=2")
                .then()
                .spec(Specs.response)
                .body("total", is(12));
    }

    @Test
    void getSingleUserNotFoundTest() {
        given()
                .spec(Specs.request)
                .when()
                .get("/users/456")
                .then()
                .spec(Specs.response2);
    }

    @Test
    void getListResourceTest() {
        given()
                .spec(Specs.request)
                .when()
                .get("/unknown")
                .then()
                .spec(Specs.response)
                .body("total", is(12));
    }

    @Test
    void getSingleResourceTest() {
        given()
                .spec(Specs.request)
                .when()
                .get("/unknown/6")
                .then()
                .spec(Specs.response)
                .contentType(ContentType.JSON)
                .body("data.name", is("blue turquoise"));

    }

    @Test
    void getSingleResourceNotFound() {
        given()
                .spec(Specs.request)
                .when()
                .get("/unknown/456")
                .then()
                .spec(Specs.response2);
    }

    @Test
    void createUserTest() {
        given()
                .spec(Specs.request)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"name\": \"Shelby\",\n" +
                        "    \"job\": \"engineer\"\n" +
                        "}")
                .when()
                .post("/users")
                .then()
                .spec(Specs.response1)
                .body("name", is("Shelby"));
    }

    @Test
    void updateUserTest() {
        given()
                .spec(Specs.request)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"name\": \"Shelby\",\n" +
                        "    \"job\": \"engineer\"\n" +
                        "}")
                .when()
                .put("/users/4")
                .then()
                .spec(Specs.response)
                .body("name", is("Shelby"));
    }

    @Test
    void singleResourcesWithLombokModel() {
        ResourceData resp = given()
                .spec(Specs.request)
                .when()
                .get("/unknown/4")
                .then()
                .spec(Specs.response)
                .log().body()
                .extract().as(ResourceData.class);
        assertEquals("#7BC4C4", resp.getResource().getColor());
    }

    @Test
    void getListResourcesWithLombokAndGroovy() {
        given()
                .spec(Specs.request)
                .when()
                .get("/unknown")
                .then()
                .spec(Specs.response)
                .log().body()
                .body("data.findAll{it.id > 2}.id",
                        hasItem(6));
    }
}