package dataFactory;

import model.User;
import net.datafaker.Faker;

import java.util.Locale;

public class UserDataFactory {
    private static Faker faker = new Faker(new Locale("PT-BR"));

    public static User newUserWithInvalidEmail(){
        User userWithInvalidEmail = newValidUser();
        userWithInvalidEmail.setEmail("");

        return userWithInvalidEmail;
    }

    public static User newUserWithInvalidName(){
        User userWithInvalidName = newValidUser();
        userWithInvalidName.setFirst_name("");
        userWithInvalidName.setLast_name("");

        return userWithInvalidName;
    }

    public static User newValidUser() {
        User newUser = new User();
        newUser.setEmail(faker.internet().emailAddress());
        newUser.setFirst_name(faker.name().firstName());
        newUser.setLast_name(faker.name().lastName());
        newUser.setAvatar(faker.avatar().image());

        return newUser;
    }
}