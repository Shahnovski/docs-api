package com.example.docksapi.exception;

import com.example.docksapi.exception.exceptions.DocNotFoundException;
import com.example.docksapi.exception.exceptions.NotAuthorizedException;
import com.example.docksapi.exception.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    ExceptionMessages exceptionMessages = new ExceptionMessages();


    @ExceptionHandler(NotAuthorizedException.class)
    protected ResponseEntity<?> handleNotAuthorizedException(NotAuthorizedException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exceptionMessages.getNotAuthorizedExceptionMessage(), "", 401);
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DocNotFoundException.class)
    protected ResponseEntity<?> handleCarNotFoundException(DocNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exceptionMessages.getDocNotFoundMessage(), "", 404);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exceptionMessages.getUserNotFoundMessage(), "", 404);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ParseException.class)
    protected ResponseEntity<?> handleParseException(ParseException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exceptionMessages.getParseExceptionMessage(), "", 500);
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<?> handleNullPointerException(NullPointerException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exceptionMessages.getNullPointerExceptionMessage(), "", 500);
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IOException.class)
    protected ResponseEntity<?> handleIOException(IOException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exceptionMessages.getIOExceptionMessage(), "", 500);
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(PropertyReferenceException.class)
    protected ResponseEntity<?> handlePropertyReferenceException(PropertyReferenceException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exceptionMessages.getPropertyReferenceExceptionMessage(), "", 500);
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(org.json.JSONException.class)
    protected ResponseEntity<?> handleJSONException(org.json.JSONException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exceptionMessages.getJSONExceptionMessage(), "", 500);
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
