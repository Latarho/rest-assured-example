package data;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OrderDataGenerator {
    static Faker faker = new Faker(new Locale("ru_RU"));

    public static String generateFirstName() {
        return faker.name().firstName();
    }

    public static String generateLastName() {
        return faker.name().lastName();
    }

    public static String generateAddress() {
        return faker.address().streetAddress();
    }

    public static int generateMetroStation() {
        return (int) (Math.random() * 3);
    }

    public static String generatePhone() {
        return faker.phoneNumber().phoneNumber();
    }

    public static int generateRentTime() {
        return (int) (Math.random() * 3);
    }

    public static String generateDeliveryDate() {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public static String generateComment() {
        return RandomStringUtils.randomAlphabetic(15);
    }
}