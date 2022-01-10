package helpers;

import common.EndPoints;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.ValidatableResponse;
import pojo.order.Order;

import static io.restassured.RestAssured.given;

public class OrderHelper extends RestAssuredHelper {
    @Step("Create order")
    public ValidatableResponse createOrder(Order order) {
        return given()
                .filter(new AllureRestAssured())
                .spec(getBaseSpec())
                .and()
                .body(order)
                .when()
                .post(EndPoints.CREATE_ORDER)
                .then();
    }

    @Step("Get orders list")
    public ValidatableResponse getOrdersList(int courierId) {
        return given()
                .filter(new AllureRestAssured())
                .spec(getBaseSpec())
                .and()
                .queryParam("courierId", courierId)
                .get(EndPoints.GET_ORDERS)
                .then();
    }

    @Step("Get order by track number")
    public ValidatableResponse getOrderByTrackNumber(Object trackNumber) {
        return given()
                .filter(new AllureRestAssured())
                .spec(getBaseSpec())
                .and()
                .queryParam("t", trackNumber)
                .get(EndPoints.GET_ORDER_BY_TRACK)
                .then();
    }

    @Step("Accept order")
    public ValidatableResponse acceptOrder(Object courierId, Object orderId) {
        return given()
                .filter(new AllureRestAssured())
                .spec(getBaseSpec())
                .and()
                .queryParam("courierId", courierId)
                .put(EndPoints.ACCEPT_ORDER + orderId)
                .then();
    }
}