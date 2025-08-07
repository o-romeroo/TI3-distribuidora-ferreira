package com.distribuidoraferreira.backend.services.interfaces;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.distribuidoraferreira.backend.dtos.GenericResponse;
import com.distribuidoraferreira.backend.dtos.ImageResponse;
import com.distribuidoraferreira.backend.dtos.ProdutoRequest;
import com.distribuidoraferreira.backend.dtos.ProdutoResponse;

public interface ProdutoService {
    GenericResponse<ProdutoResponse> addProduto(ProdutoRequest userRequest);

    GenericResponse<ProdutoResponse> getProdutoById(Long id);

    GenericResponse<ProdutoResponse> getProdutoByNome(String nome);

    GenericResponse<ProdutoResponse> getProdutoByCodBarras(String codBarras);

    GenericResponse<List<ProdutoResponse>> getProdutos();

    GenericResponse<ProdutoResponse> deleteProduto(Long id);

    GenericResponse<ProdutoResponse> deleteImage(String ImageID);

    GenericResponse<ImageResponse> uploadImage(MultipartFile file) throws IOException;

    GenericResponse<String> updateEstoqueMinimoProduto(Long id, Integer quantidadeMinimaEstoque);

    void updateProduto(Long id, ProdutoRequest produtoRequest);
}
