package api.utils;

import retrofit2.Response;

import java.io.IOException;

public class RequestException extends RuntimeException {
    public RequestException(String message) {
        super(message);
    }

    public RequestException(String status, String message) {
        this(String.format("%s\r\n%s", status, message));
    }

    public RequestException(Response response) throws IOException {
        this(String.valueOf(response.code()), response.errorBody().string());
    }
}
