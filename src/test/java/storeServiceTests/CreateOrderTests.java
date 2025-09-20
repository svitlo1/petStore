package storeServiceTests;

import api.models.Order;
import api.steps.CreateOrderSteps;
import api.steps.DeleteOrderSteps;
import api.steps.GetOrderSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@Feature("POST /v2/store/order API")
@ExtendWith(AllureJunit5.class)
public class CreateOrderTests {

    private static final CreateOrderSteps createOrderSteps = new CreateOrderSteps();
    private static final DeleteOrderSteps deleteOrderSteps = new DeleteOrderSteps();
    private final GetOrderSteps getOrderSteps = new GetOrderSteps();
    private static Order createOrderResponse;

    @Test
    @Description("Verify success POST /v2/store/order API")
    public void testSuccessCreateOrder() {

        Order createOrderRequest = createOrderSteps.buildDefaultCreateOrderRequest(2);
        createOrderResponse = createOrderSteps.createOrder(createOrderRequest);

        var id = createOrderResponse.getId();

        Order getOrderResponse = getOrderSteps.getOrderById(id);
        getOrderSteps.assertIdMatches(getOrderResponse, id);
    }

    @Test
    @Description("Verify failed POST /v2/store/order API")
    public void testFailedCreateOrder() {
        Order createOrderRequest = createOrderSteps.buildNotValidCreateOrderRequest();

        createOrderSteps.verifyFailedOrder(createOrderRequest);
    }
}
