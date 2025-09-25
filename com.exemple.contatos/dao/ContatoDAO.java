package com.example.contatos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.contatos.model.Contato;
import com.example.contatos.sqlite.DbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 Classe DAO (Data Access Object).
 Responsável por todas as operações relacionadas ao banco de dados SQLite
 da aplicação, tais como: inserir, alterar, excluir e listar contatos.
 */
public class ContatoDAO {
    private SQLiteDatabase database;
    private DbHelper dbHelper;

    /**
     Construtor da classe DAO, inicializa o DbHelper que
     gerencia a conexão e criação do banco de dados.
     */
    public ContatoDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    //Abre o banco de dados em modo de escrita/leitura.
    public void abrir() {
        database = dbHelper.getWritableDatabase();
    }

    //Fecha a conexão com o banco de dados.
    public void fechar() {
        dbHelper.close();
    }

    // Insere um novo contato no banco de dados.
    public long inserirContato(Contato contato) {
        abrir();
        ContentValues values = new ContentValues();
        values.put("nome", contato.getNome());
        long id = database.insert("contatos", null, values);
        fechar();
        return id;
    }

    /**
     Verifica se já existe um contato com mesmo nome no banco.
     Evita duplicidade de contatos.
    */
    public boolean contatoExiste(String nome) {
        abrir();
        Cursor cursor = database.query(
                "contatos",
                new String[]{"id"},
                "nome = ?",
                new String[]{nome},
                null, null, null
        );

        boolean existe = cursor.moveToFirst();
        cursor.close();
        fechar();
        return existe;
    }

     //Atualiza o nome de um contato existente.
    public boolean alterarContato(String nomeAntigo, String nomeNovo) {
        abrir();
        ContentValues values = new ContentValues();
        values.put("nome", nomeNovo);
        int linhasAfetadas = database.update("contatos", values, "nome = ?", new String[]{nomeAntigo});
        fechar();
        return linhasAfetadas > 0; // retorna true se alterou
    }

    // Exclui contato pelo nome
    public void excluirContatoPorNome(String nome) {
        abrir();
        database.delete("contatos", "nome = ?", new String[]{nome});
        fechar();
    }

     //Retorna uma lista com todos os contatos cadastrados.
    public List<Contato> ListarContatos() {
        abrir();
        List<Contato> contatos = new ArrayList<>();
        Cursor cursor = database.query("contatos", null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            Contato contato = new Contato(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("nome"))
            );
            contatos.add(contato);
        }
        cursor.close();
        fechar();
        return contatos;
    }
}
