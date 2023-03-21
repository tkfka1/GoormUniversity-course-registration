package com.finalproject.excption;

import java.util.HashMap;
import java.util.Map;

public abstract class UniversityException extends RuntimeException{
    public final Map<String, String> validation = new HashMap<>();

    public UniversityException(String message){
        super(message);
    }
    public UniversityException(String message, Throwable cause){
        super(message, cause);
    }

    public abstract int getStatusCode();

    public void addValidation(String field, String message){
        validation.put(field, message);
    }
}
