package api.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse {
    public Integer code;
    public String type;
    public String message;
}
