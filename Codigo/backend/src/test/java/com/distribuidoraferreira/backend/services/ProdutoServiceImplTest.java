package com.distribuidoraferreira.backend.services;

import com.distribuidoraferreira.backend.dtos.CategoriaRequest;
import com.distribuidoraferreira.backend.dtos.ProdutoRequest;
import com.distribuidoraferreira.backend.dtos.ProdutoResponse;
import com.distribuidoraferreira.backend.mappers.ProdutoMapper;
import com.distribuidoraferreira.backend.models.Categoria;
import com.distribuidoraferreira.backend.models.Produto;
import com.distribuidoraferreira.backend.repositories.CategoriaRepository;
import com.distribuidoraferreira.backend.repositories.ProdutoRepository;
import com.distribuidoraferreira.backend.services.implementations.ProdutoServiceImpl;

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
public class ProdutoServiceImplTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private ProdutoMapper produtoMapper;

    @InjectMocks
    private ProdutoServiceImpl produtoService;

    private EasyRandom easyRandom;
    private Produto produto;
    private Categoria categoria;
    private ProdutoRequest produtoRequest;
    private ProdutoResponse produtoResponse;

    @BeforeEach
    public void setUp() {
        easyRandom = new EasyRandom();

        categoria = easyRandom.nextObject(Categoria.class);
        categoriaRepository.save(categoria);

        produto = easyRandom.nextObject(Produto.class);
        produto.setCategoria(categoria);

        produtoRequest = easyRandom.nextObject(ProdutoRequest.class);
        produtoRequest.setCategoriaRequest(new CategoriaRequest(categoria.getNome()));

        produtoResponse = easyRandom.nextObject(ProdutoResponse.class);
    }

    @Test
    public void GIVEN_produto_request_WHEN_addProduto_THEN_save_is_called_on_repository() {
        when(produtoMapper.produtoRequestToProduto(any(ProdutoRequest.class))).thenReturn(produto);
        when(categoriaRepository.findCategoriaByNome(any(String.class))).thenReturn(Optional.of(categoria));

        produtoService.addProduto(produtoRequest);

        verify(produtoRepository).save(any(Produto.class));
    }

    @Test
    public void GIVEN_no_input_WHEN_getProdutos_THEN_findAll_and_produtosToProdutoResponses_are_called() {
        when(produtoRepository.findAll()).thenReturn(Collections.singletonList(produto));
        when(produtoMapper.produtosToProdutoResponses(anyList()))
                .thenReturn(Collections.singletonList(produtoResponse));

        produtoService.getProdutos();

        verify(produtoRepository).findAll();
        verify(produtoMapper).produtosToProdutoResponses(anyList());
    }

    @Test
    public void GIVEN_produto_id_WHEN_getProdutoById_THEN_findById_and_produtoToProdutoResponse_are_called() {
        when(produtoRepository.findById(any(Long.class))).thenReturn(Optional.of(produto));
        when(produtoMapper.produtoToProdutoResponse(any(Produto.class))).thenReturn(produtoResponse);

        produtoService.getProdutoById(produto.getId());

        verify(produtoRepository).findById(any(Long.class));
        verify(produtoMapper).produtoToProdutoResponse(any(Produto.class));
    }

    @Test
    public void GIVEN_produto_nome_WHEN_getProdutoByNome_THEN_findByNome_and_produtoToProdutoResponse_are_called() {
        when(produtoRepository.findByNome(any(String.class))).thenReturn(Optional.of(produto));
        when(produtoMapper.produtoToProdutoResponse(any(Produto.class))).thenReturn(produtoResponse);

        produtoService.getProdutoByNome(produto.getNome());

        verify(produtoRepository).findByNome(any(String.class));
        verify(produtoMapper).produtoToProdutoResponse(any(Produto.class));
    }

    @Test
    public void GIVEN_cod_barras_WHEN_getProdutoByCodBarras_THEN_findByCodBarras_and_produtoToProdutoResponse_are_called() {
        when(produtoRepository.findByCodBarras(any(String.class))).thenReturn(Optional.of(produto));
        when(produtoMapper.produtoToProdutoResponse(any(Produto.class))).thenReturn(produtoResponse);

        produtoService.getProdutoByCodBarras(produto.getCodBarras());

        verify(produtoRepository).findByCodBarras(any(String.class));
        verify(produtoMapper).produtoToProdutoResponse(any(Produto.class));
    }

    @Test
    public void GIVEN_produto_id_and_produto_request_WHEN_updateProduto_THEN_findById_and_save_are_called_on_repository() {
        when(produtoRepository.findById(any(Long.class))).thenReturn(Optional.of(produto));
        when(categoriaRepository.findCategoriaByNome(any(String.class))).thenReturn(Optional.of(categoria));

        produtoService.updateProduto(produto.getId(), produtoRequest);

        verify(produtoRepository).findById(any(Long.class));
        verify(produtoRepository).save(any(Produto.class));
    }

    @Test
    public void GIVEN_produto_request_and_produto_WHEN_updateProdutoByProdutoRequest_THEN_findCategoriaByNome_and_save_are_called_on_repository() {
        when(produtoRepository.findById(any(Long.class))).thenReturn(Optional.of(produto));
        when(categoriaRepository.findCategoriaByNome(any(String.class))).thenReturn(Optional.of(categoria));

        produtoService.updateProduto(produto.getId(), produtoRequest);

        verify(categoriaRepository).findCategoriaByNome(any(String.class));
        verify(produtoRepository).save(any(Produto.class));
    }
}