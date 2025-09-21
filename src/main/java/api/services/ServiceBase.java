package api.services;

import api.utils.RequestException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import config.EnvironmentLoader;
import config.EnvironmentProperties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Slf4j
public class ServiceBase {
    protected Retrofit retrofit;
    protected static Gson gson;

    public ServiceBase(){
        EnvironmentProperties env = EnvironmentLoader.loadActiveEnvironment();
        String baseUrl = env.getBaseUrl();

        gson = new GsonBuilder()
                .enableComplexMapKeySerialization()
                .serializeNulls()
                .setPrettyPrinting()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @SneakyThrows
    static <T> T triggerApiCall(Call<T> responseBodyCall, String serviceName) {
        Response<T> response = responseBodyCall.execute();

        if (response.isSuccessful()) {
            log.info("Service {} invoked successfully", serviceName);
            log.info("Response: {}", response);
            if (response.body() != null) {
                log.info("Body: {}", response.body());
            }
            return response.body();
        } else {
            log.error("Service {} returned error response: {}", serviceName, response);
            throw new RequestException(response);
        }
    }

}
