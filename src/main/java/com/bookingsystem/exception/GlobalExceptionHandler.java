package com.bookingsystem.exception;

import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ProblemDetail.forStatusAndDetail;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler
{
   @ExceptionHandler(PersistenceException.class)
   ProblemDetail handleProductNotFoundException(Exception ex)
   {
      ProblemDetail problemDetail = forStatusAndDetail(BAD_REQUEST, ex.getMessage());
      problemDetail.setTitle("Bad Request");
      problemDetail.setDetail(ex.getMessage());
      return problemDetail;
   }

   @ExceptionHandler({BookingNotFoundException.class, UnitNotFoundException.class})
   ProblemDetail handleNotFoundException(RuntimeException ex)
   {
      ProblemDetail problemDetail = forStatusAndDetail(NOT_FOUND, ex.getMessage());
      problemDetail.setDetail(ex.getMessage());
      problemDetail.setTitle("Not Found");
      return problemDetail;
   }


   @ExceptionHandler({NotValidArgumentException.class, UnitNotAvailableException.class})
   ProblemDetail handleBadRequestException(Exception ex)
   {
      ProblemDetail problemDetail = forStatusAndDetail(BAD_REQUEST, ex.getMessage());
      problemDetail.setTitle("Invalid Request");
      problemDetail.setDetail(ex.getMessage());
      return problemDetail;
   }

   @ExceptionHandler({Exception.class})
   ProblemDetail handleException(Exception ex)
   {
      ProblemDetail problemDetail = forStatusAndDetail(BAD_REQUEST, ex.getMessage());
      problemDetail.setTitle("Exception Occurred");
      problemDetail.setDetail(ex.getMessage());
      return problemDetail;
   }
}
