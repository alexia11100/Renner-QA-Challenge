package client;

import static io.restassured.RestAssured.*;

public abstract class BaseClient {

    public static void initConfig() {
        baseURI = "https://reqres.in/api";

        enableLoggingOfRequestAndResponseIfValidationFails();
    }
}