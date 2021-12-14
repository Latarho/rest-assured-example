package pojo.order;

import data.OrderDataGenerator;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
// Cериализация
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

public class Order {
    private String firstName;
    private String lastName;
    private String address;
    private int metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;

    public Order setOrderData(String firstName, String lastName, String address, int metroStation, String phone,
                              int rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
        return this;
    }

    public static Order getOrder(String colorVariable) {
        return new Order().setOrderData(OrderDataGenerator.getFirstName(),
                OrderDataGenerator.getLastName(),
                OrderDataGenerator.getAddress(),
                OrderDataGenerator.getMetroStation(),
                OrderDataGenerator.getPhone(),
                OrderDataGenerator.getRentTime(),
                OrderDataGenerator.getDeliveryDate(),
                OrderDataGenerator.getComment(),
                new String[]{colorVariable});
    }
}