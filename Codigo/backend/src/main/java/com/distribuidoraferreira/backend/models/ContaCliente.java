package com.distribuidoraferreira.backend.models;

import com.distribuidoraferreira.backend.enums.StatusCliente;
import com.distribuidoraferreira.backend.enums.StatusVenda;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "contas_cliente")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContaCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conta_cliente_id")
    private Long id;

    @Column(name = "nome_cliente", nullable = false)
    private String nomeCliente;

    @Column(name = "telefone", nullable = false)
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusCliente status;

    @Column(name = "saldo_devedor", nullable = true)
    private Double saldoDevedor;

    @OneToMany(mappedBy = "contaCliente", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Venda> vendas;

    @Column(name = "total_pago", nullable = true)
    private Double totalPago;

    @Column(name = "total_pago_pix", nullable = true)
    private Double totalPagoPix;

    @Column(name = "total_pago_debito", nullable = true)
    private Double totalPagoDebito;

    @Column(name = "total_pago_credito", nullable = true)
    private Double totalPagoCredito;

    @Column(name = "total_pago_dinheiro", nullable = true)
    private Double totalPagoDinheiro;

}
