package common;

public class EndPoints {
    // Base URI
    public static final String BASE_URI = "https://qa-scooter.praktikum-services.ru";

    // Courier - Create Courier
    public static final String CREATE_COURIER = "/api/v1/courier";

    // Courier - Login Courier
    public static final String LOGIN_COURIER = "/api/v1/courier/login";

    // Courier - Delete Courier
    public static final String DELETE_COURIER = "/api/v1/courier/";

    // Order - Create Order
    public static final String CREATE_ORDER = "/api/v1/orders";

    // Order - Cancel Order
    public static final String CANCEL_ORDER = "/api/v1/orders/cancel";

    // Order - Accept Order
    public static final String ACCEPT_ORDER = "/api/v1/orders/accept/";

    // Order - Get Orders List
    public static final String GET_ORDERS = "/api/v1/orders/";

    // Order - Get Order By Track ID
    public static final String GET_ORDER_BY_TRACK_ID = "/api/v1/orders/track";
}