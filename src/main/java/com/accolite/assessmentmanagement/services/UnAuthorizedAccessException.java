package com.accolite.assessmentmanagement.services;

public class UnAuthorizedAccessException extends Exception {
    public UnAuthorizedAccessException(String errorMessage) {
        super(errorMessage);
    }
}
