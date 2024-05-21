package site.strangebros.nork.global.web.handler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import site.strangebros.nork.global.web.dto.response.ExceptionResponse;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({
            IllegalArgumentException.class,
            ServletRequestBindingException.class,
            HttpMessageNotReadableException.class,
            MissingServletRequestPartException.class,
            MissingServletRequestParameterException.class,
            TypeMismatchException.class,
            BindException.class,
    })
    public ResponseEntity<ExceptionResponse> badRequestHandler(Exception exception) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(buildResponse(BAD_REQUEST, exception));
    }

    @ExceptionHandler({
            NoHandlerFoundException.class,
            NoResourceFoundException.class
    })
    public ResponseEntity<ExceptionResponse> notFoundExceptionHandler(Exception exception) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(buildResponse(NOT_FOUND, exception));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionResponse> methodNotAllowedExceptionHandler(
            HttpRequestMethodNotSupportedException exception) {
        return ResponseEntity
                .status(METHOD_NOT_ALLOWED)
                .body(buildResponse(METHOD_NOT_ALLOWED, exception));
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ExceptionResponse> notAcceptableExceptionHandler(Exception exception) {
        return ResponseEntity
                .status(NOT_ACCEPTABLE)
                .body(buildResponse(NOT_ACCEPTABLE, exception));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ExceptionResponse> unSupportedMediaTypeExceptionHandler(Exception exception) {
        return ResponseEntity
                .status(UNSUPPORTED_MEDIA_TYPE)
                .body(buildResponse(UNSUPPORTED_MEDIA_TYPE, exception));
    }

    @ExceptionHandler({
            MissingPathVariableException.class,
            ConversionNotSupportedException.class,
            HttpMessageNotWritableException.class,
    })
    public ResponseEntity<ExceptionResponse> internalServerErrorExceptionHandler(Exception exception) {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(buildResponse(INTERNAL_SERVER_ERROR, exception));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> globalExceptionHandler(Exception exception) {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(buildResponse(INTERNAL_SERVER_ERROR, exception));
    }

    private ExceptionResponse buildResponse(HttpStatus status, Exception exception) {
        return ExceptionResponse.builder()
                .status(status.value())
                .message(exception.getLocalizedMessage())
                .build();
    }

    // Not Null 인 값 안 넣었을 때, 설정한 메시지만 던지도록 함.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> validationFailedExceptionHandler(MethodArgumentNotValidException exception) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(buildValidationFailedResponse(BAD_REQUEST, exception));
    }

    // Not Null 인 값 안 넣었을 때, 설정한 메시지만 던지도록 함.
    private ExceptionResponse buildValidationFailedResponse(HttpStatus status, MethodArgumentNotValidException exception) {
        return ExceptionResponse.builder()
                .status(status.value())
                .message(exception.getFieldErrors().get(0).getDefaultMessage())
                .build();
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ExceptionResponse> handleMaxSizeException(MaxUploadSizeExceededException exception) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(buildResponse(BAD_REQUEST, exception));
    }

}
