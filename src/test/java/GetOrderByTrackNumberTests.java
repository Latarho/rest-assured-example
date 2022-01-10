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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;

public class GetOrderByTrackNumberTests {
    private CourierHelper courierHelper;
    private OrderHelper orderHelper;
    private int courierId;
    private int orderId;
    private int orderTrack;

    @Before
    @Step("setUp")
    public void setUp () {
        courierHelper = new CourierHelper();
        orderHelper = new OrderHelper();
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    @Description("Get order by track number")
    public void getOrderByTrackNumber() {
        Courier courier = Courier.getRandom();
        courierHelper.createCourier(courier);
        ValidatableResponse responseLoginCourier =
                courierHelper.loginCourier(CourierCredentials.getCourierCredentials(courier));
        courierId = responseLoginCourier.extract().path("id");

        Order order = Order.getRandomOrder(List.of("GREY"));
        ValidatableResponse responseCreateOrder = orderHelper.createOrder(order);
        responseCreateOrder.assertThat().statusCode(SC_CREATED);
        orderTrack = responseCreateOrder.extract().path("track");

        ValidatableResponse responseOrderByTrackNumber = orderHelper.getOrderByTrackNumber(orderTrack);
        responseOrderByTrackNumber.assertThat().statusCode(SC_OK);

        orderId = responseOrderByTrackNumber.extract().body().path("order.id");
        assertThat("orderId is null", orderId, notNullValue());
    }

    @Test
    @Description("Get order without track number")
    public void getOrderWithoutTrackNumber() {
        String orderTrack = "";
        ValidatableResponse response = orderHelper.getOrderByTrackNumber(orderTrack);

        String actualErrorMessage = response.extract().path("message");
        String expectedErrorMessage = "Недостаточно данных для поиска";

        response.assertThat().statusCode(SC_BAD_REQUEST);
        assertEquals("Фактическое сообщение в ответе отличается от ожидаемого", expectedErrorMessage,
                actualErrorMessage);
    }

    @Test
    @Description("Get order with wrong track number")
    public void getOrderWrongTrackNumber() {
        ValidatableResponse response = orderHelper.getOrderByTrackNumber(0);

        String actualErrorMessage = response.extract().path("message");
        String expectedErrorMessage = "Заказ не найден";

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