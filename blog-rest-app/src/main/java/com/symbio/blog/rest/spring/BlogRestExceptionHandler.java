package com.symbio.blog.rest.spring;

import com.symbio.blog.domain.exception.BlogServiceException;
import com.symbio.blog.domain.exception.BlogValidationException;
import com.symbio.blog.domain.exception.ErrorMessage;
import org.hibernate.JDBCException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class BlogRestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String INTERNAL_SERVER_ERROR = "Internal Server Error";

    private static final String VALIDATION_ERROR = "Validation Failed";


    @ExceptionHandler({BlogServiceException.class})
    public ResponseEntity<Object> handleBlogServiceException(BlogServiceException ex, WebRequest request) {
        return new ResponseEntity<Object>(new ErrorMessage(INTERNAL_SERVER_ERROR, ex.getErrorMessage()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler({JDBCException.class})
    public ResponseEntity<Object> handleJDBCException(JDBCException ex, WebRequest request) {
        return new ResponseEntity<Object>(new ErrorMessage(INTERNAL_SERVER_ERROR, ex.getMessage()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({DataAccessException.class})
    public ResponseEntity<Object> handleDataAccessException(DataAccessException ex, WebRequest request) {
        return new ResponseEntity<Object>(new ErrorMessage(INTERNAL_SERVER_ERROR, ex.getMessage()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler({BlogValidationException.class, IllegalArgumentException.class})
    public ResponseEntity<Object> handleBlogValidationException(BlogValidationException ex, WebRequest request) {
        return new ResponseEntity<Object>(new ErrorMessage(VALIDATION_ERROR, ex.getErrorMessage()), new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMessage = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
        ErrorMessage error = new ErrorMessage(VALIDATION_ERROR, errorMessage);
        return new ResponseEntity<Object>(error, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t + ", "));

        ErrorMessage error = new ErrorMessage(VALIDATION_ERROR, builder.substring(0, builder.length() - 2));
        return new ResponseEntity<Object>(error, new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);

    }


    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t + ", "));

        ErrorMessage error = new ErrorMessage(VALIDATION_ERROR, builder.substring(0, builder.length() - 2));
        return new ResponseEntity<Object>(error, new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorMessage errorDetails = new ErrorMessage(VALIDATION_ERROR, ex.getBindingResult().toString());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }


}