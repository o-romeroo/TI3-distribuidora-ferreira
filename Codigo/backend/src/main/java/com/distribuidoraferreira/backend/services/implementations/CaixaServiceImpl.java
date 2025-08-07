package com.distribuidoraferreira.backend.services.implementations;

import com.distribuidoraferreira.backend.dtos.BasicResponse;
import com.distribuidoraferreira.backend.dtos.CaixaDetailsResponse;
import com.distribuidoraferreira.backend.dtos.CaixaRequest;
import com.distribuidoraferreira.backend.dtos.CaixaResponse;
import com.distribuidoraferreira.backend.dtos.GenericResponse;
import com.distribuidoraferreira.backend.enums.StatusCaixa;
import com.distribuidoraferreira.backend.enums.StatusComanda;
import com.distribuidoraferreira.backend.mappers.CaixaMapper;
import com.distribuidoraferreira.backend.models.Caixa;
import com.distribuidoraferreira.backend.models.Comanda;
import com.distribuidoraferreira.backend.models.DetalhesCaixa;
import com.distribuidoraferreira.backend.repositories.CaixaRepository;
import com.distribuidoraferreira.backend.repositories.ComandaRepository;
import com.distribuidoraferreira.backend.services.interfaces.CaixaService;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CaixaServiceImpl implements CaixaService {

    private final CaixaRepository caixaRepository;

    private final ComandaRepository comandaRepository;

    private final CaixaMapper caixaMapper;

    @Override
    public GenericResponse<String> abrirCaixa(CaixaRequest caixaRequest) {

        if (isCaixaAberto().getEntity()) {
            return new BasicResponse<>("Já existe um caixa aberto!", 400);
        }

        Caixa caixa = caixaMapper.caixaRequestToCaixa(caixaRequest);
        caixa.setStatus(StatusCaixa.ABERTO);

        caixaRepository.save(caixa);

        return new BasicResponse<>("Caixa criado e aberto com sucesso", 201);
    }

    @Override
    public GenericResponse<String> fecharCaixa(Long id) {
        Optional<Caixa> caixa = caixaRepository.findById(id);

        List<Comanda> comandas = comandaRepository.findComandaByIdCaixa(id);
        boolean existsComandaAberta = comandas.stream()
                .anyMatch(comanda -> comanda.getStatus() == StatusComanda.ABERTA);

        if (existsComandaAberta) {
            return new BasicResponse<>("Existem comandas abertas para este caixa", 400);
        }

        if (caixa.isEmpty()) {
            return new BasicResponse<>("Caixa inexistente", 404);
        }

        if (caixa.get().getStatus() == StatusCaixa.FECHADO) {
            return new BasicResponse<>("Caixa ja esta fechado", 400);
        }

        caixa.get().setStatus(StatusCaixa.FECHADO);

        caixaRepository.save(caixa.get());

        return new BasicResponse<>("Caixa fechado com sucesso", 200);
    }

    @Override
    public GenericResponse<CaixaResponse> getCaixaById(Long id) {
        Optional<Caixa> caixa = caixaRepository.findById(id);

        if (caixa.isEmpty()) {
            return new BasicResponse<>(null, 404);
        }

        CaixaResponse response = caixaMapper.caixaToCaixaResponse(caixa.get());
        return new BasicResponse<>(response, 200);
    }

    @Override
    public GenericResponse<java.util.List<CaixaResponse>> getCaixas() {
        Optional<java.util.List<Caixa>> caixas = caixaRepository.getAllCaixasDesc();

        if (caixas.isEmpty()) {
            return new BasicResponse<>(null, 404);
        }

        for (Caixa caixa : caixas.get()) {
            caixa.setValorTotal(caixa.getFaturamentoDia() + caixa.getValorInicial());
        }

        java.util.List<CaixaResponse> response = caixaMapper.caixasToCaixasResponses(caixas.get());
        return new BasicResponse<>(response, 200);
    }

    @Override
    public GenericResponse<String> updateCaixa(Long id, CaixaRequest caixaRequest) {
        Optional<Caixa> caixa = caixaRepository.findById(id);

        if (caixa.isEmpty()) {
            return new BasicResponse<>("O caixa em questao nao existe", 404);
        }

        Caixa updatedCaixa = caixaMapper.caixaRequestToCaixa(caixaRequest);
        updatedCaixa.setId(id);
        caixaRepository.save(updatedCaixa);

        return new BasicResponse<>("Caixa atualizado com sucesso", 200);
    }

    @Override
    public GenericResponse<CaixaResponse> getCaixaByDataHora(Date dataHora) {
        Optional<Caixa> caixa = caixaRepository.findCaixaByStatusAberto();

        if (caixa.isEmpty()) {
            return new BasicResponse<>(null, 404);
        }
        caixa.get().setValorTotal(caixa.get().getFaturamentoDia() + caixa.get().getValorInicial());
        CaixaResponse response = caixaMapper.caixaToCaixaResponse(caixa.get());
        return new BasicResponse<>(response, 200);
    }

    @Override
    public GenericResponse<Boolean> isCaixaAberto() {
        Optional<Caixa> caixa = caixaRepository.findCaixaByStatusAberto();

        boolean response = (caixa.isPresent() && caixa.get().getStatus() == StatusCaixa.ABERTO);
        return new BasicResponse<>(response, 200);
    }

    @Override
    public GenericResponse<CaixaDetailsResponse> getDetalhesCaixa(Long id) {
        Optional<Caixa> caixa = caixaRepository.findById(id);

        if (caixa.isEmpty()) {
            return new BasicResponse<>(null, 404);
        }

        caixa.get().getVendas().forEach(action -> {
            System.out.println(action.getMetodoPagamento());
            System.out.println(action.getTotalVenda());
        });

        DetalhesCaixa detalhesCaixa = new DetalhesCaixa();

        caixa.get().getVendas().stream().forEach(action -> {
            detalhesCaixa.setDebito(detalhesCaixa.getDebito() + action.getTotalPagoDebito());
            detalhesCaixa.setCredito(detalhesCaixa.getCredito() + action.getTotalPagoCredito());
            detalhesCaixa.setPix(detalhesCaixa.getPix() + action.getTotalPagoPix());
            detalhesCaixa.setDinheiro(detalhesCaixa.getDinheiro() + action.getTotalPagoDinheiro());
        });

        CaixaDetailsResponse response = caixaMapper.caixaToCaixaDetailsResponse(detalhesCaixa);

        return new BasicResponse<>(response, 200);
    }

    @Override
    public GenericResponse<CaixaResponse> getCaixa() {
        Optional<Caixa> caixa = caixaRepository.findCaixaByStatusAberto();

        if (caixa.isEmpty()) {
            return new BasicResponse<>(null, 404);
        }
        caixa.get().setValorTotal(caixa.get().getFaturamentoDia() + caixa.get().getValorInicial());
        CaixaResponse response = caixaMapper.caixaToCaixaResponse(caixa.get());
        return new BasicResponse<>(response, 200);
    }

    @Override
    public GenericResponse<Long> getCaixaByID() {
        Optional<Caixa> caixa = caixaRepository.findCaixaByStatusAberto();

        boolean response = (caixa.isPresent() && caixa.get().getStatus() == StatusCaixa.ABERTO) || false;
        if (response) {
            long idResponse = caixa.get().getId();
            return new BasicResponse<>(idResponse, 200);

        }
        return new BasicResponse<>(null, 404);
    }
}