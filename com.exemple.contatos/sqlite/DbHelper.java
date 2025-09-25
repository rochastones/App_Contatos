package com.example.contatos.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 Classe responsável por gerenciar a criação e atualização do banco de dados SQLite.
 Estende SQLiteOpenHelper, que fornece métodos para abrir, criar e atualizar o banco.
 Aqui definimos a estrutura da tabela que armazena os contatos.
 */
public class DbHelper extends SQLiteOpenHelper {

    // Nome do banco de dados
    private static final String DATABASE_NAME = "contatos_db";

    // Versão do banco de dados (altere quando houver mudanças na estrutura)
    private static final int DATABASE_VERSION = 1;

    // Nome da tabela principal
    public static final String TABLE_CONTATOS_NAME = "contatos";

    // Nome das colunas
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME ="nome";

    private static final String CREATE_TABLE_CONTATOS =
            "CREATE TABLE " + TABLE_CONTATOS_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT)";

    /**
     Construtor do DbHelper.
     context contexto da aplicação (necessário para abrir ou criar o banco).
     */
    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     Metodo chamado automaticamente quando o banco é criado pela primeira vez.
     Aqui definimos a tabela "contatos".
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL (CREATE_TABLE_CONTATOS);
    }

    // Usado para atualizar a estrutura de tabelas.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
