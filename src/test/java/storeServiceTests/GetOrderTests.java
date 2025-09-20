package storeServiceTests;

import api.models.ApiResponse;
import api.models.Order;
import api.steps.CreateOrderSteps;
import api.steps.GetOrderSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@Feature("GET /v2/store/order/{id} API")
@ExtendWith(AllureJunit5.class)
public class GetOrderTests {

    private static final CreateOrderSteps createOrderSteps = new CreateOrderSteps();
    private final GetOrderSteps getOrderSteps = new GetOrderSteps();
    private static Order createOrderResponse;


    @BeforeAll
    static void setUp() {
        System.out.println("This runs before all test");
        Order createOrderRequest = createOrderSteps.buildDefaultCreateOrderRequest(2);
        createOrderResponse = createOrderSteps.createOrder(createOrderRequest);
    }

    @Test
    @Description("Verify success GET /v2/store/order/{id} by ID")
    public void testSuccessGetOrderByID() {
        var id = createOrderResponse.getId();

        Order getOrderResponse = getOrderSteps.getOrderById(id);

        getOrderSteps.assertIdMatches(getOrderResponse, id);
    }

    @Test
    @Description("Verify failed GET /v2/store/order/{id} by not existed order ID")
    public void testGetOrderByNotExistedID() {
        getOrderSteps.verifyGetNotExistedOrder(12365);
    }

    @Test
    @Description("Verify failed GET /v2/store/order/{id} by not valid ID")
    public void testGetOrderByNotValidID() {
        getOrderSteps.verifyGetOrderByNotValidId(-1);
    }
}
