import common.EndPoints;
import helpers.CourierHelper;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import pojo.courier.Courier;
import pojo.courier.CourierCredentials;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class DeleteCourierTests {
    private CourierHelper courierHelper;

    @Before
    @Step("setUp")
    public void setUp() {
        RestAssured.baseURI = EndPoints.BASE_URI;
        courierHelper = new CourierHelper();
        // Проброс запросов и ответов в консоль, аналог log().all()
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    @Description("Delete courier")
    public void deleteCourier() {
        Courier courier = Courier.getLoginPasswordAndFirstName();
        courierHelper.createCourier(courier);
        ValidatableResponse responseLoginCourier =
                courierHelper.loginCourier(new CourierCredentials()
                        .setCourierCredentials(courier.getLogin(), courier.getPassword()));

        int courierId = responseLoginCourier.extract().path("id");

        ValidatableResponse responseDeleteCourier = courierHelper.deleteCourierById(courierId);
        boolean isCourierDeleted = responseDeleteCourier.extract().path("ok");

        responseDeleteCourier.assertThat().statusCode(SC_OK);
        assertTrue(isCourierDeleted);
    }

    @Test
    @Description("Delete courier without id")
    public void deleteCourierWithoutId() {
        ValidatableResponse responseDeleteCourier =
                courierHelper.deleteCourierWithoutId();

        String actualErrorMessage = responseDeleteCourier.extract().path("message");
        String expectedErrorMessage = "Недостаточно данных для удаления курьера";

        responseDeleteCourier.assertThat().statusCode(SC_BAD_REQUEST);
        assertEquals("Фактическое сообщение в ответе отличается от ожидаемого", expectedErrorMessage,
                actualErrorMessage);
    }

    @Test
    @Description("Delete courier with wrong id")
    public void deleteCourierWrongId() {
        int wrongCourierId = 111111111;
        ValidatableResponse responseDeleteCourier = courierHelper.deleteCourierById(wrongCourierId);

        String actualErrorMessage = responseDeleteCourier.extract().path("message");
        String expectedErrorMessage = "Курьера с таким id нет.";

        responseDeleteCourier.assertThat().statusCode(SC_NOT_FOUND);
        assertEquals("Фактическое сообщение в ответе отличается от ожидаемого", expectedErrorMessage,
                actualErrorMessage);

    }
}