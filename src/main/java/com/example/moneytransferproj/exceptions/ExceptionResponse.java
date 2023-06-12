package com.example.moneytransferproj.exceptions;

public class ExceptionResponse {

    private String message;
    private Integer id;

    public ExceptionResponse(String message, Integer id) {
        this.message = message;
        this.id = id;
    }

    public ExceptionResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
