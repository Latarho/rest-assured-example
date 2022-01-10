package common;

public class EndPoints {
    // Base URI
    public static final String BASE_URI = "https://qa-scooter.praktikum-services.ru/api/v1";

    // Courier - Create Courier
    public static final String CREATE_COURIER = "/courier";

    // Courier - Login Courier
    public static final String LOGIN_COURIER = "/courier/login";

    // Courier - Delete Courier
    public static final String DELETE_COURIER = "/courier/";

    // Order - Create Order
    public static final String CREATE_ORDER = "/orders";

    // Order - Cancel Order
    public static final String CANCEL_ORDER = "/orders/cancel";

    // Order - Accept Order
    public static final String ACCEPT_ORDER = "/orders/accept/";

    // Order - Get Orders List
    public static final String GET_ORDERS = "/orders/";

    // Order - Get Order By Track ID
    public static final String GET_ORDER_BY_TRACK = "/orders/track";
}