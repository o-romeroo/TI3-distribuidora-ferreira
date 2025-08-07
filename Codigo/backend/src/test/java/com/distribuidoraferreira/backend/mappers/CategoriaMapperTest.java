package com.distribuidoraferreira.backend.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.distribuidoraferreira.backend.dtos.CategoriaRequest;
import com.distribuidoraferreira.backend.dtos.CategoriaResponse;
import com.distribuidoraferreira.backend.models.Categoria;

public class CategoriaMapperTest {

    private CategoriaMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = new CategoriaMapperImpl();
    }

    @Test
    public void testCategoriaRequestToCategoria() {
        CategoriaRequest request = new CategoriaRequest("Categoria Teste");

        Categoria categoria = mapper.categoriaRequestToCategoria(request);

        assertEquals("Categoria Teste", categoria.getNome());
    }

    @Test
    public void testCategoriaToCategoriaResponse() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("Categoria Teste");

        CategoriaResponse response = mapper.categoriaToCategoriaResponse(categoria);

        assertEquals(1L, response.getId());
        assertEquals("Categoria Teste", response.getNome());
    }

    @Test
    public void testCategoriasToCategoriaResponses() {
        Categoria categoria1 = new Categoria();
        categoria1.setId(1L);
        categoria1.setNome("Categoria 1");

        Categoria categoria2 = new Categoria();
        categoria2.setId(2L);
        categoria2.setNome("Categoria 2");

        List<Categoria> categorias = Arrays.asList(categoria1, categoria2);

        List<CategoriaResponse> responses = mapper.categoriasToCategoriaResponses(categorias);

        assertEquals(2, responses.size());
        assertEquals(1L, responses.get(0).getId());
        assertEquals("Categoria 1", responses.get(0).getNome());
        assertEquals(2L, responses.get(1).getId());
        assertEquals("Categoria 2", responses.get(1).getNome());
    }
}
