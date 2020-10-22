package com.example.postapp.dataModel;

public class ErrorModel {
    private String errorCode;

    public ErrorModel(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        errorMas();
        return errorCode;
    }

    private void errorMas() {
        if (errorCode.equals("3033")) {
            errorCode = "User with the same identity already exists";
        } else if (errorCode.equals("2002")) {
            errorCode = "Invalid application info (application id or api key)";
        } else if (errorCode.equals("3009")) {
            errorCode = "User registration is disabled for the application";
        } else if (errorCode.equals("3010")) {
            errorCode = "User registration has an unknown property ";
        } else if (errorCode.equals("3014")) {
            errorCode = "External registration failed with an error.";
        } else if (errorCode.equals("3021")) {
            errorCode = "General user registration error.";
        } else if (errorCode.equals("3038")) {
            errorCode = "Missing application-id or collection of properties for the registering user";
        } else if (errorCode.equals("3039")) {
            errorCode = "Property id cannot be used in the registration call";
        } else if (errorCode.equals("8000")) {
            errorCode = "Property value exceeds the length limit";
        } else if (errorCode.equals("3018")) {
            errorCode = "The property marked as identity is being updated and another user already has the specified value which must be unique.";
        } else if (errorCode.equals("3028")) {
            errorCode = "User is not logged in. ";
        } else if (errorCode.equals("3029")) {
            errorCode = "Cannot modify properties of another user. ";
        } else if (errorCode.equals("3030")) {
            errorCode = "Unable to locate user account - invalid user id.";


        } else errorCode = "unKnown Error";
    }

    public void setErrorCode(String errorCode) {

        this.errorCode = errorCode;
    }
}
