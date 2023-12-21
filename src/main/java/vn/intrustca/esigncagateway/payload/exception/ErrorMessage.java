package vn.intrustca.esigncagateway.payload.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorMessage<T> {
    private String status_code;
    private String message;
}
