package pojo.order;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
// Cериализация
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

public class OrderTrackNumberString {
    private String track;

    public OrderTrackNumberString setOrderTrackNumberString(String track) {
        this.track = track;
        return this;
    }
}