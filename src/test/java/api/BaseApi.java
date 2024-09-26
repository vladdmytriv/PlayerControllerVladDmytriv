package api;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import properties.PropertiesReader;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.testng.Assert.assertEquals;

public class BaseApi {
    protected RequestSpecification apiSpec;

    public BaseApi() {
        PropertiesReader propertiesReader = new PropertiesReader();
        RestAssured.defaultParser = Parser.JSON;

        apiSpec = given()
                .baseUri(propertiesReader.getBaseUri())
                .contentType(JSON)
                .accept(JSON);
    }

    public Response sendGet(String endpoint) {
        return apiSpec
                .get(endpoint)
                .then()
                .contentType(JSON)
                .log()
                .all()
                .extract()
                .response();
    }

    //could be a bug (getting blank content-type, seems like no content response)
    public Response sendGetWithQueryParams(String endpoint, Map<String, Object> params) {
        return apiSpec
                .queryParams(params)
                .get(endpoint)
                .then()
                .contentType(JSON)
                .log()
                .all()
                .extract()
                .response();
    }

    public Response sendPost(String endpoint, Object body) {
        return apiSpec
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .contentType(JSON)
                .log()
                .all()
                .extract()
                .response();
    }

    public Response sendDelete(String endpoint, Object body) {
        return apiSpec
                .body(body)
                .when()
                .delete(endpoint)
                .then()
                .contentType(JSON)
                .log()
                .all()
                .extract()
                .response();
    }

    public Response sendPatch(String endpoint, Object body) {
        return apiSpec
                .body(body)
                .when()
                .patch(endpoint)
                .then()
                .contentType(JSON)
                .log()
                .all()
                .extract()
                .response();
    }

    public void verifyStatusCode(Response response, int expectedStatusCode) {
        assertEquals(response.getStatusCode(), expectedStatusCode, "Actual status code should be equal to expected");
    }
}
