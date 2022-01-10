package pojo.order;

import java.util.List;

import static data.OrderDataGenerator.*;


public class Order {
    public String firstName;
    public String lastName;
    public String address;
    public int metroStation;
    public String phone;
    public int rentTime;
    public String deliveryDate;
    public String comment;
    public List<String> color;

    public Order (String firstName, String lastName, String address, int metroStation, String phone,
                  int rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    public static Order getRandomOrder(List<String> colorVariable) {
        return new Order(generateFirstName(),
                generateLastName(),
                generateAddress(),
                generateMetroStation(),
                generatePhone(),
                generateRentTime(),
                generateDeliveryDate(),
                generateComment(),
                colorVariable);
    }
}