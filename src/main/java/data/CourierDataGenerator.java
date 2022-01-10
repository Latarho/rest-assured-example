package data;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Locale;

public class CourierDataGenerator {
    static Faker faker = new Faker(new Locale("ru_RU"));

    public static String generateLogin() {
        return RandomStringUtils.randomAlphabetic(10);
    }

    public static String generatePassword() {
        return RandomStringUtils.randomAlphabetic(10);
    }

    public static String generateFirstName() {
        return faker.name().firstName();
    }
}