package pojo.courier;

import data.CourierDataGenerator;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
// Cериализация
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

public class Courier {
    private String login;
    private String password;
    private String firstName;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public Courier setLogin(String login) {
        this.login = login;
        return this;
    }

    public Courier setLoginAndPassword(String login, String password) {
        this.login = login;
        this.password = password;
        return this;
    }

    public Courier setLoginPasswordAndFirstName(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        return this;
    }

    public static Courier getLoginOnly() {
        return new Courier().setLogin(CourierDataGenerator.getLogin());
    }

    public static Courier getLoginAndPassword() {
        return new Courier().setLoginAndPassword(CourierDataGenerator.getLogin(),
                CourierDataGenerator.getPassword());
    }

    public static Courier getLoginPasswordAndFirstName() {
        return new Courier().setLoginPasswordAndFirstName(CourierDataGenerator.getLogin(),
                CourierDataGenerator.getPassword(), CourierDataGenerator.getFirstName());
    }
}