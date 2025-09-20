package api.interfaces;

import api.models.ApiResponse;
import api.models.Order;
import retrofit2.Call;
import retrofit2.http.*;

public interface StoreApi {
    @GET("/v2/store/order/{id}")
    Call<Order> getOrder(
            @Path("id") Integer id
    );

    @GET("/v2/store/order/{id}")
    Call<ApiResponse> getOrderFails(
            @Path("id") Integer id
    );

    @POST("/v2/store/order")
    Call<Order> createOrder(
            @Body Order request
    );

    @POST("/v2/store/order")
    Call<ApiResponse> createOrderFails(
            @Body Order request
    );

    @DELETE("/v2/store/order/{id}")
    Call<ApiResponse> deleteOrder(
            @Path("id") Integer id
    );
}
