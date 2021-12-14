package pojo.courier;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
// Cериализация
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

public class CourierCredentials {
    private String login;
    private String password;

    public CourierCredentials setCourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
        return this;
    }

    public CourierCredentials setCourierLoginCredential(String login) {
        this.login = login;
        this.password = "";
        return this;
    }

    public CourierCredentials setCourierPasswordCredential(String password) {
        this.login = "";
        this.password = password;
        return this;
    }
}