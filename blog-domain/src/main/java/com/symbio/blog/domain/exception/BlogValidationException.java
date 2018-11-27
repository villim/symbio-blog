package com.symbio.blog.domain.exception;

public class BlogValidationException extends RuntimeException {

    private static final long serialVersionUID = 4362672288875678384L;

    private String errorMessage;

    public BlogValidationException(String errorMessage, Throwable cause) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public BlogValidationException(Throwable cause) {
        super(cause);
    }

    public BlogValidationException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }


    public String getErrorMessage() {
        return errorMessage;
    }

}
