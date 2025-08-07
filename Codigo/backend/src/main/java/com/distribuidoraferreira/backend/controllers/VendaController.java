package com.distribuidoraferreira.backend.controllers;

import com.distribuidoraferreira.backend.dtos.GenericResponse;
import com.distribuidoraferreira.backend.dtos.VendaPendenteResponse;
import com.distribuidoraferreira.backend.dtos.VendaRequest;
import com.distribuidoraferreira.backend.dtos.VendaResponse;
import com.distribuidoraferreira.backend.enums.StatusVenda;
import com.distribuidoraferreira.backend.services.interfaces.VendaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendas")
public class VendaController {
    @Autowired
    VendaService vendaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GenericResponse<String>> addVenda(@RequestBody VendaRequest vendaRequest) {
        return ResponseEntity.ok().body(vendaService.addVenda(vendaRequest));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericResponse<List<VendaResponse>>> getVendas() {
        return ResponseEntity.ok().body(vendaService.getVendas());
    }

    @GetMapping("/sort")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericResponse<List<VendaResponse>>> getVendasByDataHora() {
        return ResponseEntity.ok().body(vendaService.getVendasByDataHora());
    }

    @GetMapping("/pendentes")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericResponse<List<VendaPendenteResponse>>> getVendasPendentes() {
        return ResponseEntity.ok().body(vendaService.getVendasPendentes());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getVendaById(@PathVariable Long id) {
        return ResponseEntity.ok().body(vendaService.getVendaById(id));
    }

    @GetMapping("/comanda/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getVendaByIdComanda(@PathVariable Long id) {
        return ResponseEntity.ok().body(vendaService.getVendaByIdComanda(id));
    }

    @GetMapping("/status/{status}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericResponse<List<VendaResponse>>> getVendasByStatus(
            @PathVariable StatusVenda status) {
        return ResponseEntity.ok().body(vendaService.getVendasByStatus(status));
    }

    @GetMapping("/cliente/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericResponse<List<VendaPendenteResponse>>> getVendasPendentesPorCliente(
            @PathVariable Long id) {
        return ResponseEntity.ok().body(vendaService.getVendasPendentesPorCliente(id));
    }
}