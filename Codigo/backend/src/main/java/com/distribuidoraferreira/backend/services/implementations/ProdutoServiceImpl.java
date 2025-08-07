package com.distribuidoraferreira.backend.services.implementations;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.distribuidoraferreira.backend.api_tools.ByteToFileConverter;
import com.distribuidoraferreira.backend.api_tools.Upload_Helper;
import com.distribuidoraferreira.backend.dtos.BasicResponse;
import com.distribuidoraferreira.backend.dtos.ErrorResponse;
import com.distribuidoraferreira.backend.dtos.GenericResponse;
import com.distribuidoraferreira.backend.dtos.ImageResponse;
import com.distribuidoraferreira.backend.dtos.ProdutoRequest;
import com.distribuidoraferreira.backend.dtos.ProdutoResponse;
import com.distribuidoraferreira.backend.mappers.ProdutoMapper;
import com.distribuidoraferreira.backend.mappers.ProdutoMapperImpl;
import com.distribuidoraferreira.backend.models.Categoria;
import com.distribuidoraferreira.backend.models.Produto;
import com.distribuidoraferreira.backend.repositories.CategoriaRepository;
import com.distribuidoraferreira.backend.repositories.ProdutoRepository;
import com.distribuidoraferreira.backend.services.exceptions.CategoriaNotFoundException;
import com.distribuidoraferreira.backend.services.exceptions.CategoriaProdutoBadRequestException;
import com.distribuidoraferreira.backend.services.exceptions.ProdutoNotFoundException;
import com.distribuidoraferreira.backend.services.interfaces.ProdutoService;
import com.uploadcare.api.File;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;

    private final CategoriaRepository categoriaRepository;

    private final ProdutoMapper produtoMapper;

    private final Upload_Helper uploadHelper = new Upload_Helper();

    @Override
    public GenericResponse<ProdutoResponse> addProduto(ProdutoRequest userRequest) {
        Produto produto = produtoMapper.produtoRequestToProduto(userRequest);

        Optional<Categoria> categoria = categoriaRepository
                .findCategoriaByNome(userRequest.getCategoriaRequest().getNome());

        if (categoria.isEmpty()) {
            throw new CategoriaNotFoundException();
        }
        produto.setCategoria(categoria.get());
        produtoRepository.save(produto);
        return new BasicResponse<ProdutoResponse>(produtoMapper.produtoToProdutoResponse(produto), 201);
    }

    @Override
    public GenericResponse<List<ProdutoResponse>> getProdutos() {
        List<Produto> produtos = produtoRepository.findAll();

        List<ProdutoResponse> produtosResponse = produtoMapper.produtosToProdutoResponses(produtos);

        return new BasicResponse<List<ProdutoResponse>>(produtosResponse, 200);
    }

    @Override
    public GenericResponse<ProdutoResponse> getProdutoById(Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);

        if (produto.isEmpty()) {
            return new ErrorResponse<ProdutoResponse>(null, 404, "Produto não encontrado");
        }

        ProdutoResponse response = produtoMapper.produtoToProdutoResponse(produto.get());

        return new BasicResponse<ProdutoResponse>(response, 200);
    }

    @Override
    public GenericResponse<ProdutoResponse> getProdutoByNome(String nome) {
        Produto entity = produtoRepository.findByNome(nome).orElseThrow(ProdutoNotFoundException::new);

        ProdutoResponse response = produtoMapper.produtoToProdutoResponse(entity);

        return new BasicResponse<ProdutoResponse>(response, 200);
    }

    @Override
    public GenericResponse<ProdutoResponse> getProdutoByCodBarras(String codBarras) {
        Optional<Produto> entity = produtoRepository.findByCodBarras(codBarras);

        if (entity.isEmpty()) {
            return new ErrorResponse<ProdutoResponse>(null, 404, "Produto não encontrado");
        }

        ProdutoResponse response = produtoMapper.produtoToProdutoResponse(entity.get());

        return new BasicResponse<ProdutoResponse>(response, 200);
    }

    @Override
    public void updateProduto(Long id, ProdutoRequest produtoRequest) {
        Produto produto = produtoRepository.findById(id).orElseThrow(ProdutoNotFoundException::new);
        produtoRepository.save(updateProdutoByProdutoRequest(produtoRequest, produto).getEntity());
    }

    private GenericResponse<Produto> updateProdutoByProdutoRequest(ProdutoRequest produtoRequest, Produto produto) {
        produto.setNome(produtoRequest.getNome());
        produto.setCodBarras(produtoRequest.getCodBarras());
        produto.setPreco(produtoRequest.getPreco());
        produto.setImg(produtoRequest.getImg());
        produto.setQuantidadeEstoque(produtoRequest.getQuantidadeEstoque());
        produto.setCategoria(findCategoriaByNome(produto.getCategoria().getNome()));

        return new BasicResponse<Produto>(produto, 200);
    }

    @Override
    public GenericResponse<ImageResponse> uploadImage(MultipartFile file) throws IOException {
        return new BasicResponse<ImageResponse>(
                uploadHelper.uploadImage(ByteToFileConverter.convertMultipartFileToFile(file)), 200);
    }

    @Override
    public GenericResponse<String> updateEstoqueMinimoProduto(Long id, Integer quantidadeMinimaEstoque) {
        Produto produto = produtoRepository.findById(id).orElseThrow(ProdutoNotFoundException::new);
        produto.setQuantidadeMinimaEstoque(quantidadeMinimaEstoque);
        produtoRepository.save(produto);
        return new BasicResponse<String>("Estoque mínimo atualizado com sucesso", 200);
    }

    @Override
    public GenericResponse<ProdutoResponse> deleteProduto(Long id) {
        Produto produto = produtoRepository.findById(id).orElseThrow(ProdutoNotFoundException::new);
        produtoRepository.delete(produto);
        return new BasicResponse<ProdutoResponse>(produtoMapper.produtoToProdutoResponse(produto), 204);
    }

    @Override
    public GenericResponse<ProdutoResponse> deleteImage(String ImageID) {
        boolean deleted = uploadHelper.deleteById(ImageID);
        if (deleted) {
            return new BasicResponse<ProdutoResponse>(null, 204);
        }
        return new BasicResponse<ProdutoResponse>(null, 404);
    }

    private Categoria findCategoriaByNome(String nome) {
        return categoriaRepository.findCategoriaByNome(nome).orElseThrow(CategoriaNotFoundException::new);
    }

}
