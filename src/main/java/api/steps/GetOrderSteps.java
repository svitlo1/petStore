package api.steps;

import api.models.Order;
import api.utils.RequestException;
import io.qameta.allure.Step;
import org.assertj.core.api.AssertionsForClassTypes;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GetOrderSteps extends OrderSteps {

    @Step("Fetch order details from GET /v2/store/order/{id}")
    public Order getOrderById(Integer id) {
        return storeService.getOrder(id);
    }

    @Step("Assert that order ID in response matches requested ID")
    public void assertIdMatches(Order orderResponse, Integer expectedId) {
        assertThat(orderResponse.getId())
                .as("Id value from response should be the same as in request")
                .isEqualTo(expectedId);
    }

    @Step("Verify response of Get not existed order")
    public void verifyGetNotExistedOrder(Integer id) {
        AssertionsForClassTypes.assertThatThrownBy(() ->  storeService.getOrderFails(id))
                .isInstanceOf(RequestException.class)
                .hasMessage("404\n{\"code\":404,\"type\":\"error\",\"message\":\"Order not found\"}");
    }

    @Step("Verify response of Get order by not valid ID")
    public void verifyGetOrderByNotValidId(Integer id) {

        AssertionsForClassTypes.assertThatThrownBy(() ->  storeService.getOrderFails(id))
                .isInstanceOf(RequestException.class)
                .hasMessage("400\n{\"code\":400,\"type\":\"error\",\"message\":\"Invalid ID supplied\"}");
    }
}
