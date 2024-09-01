package com.sfjs;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
    ErrorResponse errorResponse = new ExceptionErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  // Add more specific exception handlers as needed

  private class ExceptionErrorResponse implements ErrorResponse {

    HttpStatusCode status;
    String detail;

    ExceptionErrorResponse(HttpStatusCode status, String detail) {
      this.status = status;
      this.detail = detail;
    }

    @Override
    public HttpStatusCode getStatusCode() {
      return status;
    }

    @Override
    public ProblemDetail getBody() {
      return ProblemDetail.forStatusAndDetail(status, detail);
    }

  }
}
