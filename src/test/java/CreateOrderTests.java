import helpers.OrderHelper;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import pojo.order.Order;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class CreateOrderTests {

    private final List<String> colorVariable;

    public CreateOrderTests(List<String> colorVariable) {
        this.colorVariable = colorVariable;
    }

    @Before
    @Step("setUp")
    public void setUp() {
        RestAssured.filters(new RequestLoggingFilter(), new RequestLoggingFilter());
    }

    @Parameterized.Parameters
    public static Object[] getColor() {
        return new Object[][] {
                {List.of("GREY", "BLACK")},
                {List.of("GREY")},
                {List.of("BLACK")},
                {List.of("")},
                {null},
        };
    }

    @Test
    @Description("Create order")
    public void createOrder() {
        OrderHelper orderHelper = new OrderHelper();
        Order order = Order.getRandomOrder(colorVariable);
        ValidatableResponse responseCreateOrder = orderHelper.createOrder(order);

        int orderTrack = responseCreateOrder.extract().path("track");

        responseCreateOrder.assertThat().statusCode(SC_CREATED);
        assertThat("Номер заказа не присвоен", orderTrack, notNullValue());
    }
}