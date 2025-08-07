package com.distribuidoraferreira.backend.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "produtos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Produto {
    @Id
    @Column(name = "produto_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cod_barras", unique = true)
    private String codBarras;

    @Column(name = "nome")
    private String nome;

    @Column(name = "preco")
    private Double preco;

    @Column(name = "preco_consumo")
    private Double precoConsumo;

    @Column(name = "img")
    private String img;

    @Column(name = "imgID")
    private String imgID;

    @Column(name = "estoque")
    private Integer quantidadeEstoque;

    @Column(name = "estoque_minimo")
    private Integer quantidadeMinimaEstoque;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "categoria", referencedColumnName = "categoria_id", nullable = false)
    private Categoria categoria;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovimentacaoEstoque> movimentacoesEstoque;

    @PrePersist
    public void prePersist() {
        if (this.quantidadeEstoque == null) {
            this.quantidadeEstoque = 0; // Valor padrão
        }
        if (this.quantidadeMinimaEstoque == null) {
            this.quantidadeMinimaEstoque = 0; // Valor padrão
        }
    }

    public void aumentarQuantidadeEstoque(int aumento) {
        int novaQuantidade = getQuantidadeEstoque() + aumento;

        setQuantidadeEstoque(novaQuantidade);
    }

    public void diminuirQuantidadeEstoque(int decrescimo) {
        int novaQuantidade = getQuantidadeEstoque() - decrescimo;

        setQuantidadeEstoque(novaQuantidade);
    }
}
