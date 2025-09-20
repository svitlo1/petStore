package api.models;

import api.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order {
    public Integer id;
    public Integer petId;
    public Integer quantity;
    public String shipDate;
    public OrderStatus status;
    public Boolean complete;
}
