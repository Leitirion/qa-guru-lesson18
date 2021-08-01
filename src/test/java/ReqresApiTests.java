
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

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
}