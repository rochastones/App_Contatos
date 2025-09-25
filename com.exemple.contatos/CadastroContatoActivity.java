package com.example.contatos;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contatos.dao.ContatoDAO;
import com.example.contatos.model.Contato;

/**
 Activity responsável por inserir, alterar ou excluir contatos.
 O comportamento é definido pela "acao" recebida via Intent:
 "inserir" → insere novo contato.
 "alterar" → altera contato existente.
 excluir" → exclui contato existente.
 */
public class CadastroContatoActivity extends AppCompatActivity {

    private EditText etNome; // Nome do contato (usado em todas as ações)
    private EditText etNovoNome; // Novo nome (visível apenas para alteração)
    private Button btnSalvar; // Executa a ação escolhida

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_contato);

        etNome = findViewById(R.id.etNome);
        etNovoNome = findViewById(R.id.etNovoNome);
        btnSalvar = findViewById(R.id.btnSalvar);

        // Recupera qual ação deve ser executada (inserir/alterar/excluir)
        String acao = getIntent().getStringExtra("acao");
        if ("alterar".equals(acao)) {
            etNovoNome.setVisibility(View.VISIBLE); // mostra campo do novo nome
        } else {
            etNovoNome.setVisibility(View.GONE); // oculta se não for alterar
        }

        btnSalvar.setOnClickListener(v -> salvarContato(acao));
    }

    //Executa a ação de salvar de acordo com o parâmetro "acao".
    private void salvarContato(String acao) {
        ContatoDAO dao = new ContatoDAO(CadastroContatoActivity.this);

        try {
            if ("alterar".equals(acao)) {

                // Alterar contato
                String nomeAntigo = etNome.getText().toString().trim();
                String nomeNovo = etNovoNome.getText().toString().trim();

                if (nomeAntigo.isEmpty()) {
                    etNome.setError("Digite o nome existente");
                    return;
                }
                if (nomeNovo.isEmpty()) {
                    etNovoNome.setError("Digite o novo nome");
                    return;
                }

                boolean atualizado = dao.alterarContato(nomeAntigo, nomeNovo);
                if (!atualizado) {
                    etNome.setError("Contato não encontrado");
                    return;
                }

            } else if ("excluir".equals(acao)) {

                // Excluir contato
                String nome = etNome.getText().toString().trim();
                if (nome.isEmpty()) {
                    etNome.setError("Digite o nome do contato a excluir");
                    return;
                }
                dao.excluirContatoPorNome(nome);

            } else {
                // Inserção contato
                String nome = etNome.getText().toString().trim();
                if (nome.isEmpty()) {
                    etNome.setError("Nome é obrigatório");
                    return;
                }

                //Verifica de duplicidade antes de inserir
                if (dao.contatoExiste(nome)) {
                    etNome.setError("Esse contato já existe!");
                    return;
                }

                dao.inserirContato(new Contato(0, nome));
            }

            // Se chegou aqui, operação foi realizada com sucesso
            setResult(RESULT_OK);
            finish();

        } finally {
            dao.fechar();
        }
    }
}

