import helpers.CourierHelper;
import helpers.OrderHelper;
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
import pojo.order.Order;

import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AcceptOrderTests {

    private CourierHelper courierHelper;
    private OrderHelper orderHelper;
    private int courierId;
    private int orderId;
    private int orderTrackId;

    @Before
    @Step("setUp")
    public void setUp () {
        courierHelper = new CourierHelper();
        Courier courier = Courier.getRandom();
        courierHelper.createCourier(courier);
        ValidatableResponse responseLoginCourier =
                courierHelper.loginCourier(CourierCredentials.getCourierCredentials(courier));
        courierId = responseLoginCourier.extract().path("id");

        orderHelper = new OrderHelper();
        Order order = Order.getRandomOrder(List.of("BLACK"));
        ValidatableResponse responseCreateOrder = orderHelper.createOrder(order);
        responseCreateOrder.assertThat().statusCode(SC_CREATED);
        orderTrackId = responseCreateOrder.extract().path("track");

        ValidatableResponse responseOrderByTrackId = orderHelper.getOrderByTrackNumber(orderTrackId);
        responseOrderByTrackId.assertThat().statusCode(SC_OK);
        orderId = responseOrderByTrackId.extract().body().path("order.id");

        // Проброс запросов и ответов в консоль, аналог log().all()
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    @Description("Accept order")
    public void acceptOrder() {
        ValidatableResponse response = orderHelper.acceptOrder(courierId, orderId);

        boolean isOrderAccepted = response.extract().path("ok");
        assertTrue(isOrderAccepted);
        response.assertThat().statusCode(SC_OK);
    }

    @Test
    @Description("Accept order without courierId")
    public void acceptOrderWithoutCourierId() {
        ValidatableResponse response = orderHelper.acceptOrder("", orderId);

        String actualErrorMessage = response.extract().path("message");
        String expectedErrorMessage = "Недостаточно данных для поиска";

        response.assertThat().statusCode(SC_BAD_REQUEST);
        assertEquals("Фактическое сообщение в ответе отличается от ожидаемого", expectedErrorMessage,
                actualErrorMessage);
    }

    @Test
    @Description("Accept order wrong courierId")
    public void acceptOrderWrongCourierId() {
        ValidatableResponse response = orderHelper.acceptOrder(0, orderId);

        String actualErrorMessage = response.extract().path("message");
        String expectedErrorMessage = "Курьера с таким id не существует";

        response.assertThat().statusCode(SC_NOT_FOUND);
        assertEquals("Фактическое сообщение в ответе отличается от ожидаемого", expectedErrorMessage,
                actualErrorMessage);
    }

    @Test
    @Description("Accept order without orderId")
    public void acceptOrderWithoutOrderId() {
        ValidatableResponse response = orderHelper.acceptOrder(courierId, "");

        String actualErrorMessage = response.extract().path("message");
        String expectedErrorMessage = "Недостаточно данных для поиска";

        response.assertThat().statusCode(SC_BAD_REQUEST);
        assertEquals("Фактическое сообщение в ответе отличается от ожидаемого", expectedErrorMessage,
                actualErrorMessage);
    }

    @Test
    @Description("Accept order wrong orderId")
    public void acceptOrderWrongOrderId() {
        ValidatableResponse response = orderHelper.acceptOrder(courierId, 0);

        String actualErrorMessage = response.extract().path("message");
        String expectedErrorMessage = "Заказа с таким id не существует";

        response.assertThat().statusCode(SC_NOT_FOUND);
        assertEquals("Фактическое сообщение в ответе отличается от ожидаемого", expectedErrorMessage,
                actualErrorMessage);
    }

    @After
    @Step("Teardown - delete courier")
    public void tearDown() {
        if (courierId != 0)
            courierHelper.deleteCourier(courierId);
    }
}