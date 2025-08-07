package com.distribuidoraferreira.backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

import com.distribuidoraferreira.backend.enums.TipoMovimentacao;

@Entity
@Table(name = "movimentacoes_estoque")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovimentacaoEstoque {
    @Id
    @Column(name = "id_movimentacao")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @Column(name = "horario_registro", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date horarioRegistro;

    @Column(name = "tipo", nullable = false)
    private TipoMovimentacao tipo;

    @Column(name = "observacao")
    private String observacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @Column(name = "valor_total_movimentacao")
    private Double valor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compra_id")
    private Compra compra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venda_id")
    private Venda venda;

    private double precoUnitario;

    @PrePersist
    public void prePersist() {
        if (this.horarioRegistro == null) {
            this.horarioRegistro = Date.from(Instant.now()); // Valor padrão
        }
    }

    public double calcularValorTotalMovimentacao() {
        return precoUnitario * quantidade;
    }
}
