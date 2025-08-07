package com.distribuidoraferreira.backend.dtos;

public class BasicResponse<T> implements GenericResponse<T> {
    private T entity;
    private int status;

    public BasicResponse(T entity, int status) {
        this.entity = entity;
        this.status = status;
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
