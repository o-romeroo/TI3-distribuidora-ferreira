package com.distribuidoraferreira.backend.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.distribuidoraferreira.backend.dtos.CategoriaRequest;
import com.distribuidoraferreira.backend.dtos.CategoriaResponse;
import com.distribuidoraferreira.backend.models.Categoria;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nome", source = "request.nome")
    @Mapping(target = "produtos", ignore = true)
    public Categoria categoriaRequestToCategoria(CategoriaRequest request);

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "nome", source = "entity.nome")
    public CategoriaResponse categoriaToCategoriaResponse(Categoria entity);

    public List<CategoriaResponse> categoriasToCategoriaResponses(List<Categoria> entities);
}
