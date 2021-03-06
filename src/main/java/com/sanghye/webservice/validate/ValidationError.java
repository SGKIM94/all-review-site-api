package com.sanghye.webservice.validate;

public class ValidationError {
    private String fieldName;

    private String errorMessage;

    ValidationError(String fieldName, String errorMessage) {
        this.fieldName = fieldName;
        this.errorMessage = errorMessage;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return "ValidationError [fieldName=" + fieldName + ", errorMessage=" + errorMessage + "]";
    }
}
