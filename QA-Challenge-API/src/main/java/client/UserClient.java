package client;

import model.User;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class UserClient extends BaseClient {
    private static String USER = "/users";

    public UserClient() {
        BaseClient.initConfig();
    }

    public Response getUser(String _id) {
        return
                given()
                        .pathParam("_id", _id)
                .when()
                        .get(USER+"/{_id}")
                ;
    }

    public Response createUser(User user) {
        return
                given()
                        .contentType(ContentType.JSON)
                        .body(user.toString())
                .when()
                        .post(USER)
                ;
    }
}