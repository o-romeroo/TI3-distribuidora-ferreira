package com.distribuidoraferreira.backend.dtos;

public class ErrorResponse<T> implements GenericResponse<T> {
    private T entity;
    private int status;
    private String messageError;

    public ErrorResponse(T entity, int status) {
        this.entity = entity;
        this.status = status;
        messageError = "";
    }

    public ErrorResponse(T entity, int status, String messageError) {
        this.entity = entity;
        this.status = status;
        this.messageError = messageError;
    }

    @Override
    public T getEntity() {
        return entity;
    }

    @Override
    public int getStatus() {
        return status;
    }
}
