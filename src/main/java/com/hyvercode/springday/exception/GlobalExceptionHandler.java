package com.hyvercode.springday.exception;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Objects;
import java.util.concurrent.TimeoutException;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final String DEFAULT_ERROR_CODE = "80000";

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> renderDefaultResponse(Exception ex) {
    log.error("Exception occurred: ", ex);
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode(DEFAULT_ERROR_CODE);
    errorResponse.setMessage("Internal server error");

    return getErrorResponseResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, errorResponse);
  }


  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ErrorResponse> renderBusinessErrorResponse(BusinessException exception) {
    log.error("BusinessException occurred: ", exception);
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setTitle("Error Business Exception");
    errorResponse.setCode(exception.errorCode);
    errorResponse.setMessage(exception.getMessage());

    return getErrorResponseResponseEntity(exception.getHttpStatus(), errorResponse);
  }

  @ExceptionHandler(HttpServerErrorException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public ResponseEntity<ErrorResponse> renderHttpServerErrorResponse(HttpServerErrorException exception) {
    log.error("HttpServerErrorException occurred: ", exception);

    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode(DEFAULT_ERROR_CODE);
    errorResponse.setMessage("Internal server error");

    return getErrorResponseResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, errorResponse);
  }

  @ExceptionHandler(TimeoutException.class)
  public ResponseEntity<ErrorResponse> renderTimeoutResponse(TimeoutException exception) {
    log.error("TimeoutException occurred: ", exception);

    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode(DEFAULT_ERROR_CODE);
    errorResponse.setMessage("Timeout Exception");

    return getErrorResponseResponseEntity(HttpStatus.REQUEST_TIMEOUT, errorResponse);
  }

  private ResponseEntity<ErrorResponse> getErrorResponseResponseEntity(HttpStatus httpStatus,
                                                                       ErrorResponse errorResponse) {


    if (errorResponse != null && errorResponse.getTitle() == null) {
      errorResponse.setTitle("System Error");
      return new ResponseEntity<>(errorResponse, new HttpHeaders(), httpStatus);
    }

    return new ResponseEntity<>(errorResponse, new HttpHeaders(), httpStatus);
  }
}
