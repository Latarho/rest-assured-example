import common.EndPoints;
import helpers.CourierHelper;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import pojo.courier.Courier;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.courier.CourierCredentials;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreateCourierTests {
    private CourierHelper courierHelper;
    private int courierId;

    @Before
    @Step("setUp")
    public void setUp() {
        RestAssured.baseURI = EndPoints.BASE_URI;
        courierHelper = new CourierHelper();
        // Проброс запросов и ответов в консоль, аналог log().all()
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    @Description("Create courier with all fields (login, password, firstname)")
    public void createCourierLoginPasswordAndFirstName() {
        Courier courier = Courier.getLoginPasswordAndFirstName();
        ValidatableResponse response = new CourierHelper().createCourier(courier);

        boolean isCourierCreated = response.extract().path("ok");

        response.assertThat().statusCode(SC_CREATED);
        assertTrue(isCourierCreated);

        ValidatableResponse responseLoginCourierForID =
                courierHelper.loginCourier(new CourierCredentials()
                        .setCourierCredentials(courier.getLogin(), courier.getPassword()));
        courierId = responseLoginCourierForID.extract().path("id");
    }

    @Test
    @Description("Create courier with required fields (login, password)")
    public void createCourierLoginAndPassword() {
        Courier courier = Courier.getLoginAndPassword();
        ValidatableResponse response = new CourierHelper().createCourier(courier);

        int statusCode = response.extract().statusCode();
        boolean isCourierCreated = response.extract().path("ok");

        assertThat(statusCode, equalTo(SC_CREATED));
        assertTrue(isCourierCreated);

        ValidatableResponse responseLoginCourierForID =
                courierHelper.loginCourier(new CourierCredentials()
                        .setCourierCredentials(courier.getLogin(), courier.getPassword()));
        courierId = responseLoginCourierForID.extract().path("id");
    }

    @Test
    @Description("Create courier with only login")
    public void createCourierLoginOnly() {
        Courier courier = Courier.getLoginOnly();
        ValidatableResponse response = new CourierHelper().createCourier(courier);

        int statusCode = response.extract().statusCode();
        String actualErrorMessage = response.extract().path("message");
        String expectedErrorMessage = "Недостаточно данных для создания учетной записи";

        assertThat(statusCode, equalTo(SC_BAD_REQUEST));
        assertEquals("Фактическое сообщение в ответе отличается от ожидаемого", expectedErrorMessage,
                actualErrorMessage);
    }

    @Test
    @Description("Create courier with same login")
    public void createCourierSameLogin() {
        Courier courier = Courier.getLoginPasswordAndFirstName();
        new CourierHelper().createCourier(courier);
        ValidatableResponse response = new CourierHelper().createCourier(courier);

        int statusCode = response.extract().statusCode();
        String actualErrorMessage = response.extract().path("message");
        String expectedErrorMessage = "Этот логин уже используется. Попробуйте другой.";

        assertThat(statusCode, equalTo(SC_CONFLICT));
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