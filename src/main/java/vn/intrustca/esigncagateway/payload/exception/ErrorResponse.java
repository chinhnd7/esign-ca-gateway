package vn.intrustca.esigncagateway.payload.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private int responseCode;
    private String responseMessage;

    public ErrorResponse(String responseMessage)
    {
        super();
        this.responseMessage = responseMessage;
    }
}
