package com.distribuidoraferreira.backend.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import com.distribuidoraferreira.backend.enums.MetodoPagamento;

@Entity
@Table(name = "compras")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Compra {
    @Id
    @Column(name = "compra_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_hora", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date dataHora;

    @Column(name = "total_compra")
    private double totalCompra;

    @Column(name = "metodo_pagamento", nullable = false)
    private MetodoPagamento metodoPagamento;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovimentacaoEstoque> movimentacoesEstoque;

    @PrePersist
    public void prePersist() {
        if (this.dataHora == null) {
            this.dataHora = Date.from(Instant.now()); // Valor padrão
        }

        if (this.totalCompra == 0)  {
            this.totalCompra = calcularValorTotal();
        }
    }

    private double calcularValorTotal() {
        double total = 0;

        for (MovimentacaoEstoque movimentacao : movimentacoesEstoque) {
            total = total + movimentacao.calcularValorTotalMovimentacao();
        }

        return total;
    }
}
