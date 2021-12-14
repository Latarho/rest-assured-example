package data;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Locale;

public class CourierDataGenerator {
    static Faker faker = new Faker(new Locale("ru_RU"));

    private String login;
    private String password;
    private String firstName;

    public static String getLogin() {
        return RandomStringUtils.randomAlphabetic(10);
    }

    public static String getPassword() {
        return RandomStringUtils.randomAlphabetic(10);
    }

    public static String getFirstName() {
        return faker.name().firstName();
    }
}