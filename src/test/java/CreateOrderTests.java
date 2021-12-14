import common.EndPoints;
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
import pojo.order.OrderTrackNumberString;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTests {
    public final String colorVariable;

    public CreateOrderTests(String colorVariable) {
        this.colorVariable = colorVariable;
    }

    @Before
    @Step("setUp")
    public void setUp() {
        RestAssured.baseURI = EndPoints.BASE_URI;
        RestAssured.filters(new RequestLoggingFilter(), new RequestLoggingFilter());
    }

    @Parameterized.Parameters
    public static Object[] getColor() {
        return new Object[][] {{"GREY\" , \"BLACK"}, {"GREY"}, {"BLACK"}, {""}};
    }

    @Test
    @Description("Create order")
    public void createOrder() {
        OrderHelper orderHelper = new OrderHelper();
        Order order = Order.getOrder(colorVariable);
        ValidatableResponse responseCreateOrder = orderHelper.createOrder(order);

        int orderTrack = responseCreateOrder.extract().path("track");
        String orderTrackString = String.valueOf(orderTrack);

        responseCreateOrder.assertThat().statusCode(SC_CREATED);
        assertThat("Номер заказа не присвоен", orderTrack, notNullValue());

//        ValidatableResponse responseCancelOrder =
//                orderHelper.cancelOrderString(new OrderTrackNumberString()
//                                .setOrderTrackNumberString(orderTrackString));
//       responseCancelOrder.assertThat().statusCode(SC_OK);
    }
}