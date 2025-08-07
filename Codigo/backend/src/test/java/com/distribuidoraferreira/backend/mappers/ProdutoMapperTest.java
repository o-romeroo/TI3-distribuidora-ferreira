package com.distribuidoraferreira.backend.mappers; 

import com.distribuidoraferreira.backend.dtos.CategoriaRequest;
import com.distribuidoraferreira.backend.dtos.ProdutoRequest;
import com.distribuidoraferreira.backend.dtos.ProdutoResponse;
import com.distribuidoraferreira.backend.models.Categoria;
import com.distribuidoraferreira.backend.models.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ProdutoMapperTest {

    // @Mock
    // private CategoriaMapper categoriaMapper;

    // @InjectMocks
    // private ProdutoMapper produtoMapper = new ProdutoMapperImpl();

    // @BeforeEach
    // public void setup() {
    //     MockitoAnnotations.openMocks(this);
    // }

    // @Test
    // public void testProdutoRequestToProduto() {
    //     CategoriaRequest categoriaRequest = new CategoriaRequest();
    //     categoriaRequest.setNome("Categoria Teste");

    //     ProdutoRequest produtoRequest = new ProdutoRequest("123456789", "Produto Teste", 10.0, 100, "img.jpg", null, categoriaRequest);

    //     Categoria categoria = new Categoria();
    //     categoria.setNome("Categoria Teste");

    //     when(categoriaMapper.categoriaRequestToCategoria(categoriaRequest)).thenReturn(categoria);

    //     Produto produto = produtoMapper.produtoRequestToProduto(produtoRequest);

    //     assertEquals(produtoRequest.getCodBarras(), produto.getCodBarras());
    //     assertEquals(produtoRequest.getNome(), produto.getNome());
    //     assertEquals(produtoRequest.getPreco(), produto.getPreco());
    //     assertEquals(produtoRequest.getImg(), produto.getImg());
    //     assertEquals(produtoRequest.getQuantidadeEstoque(), produto.getQuantidadeEstoque());
    //     assertEquals(categoria, produto.getCategoria());
    // }

    // @Test
    // public void testProdutoToProdutoResponse() {
    //     Categoria categoria = new Categoria();
    //     categoria.setNome("Categoria Teste");

    //     Produto produto = new Produto();
    //     produto.setId(1L);
    //     produto.setCodBarras("123456789");
    //     produto.setNome("Produto Teste");
    //     produto.setPreco(10.0);
    //     produto.setImg("img.jpg");
    //     produto.setQuantidadeEstoque(100);
    //     produto.setCategoria(categoria);

    //     ProdutoResponse produtoResponse = produtoMapper.produtoToProdutoResponse(produto);

    //     assertEquals(produto.getId(), produtoResponse.getId());
    //     assertEquals(produto.getNome(), produtoResponse.getNome());
    //     assertEquals(produto.getCodBarras(), produtoResponse.getCodBarras());
    //     assertEquals(produto.getPreco(), produtoResponse.getPreco());
    //     assertEquals(produto.getQuantidadeEstoque(), produtoResponse.getQuantidadeEstoque());
    //     assertEquals(produto.getImg(), produtoResponse.getImg());
    //     assertEquals(produto.getCategoria().getNome(), produtoResponse.getCategoriaResponse().getNome());
    // }
}
