package com.distribuidoraferreira.backend.dtos;

public interface GenericResponse<T> {
    T getEntity();

    int getStatus();
}
