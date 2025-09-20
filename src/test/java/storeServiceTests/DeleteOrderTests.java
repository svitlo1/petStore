package storeServiceTests;

import api.models.ApiResponse;
import api.models.Order;
import api.steps.CreateOrderSteps;
import api.steps.DeleteOrderSteps;
import api.steps.GetOrderSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@Feature("DELETE /v2/store/order/{id} API")
@ExtendWith(AllureJunit5.class)
public class DeleteOrderTests {

    private static final CreateOrderSteps createOrderSteps = new CreateOrderSteps();
    private static final DeleteOrderSteps deleteOrderSteps = new DeleteOrderSteps();
    private final GetOrderSteps getOrderSteps = new GetOrderSteps();
    private static Order createOrderResponse;


    @BeforeAll
    static void setUp() {
        System.out.println("This runs before all test");
        Order createOrderRequest = createOrderSteps.buildDefaultCreateOrderRequest(2);
        createOrderResponse = createOrderSteps.createOrder(createOrderRequest);
    }

    @Test
    @Description("Verify success DELETE /v2/store/order/{id} by ID")
    public void testSuccessDeleteOrderByID() {
        var id = createOrderResponse.getId();

        ApiResponse deleteOrderResponse = deleteOrderSteps.deleteOrder(id);

        deleteOrderSteps.verifyDeleteOrderSuccessfully(deleteOrderResponse);
    }

    @Test
    @Description("Verify failed DELETE /v2/store/order/{id} by not existed order ID")
    public void testDeleteOrderByNotExistedID() {

        deleteOrderSteps.verifyDeleteNotExistedOrder(0);
    }

    @Test
    @Description("Verify failed DELETE /v2/store/order/{id} by not valid ID")
    public void testDeleteOrderByNotValidID() {

        deleteOrderSteps.verifyDeleteOrderByNotValidId(-1);
    }
}
