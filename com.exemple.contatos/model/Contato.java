package com.example.contatos.model;

/**
 Classe de modelo que representa um contato no sistema.
 Cada objeto desta classe corresponde a um registro
 na tabela "contatos" do banco de dados SQLite.
 */
public class Contato {
    // Identificador único do contato (chave primária no banco)
    private int id;

    // Nome do contato
    private String nome;

    /**
     Construtor da classe Contato.
     id identificador único no banco
     nome nome do contato
     */
    public Contato(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    /**
     Retorna o identificador do contato.
     return id
     */
    public int getId() {
        return id;
    }

    /**
     Retorna o nome do contato.
     return nome
     */
    public String getNome() {
        return nome;
    }
}
