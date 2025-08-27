package com.hyvercode.springday.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.validation.FieldError;
import org.webjars.NotFoundException;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final String DEFAULT_ERROR_CODE = "80";

  @ExceptionHandler(BusinessException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
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

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorResponse> renderEntityNotFoundExceptionErrorResponse(EntityNotFoundException exception) {
    log.error("(EntityNotFoundException occurred: ", exception);

    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode(DEFAULT_ERROR_CODE);
    errorResponse.setMessage(exception.getMessage());

    return getErrorResponseResponseEntity(HttpStatus.BAD_REQUEST, errorResponse);
  }

  @ExceptionHandler( value = DataIntegrityViolationException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ResponseEntity<ErrorResponse> renderDataIntegrityViolationExceptionResponse(DataIntegrityViolationException exception) {
    log.error("Violates foreign key constraint occurred: ", exception);
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode(DEFAULT_ERROR_CODE);
    errorResponse.setMessage("Violates foreign key constraint");

    return getErrorResponseResponseEntity(HttpStatus.CONFLICT, errorResponse);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorResponse> renderMethodArgumentErrorResponse(MethodArgumentNotValidException exception) {
    log.error("MethodArgumentNotValidException occurred: {} ", exception.getMessage());
    List<ErrorValidationResponse> baseValidationResponses = new ArrayList<>();
    for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
      baseValidationResponses.add(
        ErrorValidationResponse
          .builder()
          .propertyName(fieldError.getField())
          .errorMessage(fieldError.getDefaultMessage())
          .build()
      );
    }
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode(DEFAULT_ERROR_CODE);
    errorResponse.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
    errorResponse.setValidations(baseValidationResponses);
    return getErrorResponseResponseEntity(HttpStatus.BAD_REQUEST, errorResponse);
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
