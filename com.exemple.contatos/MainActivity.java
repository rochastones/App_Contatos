package com.example.contatos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.contatos.adapter.ContatoAdapter;
import com.example.contatos.dao.ContatoDAO;
import com.example.contatos.model.Contato;

import java.util.List;

/**
 Activity principal da aplicação.
  Exibi a lista de contatos cadastrados e oferece opções
 de: inserir, alterar e excluir contatos.
 */
public class MainActivity extends AppCompatActivity {

    private ContatoAdapter contatoAdapter;
    private ListView lvContatos;
    private ContatoDAO contatoDAO;
    private ActivityResultLauncher<Intent> cadastroLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contatoDAO = new ContatoDAO(this);
        lvContatos = findViewById(R.id.lv_contatos);

        // Configura o launcher para abrir CadastroContatoActivity e receber resultado
        cadastroLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {

                            // Atualiza lista após sucesso
                            loadContatos();
                            Toast.makeText(MainActivity.this, "Contato salvo com sucesso!", Toast.LENGTH_SHORT).show();
                        } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                            Toast.makeText(MainActivity.this, "Cadastro cancelado", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        // Butão Inserir Novo Contato
        Button btnNovoContato = findViewById(R.id.btn_novo_contato);
        btnNovoContato.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, CadastroContatoActivity.class);
            cadastroLauncher.launch(i);
        });

        // Botão Alterar Contato
        Button btnAlterarContato = findViewById(R.id.btn_alterar_contato);
        btnAlterarContato.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, CadastroContatoActivity.class);
            i.putExtra("acao", "alterar"); // Passa a ação para a próxima tela
            cadastroLauncher.launch(i);
        });

        // Botão Excluir Contato
        Button btnExcluirContato = findViewById(R.id.btn_excluir_contato);
        btnExcluirContato.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, CadastroContatoActivity.class);
            i.putExtra("acao", "excluir"); // Passa a ação para a próxima tela
            cadastroLauncher.launch(i);
        });

        // Carregar lista inicial de contatos
        loadContatos();
    }

    // Garantir que a lista sempre seja atualizada
    @Override
    protected void onResume() {
        super.onResume();
        loadContatos();
    }
    // Atualiza a ListView com os contatos do banco.
    private void loadContatos() {
        List<Contato> contatos = contatoDAO.ListarContatos();
        if (contatoAdapter == null) {
            contatoAdapter = new ContatoAdapter(contatos, this);
            lvContatos.setAdapter(contatoAdapter);
        } else {
            contatoAdapter.updateData(contatos); // Apenas atualiza os dados
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        contatoDAO.fechar();
    }
}
