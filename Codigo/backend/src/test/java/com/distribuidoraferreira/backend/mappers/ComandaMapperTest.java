package com.distribuidoraferreira.backend.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.distribuidoraferreira.backend.dtos.ComandaRequest;
import com.distribuidoraferreira.backend.dtos.ComandaResponse;
import com.distribuidoraferreira.backend.dtos.VendaRequest;
import com.distribuidoraferreira.backend.models.Comanda;
import com.distribuidoraferreira.backend.models.Venda;
import com.distribuidoraferreira.backend.enums.StatusComanda;
import com.distribuidoraferreira.backend.enums.StatusVenda;


public class ComandaMapperTest {

    // private ComandaMapper mapper;

    // @BeforeEach
    // public void setUp() {
    //     mapper = new ComandaMapperImpl();
    // }

    // @Test
    // public void testComandaRequestToComanda() {
    //     VendaRequest venda = new VendaRequest(null, null, StatusVenda.PENDENTE);

    //     ComandaRequest request = new ComandaRequest(StatusComanda.ATIVA, null, venda);

    //     Comanda comanda = mapper.comandaRequestToComanda(request);

    //     assertEquals(StatusComanda.ATIVA, comanda.getStatus());
    //     assertEquals(1, comanda.getMesa());
    //     assertEquals(venda, comanda.getVenda());
    // }

    // @Test
    // public void testComandaToComandaResponse() {
    //     Comanda comanda = new Comanda();
    //     comanda.setId(1);
    //     comanda.setDataHora(new Date());
    //     comanda.setStatus(StatusComanda.INATIVA);
    //     comanda.setMesa(2);
    //     Venda venda = new Venda();
    //     comanda.setVenda(venda);

    //     ComandaResponse response = mapper.comandaToComandaResponse(comanda);

    //     assertEquals(1, response.id());
    //     assertEquals(StatusComanda.INATIVA, response.status());
    //     assertEquals(2, response.mesa());
    //     assertEquals(venda, response.vendaResponse());
    // }

    // @Test
    // public void testComandasToComandaResponses() {
    //     Comanda comanda1 = new Comanda();
    //     comanda1.setId(1);
    //     comanda1.setDataHora(new Date());
    //     comanda1.setStatus(StatusComanda.ATIVA);
    //     comanda1.setMesa(1);
    //     Venda venda1 = new Venda();
    //     comanda1.setVenda(venda1);

    //     Comanda comanda2 = new Comanda();
    //     comanda2.setId(2);
    //     comanda2.setDataHora(new Date());
    //     comanda2.setStatus(StatusComanda.INATIVA);
    //     comanda2.setMesa(2);
    //     Venda venda2 = new Venda();
    //     comanda2.setVenda(venda2);

    //     List<Comanda> comandas = Arrays.asList(comanda1, comanda2);

    //     List<ComandaResponse> responses = mapper.comandasToComandaResponses(comandas);

    //     assertEquals(2, responses.size());
    //     assertEquals(1, responses.get(0).id());
    //     assertEquals(StatusComanda.ATIVA, responses.get(0).status());
    //     assertEquals(1, responses.get(0).mesa());
    //     assertEquals(venda1, responses.get(0).vendaResponse());
    //     assertEquals(2, responses.get(1).id());
    //     assertEquals(StatusComanda.INATIVA, responses.get(1).status());
    //     assertEquals(2, responses.get(1).mesa());
    //     assertEquals(venda2, responses.get(1).vendaResponse());
    // }
}
