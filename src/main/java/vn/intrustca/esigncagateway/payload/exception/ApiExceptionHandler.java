package vn.intrustca.esigncagateway.payload.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.intrustca.esigncagateway.utils.MessageUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ServiceExceptionResponse> handleServiceException(ServiceException ex) {
        return new ResponseEntity<>(new ServiceExceptionResponse(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ AppException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFoundException(Exception e, HttpServletResponse response) throws IOException {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("responseMessage", "Cert Not Found");
        errorDetails.put("responseCode", 404);
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(), errorDetails);
    }

    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<ErrorMessage> handleBusinessException(BusinessException ex) {
        log.error("error occurred.", ex);
        ErrorMessage errorMessage = ErrorMessage
                .builder()
                .responseCode(ex.getExceptionCode().getCode())
                .responseMessage(MessageUtil.getMessage(ex.getExceptionCode().getMessage()))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<ErrorMessage> handleValidationException(ValidationException ex) {
        log.error("error occurred.", ex);
        ErrorMessage errorMessage = ErrorMessage
                .builder()
                .responseCode(ExceptionCode.INVALID_DOCUMENT.getCode())
                .responseMessage(getValidationMessage(ex))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    private String getValidationMessage(ValidationException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        if (!CollectionUtils.isEmpty(fieldErrors)) {
            return fieldErrors.get(0).getDefaultMessage();
        }
        return null;
    }

//    @ExceptionHandler(value = {JwtExpiredTokenException.class})
//    protected ErrorResponse handleJwtExpiredTokenException(JwtExpiredTokenException ex) {
//        return new ErrorResponse(HttpStatus.UNAUTHORIZED.value(),
//                ex.getMessage());
//    }
}

