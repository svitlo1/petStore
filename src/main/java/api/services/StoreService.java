package api.services;

import api.interfaces.StoreApi;
import api.models.ApiResponse;
import api.models.Order;
import retrofit2.Call;

public class StoreService extends ServiceBase {
    private StoreApi storeApi;
    private static final String SERVICE_NAME = "StoreService";

    public StoreService() {
        super();
        storeApi = retrofit.create(StoreApi.class);
    }

    private <T> T execute(Call<T> responseBodyCall) {
        return triggerApiCall(responseBodyCall, SERVICE_NAME);
    }

    public Order getOrder(Integer id) {
        return execute(storeApi.getOrder(id));
    }

    public ApiResponse getOrderFails(Integer id) {
        return execute(storeApi.getOrderFails(id));
    }

    public Order createOrder(Order request) {
        return execute(storeApi.createOrder(request));
    }

    public ApiResponse createOrderFails(Order request) {
        return execute(storeApi.createOrderFails(request));
    }

    public ApiResponse deleteOrder(Integer id) {
        return execute(storeApi.deleteOrder(id));
    }
}
