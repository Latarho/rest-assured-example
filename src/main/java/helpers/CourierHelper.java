package helpers;

import common.EndPoints;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import pojo.courier.Courier;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import pojo.courier.CourierCredentials;

import static io.restassured.RestAssured.given;

public class CourierHelper {
    @Step("Create courier")
    public ValidatableResponse createCourier(Courier courier) {
        return given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .and()
                .body(courier)
                .when()
                .post(EndPoints.CREATE_COURIER)
                .then();
    }

    @Step("Login courier")
    public ValidatableResponse loginCourier(CourierCredentials credentials) {
        return given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .and()
                .body(credentials)
                .when()
                .post(EndPoints.LOGIN_COURIER)
                .then();
    }

    @Step("Delete courier by Id")
    public ValidatableResponse deleteCourierById(int courierId) {
        return given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .when()
                .delete(EndPoints.DELETE_COURIER + courierId)
                .then();
    }

    @Step("Delete courier without Id")
    public ValidatableResponse deleteCourierWithoutId() {
        return given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .when()
                .delete(EndPoints.DELETE_COURIER)
                .then();
    }
}