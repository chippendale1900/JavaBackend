package lesson3;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class lesson3mealPlannerTest {
    private final String apiKey = "326088f36d40481f97a17bd56de451d8";

    @Test
    public void getMealplanner() {
        given()
                .when()
                .get("https://api.spoonacular.com/mealplanner/:username/shopping-list/items" +
                        "hash=ipsum ea proident amet occaecat&apiKey=" +apiKey)
                .then()
                .statusCode(200);
    }

    @Test
    void getMealPlannerTest() {
        JsonPath response = given()
                .queryParam("apiKey", apiKey)
                .queryParam("hash", "ipsum ea proident amet occaecat")
                .when()
                .get("https://api.spoonacular.com/mealplanner/:username/shopping-list/items")
                .body()
                .jsonPath();
        assertThat(response.get("cost"), equalTo(0.71));
    }

    String id;

    @Test
    void postMealPlannerTest() {
        id = given()
                .queryParam("apiKey", apiKey)
                .body("{\n"
                        + " \"item\": 1 package baking powder,\n"
                        + " \"aisle\": Baking,\n"
                        + " \"parse\": true,\n"
                        +")
                        .when()
                        .post("https://api.spoonacular.com/mealplanner/:username/shopping-list/items")
                        .then()
                        .statusCode(200)
                        .extract()
                        .jsonPath()
                        .get("id")
                        .toString();
    }
    @AfterEach
    void tearDown() {
        given()
                .queryParam("apiKey", apiKey)
                .delete("https://api.spoonacular.com/mealplanner/:username/shopping-list/items" + id)
                .then()
                .statusCode(200);
    }



}
