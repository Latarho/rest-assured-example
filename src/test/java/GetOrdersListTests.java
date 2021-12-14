import common.EndPoints;
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

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertFalse;

public class GetOrdersListTests {
    private CourierHelper courierHelper;
    private OrderHelper orderHelper;
    private int courierId;
    private int orderId;
    private int orderTrackId;
    String color = "BLACK";

    @Before
    @Step("setUp")
    public void setUp () {
        RestAssured.baseURI = EndPoints.BASE_URI;
        courierHelper = new CourierHelper();
        orderHelper = new OrderHelper();
        // Проброс запросов и ответов в консоль, аналог log().all()
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    @Description("Get orders list test")
    public void getOrdersList() {
        Courier courier = Courier.getLoginPasswordAndFirstName();
        courierHelper.createCourier(courier);
        ValidatableResponse responseLoginCourier =
                courierHelper.loginCourier(new CourierCredentials()
                        .setCourierCredentials(courier.getLogin(), courier.getPassword()));
        courierId = responseLoginCourier.extract().path("id");

        Order order = Order.getOrder(color);
        ValidatableResponse responseCreateOrder = orderHelper.createOrder(order);
        responseCreateOrder.assertThat().statusCode(SC_CREATED);
        orderTrackId = responseCreateOrder.extract().path("track");

        ValidatableResponse responseOrderByTrackId = orderHelper.getOrderByTrackId(orderTrackId);
        responseOrderByTrackId.assertThat().statusCode(SC_OK);
        orderId = responseOrderByTrackId.extract().body().path("order.id");

        ValidatableResponse responseAcceptOrder = orderHelper.acceptOrder(courierId, orderId);
        responseAcceptOrder.assertThat().statusCode(SC_OK);

        ValidatableResponse responseGetOrder = orderHelper.getOrdersList(courierId);
        responseGetOrder.assertThat().statusCode(SC_OK);
        List<Object> orders = responseGetOrder.extract().jsonPath().getList("orders");
        assertFalse(orders.isEmpty());
    }

    @After
    @Step("Teardown - delete courier")
    public void tearDown() {
        if (courierId != 0)
            courierHelper.deleteCourierById(courierId);
    }
}