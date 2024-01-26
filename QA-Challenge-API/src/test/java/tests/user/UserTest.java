package tests.user;

import client.UserClient;
import dataFactory.UserDataFactory;
import model.User;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {
    private UserClient userClient = new UserClient();

    // Get single user tests
    @Test
    public void testValidGetSingleExistingUser() {
        User userResult = userClient.getUser("1")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().jsonPath().getObject("data", User.class)
                ;

        Assertions.assertAll("Asserting user get attributes",
                () -> Assertions.assertEquals(1, userResult.getId()),
                () -> Assertions.assertEquals("george.bluth@reqres.in", userResult.getEmail()),
                () -> Assertions.assertEquals("George", userResult.getFirst_name()),
                () -> Assertions.assertEquals("Bluth", userResult.getLast_name()),
                () -> Assertions.assertEquals("https://reqres.in/img/faces/1-image.jpg", userResult.getAvatar())
        );
    }

    @Test
    public void testInvalidGetSingleUserWithNegativeId() {
        userClient.getUser("-1")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
        ;
    }

    @Test
    public void testInvalidGetSingleUserWithStringId() {
        userClient.getUser("abcde")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
        ;
    }

    // Post tests
    @Test
    public void testCreateValidUser() {
        User user = UserDataFactory.newValidUser();
        User userPostResult = userClient.createUser(user)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract().as(User.class)
                ;

        user.setId(userPostResult.getId());

        Assertions.assertAll("Asserting user post attributes",
                () -> Assertions.assertEquals(user.getId(), userPostResult.getId()),
                () -> Assertions.assertEquals(user.getEmail(), userPostResult.getEmail()),
                () -> Assertions.assertEquals(user.getFirst_name(), userPostResult.getFirst_name()),
                () -> Assertions.assertEquals(user.getLast_name(), userPostResult.getLast_name()),
                () -> Assertions.assertEquals(user.getAvatar(), userPostResult.getAvatar())
        );

        // Check if the user was saved
        User userGetResult = userClient.getUser(String.format("%d", userPostResult.getId()))
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract().as(User.class)
                ;

        Assertions.assertAll("Asserting user get attributes",
                () -> Assertions.assertEquals(user.getId(), userGetResult.getId()),
                () -> Assertions.assertEquals(user.getEmail(), userGetResult.getEmail()),
                () -> Assertions.assertEquals(user.getFirst_name(), userGetResult.getFirst_name()),
                () -> Assertions.assertEquals(user.getLast_name(), userGetResult.getLast_name()),
                () -> Assertions.assertEquals(user.getAvatar(), userGetResult.getAvatar())
        );
    }

    @Test
    public void testCreateUserWithInvalidEmail() {
        User user = UserDataFactory.newUserWithInvalidEmail();
        userClient.createUser(user)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
        ;
    }

    @Test
    public void testCreateUserWithInvalidName() {
        User user = UserDataFactory.newUserWithInvalidName();
        userClient.createUser(user)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
        ;
    }
}