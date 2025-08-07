package com.distribuidoraferreira.backend.services.implementations;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import com.distribuidoraferreira.backend.dtos.CategoriaRequest;
import com.distribuidoraferreira.backend.dtos.CategoriaResponse;
import com.distribuidoraferreira.backend.dtos.GenericResponse;
import com.distribuidoraferreira.backend.dtos.BasicResponse;
import com.distribuidoraferreira.backend.mappers.CategoriaMapper;
import com.distribuidoraferreira.backend.models.Categoria;
import com.distribuidoraferreira.backend.repositories.CategoriaRepository;
import com.distribuidoraferreira.backend.services.interfaces.CategoriaService;

import jakarta.transaction.Transactional;

@Service
@AllArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    private final CategoriaMapper categoriaMapper;

    @Override
    public void addCategoria(CategoriaRequest categoriaRequest) {
        Categoria categoria = categoriaMapper.categoriaRequestToCategoria(categoriaRequest);
        categoria.setNome(categoria.getNome().toUpperCase());
        categoriaRepository.save(categoria);
    }

    @Override
    @Transactional
    public void deleteCategoriaById(Long id) {
        categoriaRepository.deleteCategoriaById(id);
    }

    @Override
    public GenericResponse<CategoriaResponse> getCategoriaById(Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        if (categoria.isEmpty()) {
            return new BasicResponse<>(null, 404);
        }

        CategoriaResponse response = categoriaMapper.categoriaToCategoriaResponse(categoria.get());

        @SuppressWarnings({ "rawtypes", "unchecked" })
        GenericResponse<CategoriaResponse> genericResponse = new BasicResponse(response, 200);

        return genericResponse;
    }

    @Override
    public GenericResponse<List<CategoriaResponse>> getCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();

        List<CategoriaResponse> categoriasResponse = categoriaMapper.categoriasToCategoriaResponses(categorias);

        return new BasicResponse<List<CategoriaResponse>>(categoriasResponse, 200);
    }

    @Override
    public GenericResponse<CategoriaResponse> getCategoriaByNome(String nome) {
        Optional<Categoria> categoria = categoriaRepository.findCategoriaByNome(nome.toUpperCase());
        if (categoria.isEmpty()) {
            return new BasicResponse<>(null, 404);
        }
        CategoriaResponse response = categoriaMapper.categoriaToCategoriaResponse(categoria.get());
        @SuppressWarnings({ "rawtypes", "unchecked" })
        GenericResponse<CategoriaResponse> genericResponse = new BasicResponse(response, 200);
        return genericResponse;
    }

}
