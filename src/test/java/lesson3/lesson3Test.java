package lesson3;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class lesson3Test {
    private final String apiKey = "326088f36d40481f97a17bd56de451d8";

    @Test
    public void getRecipePositiveTest() {
        given()
                .when()
                .get("https://api.spoonacular.com/recipes/716429/information?" +
                        "apiKey" +apiKey)
                .then()
                .statusCode(200);
    }

    @Test
    void getRecipeWithQueryParametersPositiveTest() {
        given()
                .queryParam("apiKey", apiKey)
                .queryParam("includeNutrition", "false")
                .when()
                .get("https://api.spoonacular.com/recipes/716429/information")
                .then()
                .statusCode(200);
    }

    @Test
    void complexSearchTest() {
        JsonPath response = given()
                .queryParam("apiKey", apiKey)
                .when()
                .get("https://api.spoonacular.com/recipes/716429/information")
                .body()
                .jsonPath();
        assertThat(response.get("query"), equalTo("pasta"));
        assertThat(response.get("offset"), equalTo(0));
        assertThat(response.get("cuisine"), equalTo("italian"));
        assertThat(response.get("diet"), equalTo("vegetarian"));
        assertThat(response.get("intolerances"), equalTo("gluten"));
        assertThat(response.get("includeIngredients"), equalTo("tomato,cheese"));
        assertThat(response.get("excludeIngredients"), equalTo("eggs"));
        assertThat(response.get("instructionsRequired"), is(true));
        assertThat(response.get("fillIngredients"), is(false));
        assertThat(response.get("recipeBoxId"), equalTo(2468));
        assertThat(response.get("sort"), equalTo("calories"));
        assertThat(response.get("minFat"), equalTo(1));
        assertThat(response.get("minCaffeine"), equalTo(0));
        assertThat(response.get("maxCaffeine"), equalTo(100));
        assertThat(response.get("number"), equalTo(10));
        assertThat(response.get("type"), equalTo("salad"));
        assertThat(response.get("equipment"), equalTo("pan"));

    }
    String id;

    @Test
    void cuisineTest() {
        id = given()
                .queryParam("apiKey", apiKey)
                .body("{\n"
                        + " \"title\": Pork roast with green beans,\n"
                        + " \"ingredientList\": 3 oz pork shoulder,\n"
                        + " \"language\": en,\n"
                        +")
                        .when()
                        .post("https://api.spoonacular.com/recipes/cuisine")
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
                .delete("https://api.spoonacular.com/recipes/cuisine" + id)
                .then()
                .statusCode(200);
    }
}
