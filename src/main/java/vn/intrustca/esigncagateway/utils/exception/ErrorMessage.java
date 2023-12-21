package vn.intrustca.esigncagateway.utils.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorMessage<T> {
    private String responseCode;
    private String responseMessage;
    private T data;

}
