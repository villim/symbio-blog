package com.symbio.blog.domain.exception;

public class BlogServiceException extends RuntimeException {


    private static final long serialVersionUID = 4362672288875678383L;

    private String errorMessage;

    public BlogServiceException(String errorMessage, Throwable cause) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public BlogServiceException(Throwable cause) {
        super(cause);
    }

    public BlogServiceException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }


    public String getErrorMessage() {
        return errorMessage;
    }

}
