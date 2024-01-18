package ru.miroshka.astra4backclient.exceptios.errors;


import java.util.List;

public class FieldsValidationError {
    private List<String> errorFieldsMessages;

    public FieldsValidationError(List<String> errorFieldsMessages) {
        this.errorFieldsMessages = errorFieldsMessages;
    }

    public FieldsValidationError() {
    }

    public List<String> getErrorFieldsMessages() {
        return errorFieldsMessages;
    }

    public void setErrorFieldsMessages(List<String> errorFieldsMessages) {
        this.errorFieldsMessages = errorFieldsMessages;
    }
}
