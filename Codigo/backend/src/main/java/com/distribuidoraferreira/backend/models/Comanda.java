package com.distribuidoraferreira.backend.models;

import com.distribuidoraferreira.backend.enums.StatusComanda;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import jakarta.persistence.OneToMany;

@Entity
@Table(name = "comandas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comanda {
    @Id
    @Column(name = "comanda_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_hora", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date dataHora;

    @Enumerated(EnumType.STRING)
    private StatusComanda status;

    @Column(name = "caixa_Id")
    private Long idCaixa;

    @Column(name = "conta_cliente_id")
    private Long idCliente;

    @OneToMany(mappedBy = "comanda", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Venda> venda;

    public void addVenda(Venda venda) {
        venda.setComanda(this);
        this.venda.add(venda);
    }

    @PrePersist
    public void prePersist() {
        if (this.dataHora == null) {
            this.dataHora = Date.from(Instant.now()); // Valor padrão
        }
    }

}
