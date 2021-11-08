package com.ttn.project.ecommerce.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.Map;

//@JsonInclude(JsonInclude.Include.NON_NULL)
//public class ExceptionResponse {
//    int status;
//    private Date timestamp;
//    private String message;
//    private String details;
//    Map<String,String> validationErrors;
//
//    public ExceptionResponse(int status, Date timestamp, String message, String details) {
//        super();
//        this.status = status;
//        this.timestamp = timestamp;
//        this.message = message;
//        this.details = details;
//    }
//
//    public int getStatus() {
//        return status;
//    }
//
//    public Date getTimestamp() {
//        return timestamp;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public String getDetails() {
//        return details;
//    }
//}

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponse {
    private Date timestamp;
    private String message;
    private String details;

    public ExceptionResponse(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}