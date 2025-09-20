package api.steps;

import api.models.ApiResponse;
import api.utils.RequestException;
import io.qameta.allure.Step;
import org.assertj.core.api.AssertionsForClassTypes;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DeleteOrderSteps extends OrderSteps {

    @Step("Delete order with ID: {id}")
    public ApiResponse deleteOrder(Integer id) {
        return storeService.deleteOrder(id);
    }

    @Step("Verify response of successfully Delete order ")
    public void verifyDeleteOrderSuccessfully(ApiResponse response) {
        assertThat(response.code)
                .as("Order should be deleted")
                .isEqualTo(200);
    }

    @Step("Verify response of Delete not existed order")
    public void verifyDeleteNotExistedOrder(Integer id) {
        AssertionsForClassTypes.assertThatThrownBy(() ->  storeService.deleteOrder(id))
                .isInstanceOf(RequestException.class)
                .hasMessage("404\n{\"code\":404,\"type\":\"error\",\"message\":\"Order not found\"}");
    }

    @Step("Verify response of Delete order with invalid ID")
    public void verifyDeleteOrderByNotValidId(Integer id) {
        AssertionsForClassTypes.assertThatThrownBy(() ->  storeService.deleteOrder(id))
                .isInstanceOf(RequestException.class)
                .hasMessage("400\n{\"code\":400,\"type\":\"error\",\"message\":\"Invalid ID supplied\"}");
    }
}
