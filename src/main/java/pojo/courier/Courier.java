package pojo.courier;

import static data.CourierDataGenerator.*;


public class Courier {
    public String login;
    public String password;
    public String firstName;

    public Courier (String login) {
        this.login = login;
    }

    public Courier (String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Courier (String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public static Courier getRandom() {
        return new Courier(generateLogin(), generatePassword(), generateFirstName());
    }

    public static Courier getRandomWithLoginAndPassword() {
        return new Courier(generateLogin(), generatePassword());
    }

    public static Courier getRandomWithLogin() {
        return new Courier(generateLogin());
    }
}