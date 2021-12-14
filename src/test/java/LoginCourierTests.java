import common.EndPoints;
import helpers.CourierHelper;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.courier.Courier;
import pojo.courier.CourierCredentials;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;

public class LoginCourierTests {
    private CourierHelper courierHelper;
    private int courierId;

    @Before
    @Step("setUp")
    public void setUp () {
        RestAssured.baseURI = EndPoints.BASE_URI;
        courierHelper = new CourierHelper();
        // Проброс запросов и ответов в консоль, аналог log().all()
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    @Description("Login courier with correct credentials")
    public void loginCourierCorrectCredentials() {
        // Get body for new courier
        Courier courier = Courier.getLoginPasswordAndFirstName();
        // Create courier
        courierHelper.createCourier(courier);
        // Login courier
        ValidatableResponse responseLoginCourier =
                courierHelper.loginCourier(new CourierCredentials()
                        .setCourierCredentials(courier.getLogin(), courier.getPassword()));

        courierId = responseLoginCourier.extract().path("id");

        responseLoginCourier.assertThat().statusCode(SC_OK);
        assertThat("Номер курьера не присвоен", courierId, notNullValue());
    }

    @Test
    @Description("Login courier with only login")
    public void loginCourierOnlyLogin() {
        Courier courier = Courier.getLoginPasswordAndFirstName();
        courierHelper.createCourier(courier);
        ValidatableResponse responseLoginCourier =
                courierHelper.loginCourier(new CourierCredentials()
                        .setCourierLoginCredential(courier.getLogin()));

        String actualErrorMessage = responseLoginCourier.extract().path("message");
        String expectedErrorMessage = "Недостаточно данных для входа";

        responseLoginCourier.assertThat().statusCode(SC_BAD_REQUEST);
        assertEquals("Фактическое сообщение в ответе отличается от ожидаемого", expectedErrorMessage,
                actualErrorMessage);

        ValidatableResponse responseLoginCourierForID =
                courierHelper.loginCourier(new CourierCredentials()
                        .setCourierCredentials(courier.getLogin(), courier.getPassword()));
        courierId = responseLoginCourierForID.extract().path("id");
    }

    @Test
    @Description("Login courier with only password")
    public void loginCourierOnlyPassword() {
        Courier courier = Courier.getLoginPasswordAndFirstName();
        courierHelper.createCourier(courier);
        ValidatableResponse responseLoginCourier =
                courierHelper.loginCourier(new CourierCredentials()
                        .setCourierPasswordCredential(courier.getPassword()));

        String actualErrorMessage = responseLoginCourier.extract().path("message");
        String expectedErrorMessage = "Недостаточно данных для входа";

        responseLoginCourier.assertThat().statusCode(SC_BAD_REQUEST);
        assertEquals("Фактическое сообщение в ответе отличается от ожидаемого", expectedErrorMessage,
                actualErrorMessage);

        ValidatableResponse responseLoginCourierForID =
                courierHelper.loginCourier(new CourierCredentials()
                        .setCourierCredentials(courier.getLogin(), courier.getPassword()));
        courierId = responseLoginCourierForID.extract().path("id");
    }

    @Test
    @Description("Login courier with wrong login")
    public void loginCourierWrongLogin() {
        Courier courier = Courier.getLoginPasswordAndFirstName();
        ValidatableResponse responseLoginCourier =
                courierHelper.loginCourier(new CourierCredentials()
                        .setCourierCredentials(courier.getLogin(), courier.getPassword()));

        String actualErrorMessage = responseLoginCourier.extract().path("message");
        String expectedErrorMessage = "Учетная запись не найдена";

        responseLoginCourier.assertThat().statusCode(SC_NOT_FOUND);
        assertEquals("Фактическое сообщение в ответе отличается от ожидаемого", expectedErrorMessage,
                actualErrorMessage);
    }

    @Test
    @Description("Login courier with wrong password")
    public void loginCourierWrongPassword() {
        Courier courier = Courier.getLoginPasswordAndFirstName();
        String wrongPassword = "121445324523seg1D*";
        courierHelper.createCourier(courier);
        ValidatableResponse responseLoginCourier =
                courierHelper.loginCourier(new CourierCredentials()
                        .setCourierCredentials(courier.getLogin(), wrongPassword));

        String actualErrorMessage = responseLoginCourier.extract().path("message");
        String expectedErrorMessage = "Учетная запись не найдена";

        responseLoginCourier.assertThat().statusCode(SC_NOT_FOUND);
        assertEquals("Фактическое сообщение в ответе отличается от ожидаемого", expectedErrorMessage,
                actualErrorMessage);

        ValidatableResponse responseLoginCourierForID =
                courierHelper.loginCourier(new CourierCredentials()
                        .setCourierCredentials(courier.getLogin(), courier.getPassword()));
        courierId = responseLoginCourierForID.extract().path("id");
    }

    @After
    @Step("Teardown - delete courier")
    public void tearDown() {
        if (courierId != 0)
            courierHelper.deleteCourierById(courierId);
    }
}