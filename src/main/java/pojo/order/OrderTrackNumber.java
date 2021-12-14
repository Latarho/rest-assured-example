package pojo.order;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
// Cериализация
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

public class OrderTrackNumber {
    private int track;

    public OrderTrackNumber setOrderTrackNumber(int track) {
        this.track = track;
        return this;
    }
}