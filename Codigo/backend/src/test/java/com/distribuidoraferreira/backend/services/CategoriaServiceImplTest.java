package com.distribuidoraferreira.backend.services;

import com.distribuidoraferreira.backend.dtos.CategoriaRequest;
import com.distribuidoraferreira.backend.dtos.CategoriaResponse;
import com.distribuidoraferreira.backend.mappers.CategoriaMapper;
import com.distribuidoraferreira.backend.models.Categoria;
import com.distribuidoraferreira.backend.repositories.CategoriaRepository;
import com.distribuidoraferreira.backend.services.implementations.CategoriaServiceImpl;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoriaServiceImplTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private CategoriaMapper categoriaMapper;

    @InjectMocks
    private CategoriaServiceImpl categoriaService;

    private EasyRandom easyRandom;
    private Categoria categoria;
    private CategoriaRequest categoriaRequest;
    private CategoriaResponse categoriaResponse;

    @BeforeEach
    public void setUp() {
        easyRandom = new EasyRandom();
        categoria = easyRandom.nextObject(Categoria.class);
        categoriaRequest = easyRandom.nextObject(CategoriaRequest.class);
        categoriaResponse = easyRandom.nextObject(CategoriaResponse.class);
    }

    @Test
    public void GIVEN_categoria_request_WHEN_addCategoria_THEN_save_is_called_on_repository() {
        when(categoriaMapper.categoriaRequestToCategoria(any(CategoriaRequest.class))).thenReturn(categoria);

        categoriaService.addCategoria(categoriaRequest);

        verify(categoriaRepository).save(any(Categoria.class));
    }

    @Test
    public void GIVEN_categoria_id_WHEN_deleteCategoriaById_THEN_deleteCategoriaById_is_called_on_repository() {
        categoriaService.deleteCategoriaById(categoria.getId());

        verify(categoriaRepository).deleteCategoriaById(any(Long.class));
    }

    @Test
    public void GIVEN_categoria_id_WHEN_getCategoriaById_THEN_findById_and_categoriaToCategoriaResponse_are_called() {
        when(categoriaRepository.findById(any(Long.class))).thenReturn(Optional.of(categoria));
        when(categoriaMapper.categoriaToCategoriaResponse(any(Categoria.class))).thenReturn(categoriaResponse);

        categoriaService.getCategoriaById(categoria.getId());

        verify(categoriaRepository).findById(any(Long.class));
        verify(categoriaMapper).categoriaToCategoriaResponse(any(Categoria.class));
    }

    @Test
    public void GIVEN_nome_WHEN_getCategoriaByNome_THEN_findCategoriaByNome_and_categoriaToCategoriaResponse_are_called() {
        when(categoriaRepository.findCategoriaByNome(any(String.class))).thenReturn(Optional.of(categoria));
        when(categoriaMapper.categoriaToCategoriaResponse(any(Categoria.class))).thenReturn(categoriaResponse);

        categoriaService.getCategoriaByNome(categoria.getNome());

        verify(categoriaRepository).findCategoriaByNome(any(String.class));
        verify(categoriaMapper).categoriaToCategoriaResponse(any(Categoria.class));
    }

    @Test
    public void GIVEN_no_input_WHEN_getCategorias_THEN_findAll_and_categoriasToCategoriaResponses_are_called() {
        when(categoriaRepository.findAll()).thenReturn(Collections.singletonList(categoria));
        when(categoriaMapper.categoriasToCategoriaResponses(anyList()))
                .thenReturn(Collections.singletonList(categoriaResponse));

        categoriaService.getCategorias();

        verify(categoriaRepository).findAll();
        verify(categoriaMapper).categoriasToCategoriaResponses(anyList());
    }
}