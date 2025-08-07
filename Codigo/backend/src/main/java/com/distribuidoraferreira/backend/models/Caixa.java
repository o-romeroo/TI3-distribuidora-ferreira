package com.distribuidoraferreira.backend.models;

import com.distribuidoraferreira.backend.enums.StatusCaixa;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

@Entity
@Table(name = "caixas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Caixa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "caixa_id")
    private Long id;

    @Column(name = "data_hora", nullable = false)
    private Date dataHora;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusCaixa status;

    @Column(name = "valor_inicial", nullable = false)
    private Double valorInicial;

    @Column(name = "faturamento_dia", nullable = false)
    private Double faturamentoDia;

    @Column(name = "valor_total", nullable = false)
    private Double valorTotal;

    @OneToMany(mappedBy = "caixa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Venda> vendas;

    @PrePersist
    public void prePersist() {
        if (this.dataHora == null) {
            this.dataHora = Date.from(Instant.now()); // Valor padrão
        }

        if (this.faturamentoDia == null) {
            this.faturamentoDia = 0.0;
        }

        if (this.valorTotal == null) {
            this.valorTotal = this.valorInicial;
        }
    }
}