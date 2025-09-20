package api.steps;

import api.enums.OrderStatus;
import api.models.ApiResponse;
import api.models.Order;
import api.utils.RequestException;
import io.qameta.allure.Step;
import org.assertj.core.api.AssertionsForClassTypes;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CreateOrderSteps extends OrderSteps {

    @Step("Build default Order creation request")
    public Order buildDefaultCreateOrderRequest(int id) {
        return Order.builder()
                .id(id)
                .petId(1)
                .quantity(1)
                .shipDate("2025-09-13T17:40:32.812Z")
                .status(OrderStatus.PLACED)
                .complete(true).build();
    }

    @Step("Build default Order creation request")
    public Order buildNotValidCreateOrderRequest() {
        return Order.builder()
                .petId(1)
                .quantity(1)
                .build();
    }

    @Step("Send POST request to create a order")
    public Order createOrder(Order createOrderRequest) {
        return storeService.createOrder(createOrderRequest);
    }

    @Step("Verify Status Code and message of Invalid Create Order")
    public void verifyFailedOrder(Order createOrderRequest) {
        AssertionsForClassTypes.assertThatThrownBy(() ->  storeService.createOrderFails(createOrderRequest))
                .isInstanceOf(RequestException.class)
                .hasMessage("400\n{\"code\":404,\"type\":\"error\",\"message\":\"Invalid Order\"}");
    }
}
