package helpers;

import common.EndPoints;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import pojo.order.Order;
import pojo.order.OrderTrackNumber;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import pojo.order.OrderTrackNumberString;

import static io.restassured.RestAssured.given;

public class OrderHelper {
    @Step("Create order")
    public ValidatableResponse createOrder(Order order) {
        return given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .and()
                .body(order)
                .when()
                .post(EndPoints.CREATE_ORDER)
                .then();
    }

    @Step("Cancel order string format")
    public ValidatableResponse cancelOrderString(OrderTrackNumberString orderTrackNumberString) {
        return given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .and()
                .body(orderTrackNumberString)
                .when()
                .put(EndPoints.CANCEL_ORDER)
                .then();
    }

    @Step("Cancel order")
    public ValidatableResponse cancelOrder(OrderTrackNumber orderTrackNumber) {
        return given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .and()
                .body(orderTrackNumber)
                .when()
                .put(EndPoints.CANCEL_ORDER)
                .then();
    }

    @Step("Get orders list")
    public ValidatableResponse getOrdersList(int courierId) {
        return given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .and()
                .queryParam("courierId", courierId)
                .get(EndPoints.GET_ORDERS)
                .then();
    }

    @Step("Get order by trackId")
    public ValidatableResponse getOrderByTrackId(int firstOrderTrackId) {
        return given()
                .filter(new AllureRestAssured())
                .when()
                .queryParam("t", firstOrderTrackId)
                .get(EndPoints.GET_ORDER_BY_TRACK_ID)
                .then();
    }

    @Step("Accept order")
    public ValidatableResponse acceptOrder(int courierId, int orderId) {
        return given()
                .filter(new AllureRestAssured())
                .when()
                .queryParam("courierId", courierId)
                .put(EndPoints.ACCEPT_ORDER + orderId)
                .then();
    }
}