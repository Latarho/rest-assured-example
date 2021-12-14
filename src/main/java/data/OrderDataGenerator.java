package data;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OrderDataGenerator {
    static Faker faker = new Faker(new Locale("ru_RU"));

    private String firstName;
    private String lastName;
    private String address;
    private int metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;

    public static String getFirstName() {
        return faker.name().firstName();
    }

    public static String getLastName() {
        return faker.name().lastName();
    }

    public static String getAddress() {
        return faker.address().streetAddress();
    }

    public static int getMetroStation() {
        return (int) (Math.random() * 3);
    }

    public static String getPhone() {
        return faker.phoneNumber().phoneNumber();
    }

    public static int getRentTime() {
        return (int) (Math.random() * 3);
    }

    public static String getDeliveryDate() {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public static String getComment() {
        return RandomStringUtils.randomAlphabetic(15);
    }
}