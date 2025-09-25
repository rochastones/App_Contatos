package com.example.contatos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.contatos.R;
import com.example.contatos.model.Contato;

import java.util.List;

/**
 Classe Adapter responsável por ligar os dados (Lista de Contatos)
 com o componente de interface ListView na tela.

 Utiliza o padrão ViewHolder para otimizar performance,
 evitando recriar componentes visuais repetidamente.
 */
public class ContatoAdapter extends BaseAdapter {

    private final List<Contato> contatos; //lista de contatos a exibir
    private final Context context; //contexto da Activity

    /**
     Construtor do Adapter.
     contatos lista de contatos a serem exibidos
     context contexto da aplicação
     */
    public ContatoAdapter(List<Contato> contatos, Context context) {
        this.contatos = contatos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return contatos.size(); // quantidade de itens na lista
    }

    //Necessário para que o MainActivity funcione sem recriar o adapter
    @Override
    public Contato getItem(int position) {
        return contatos.get(position); // retorna contato na posição informada
    }

    @Override
    public long getItemId(int position) {
        return contatos.get(position).getId(); // Retorna o ID real do contato
    }

    //Metodo responsável por criar ou reaproveitar a View que representa cada item da lista.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {

            // Se a view ainda não existe, infla o layout do item
            convertView = LayoutInflater.from(context).inflate(R.layout.lista_contato, parent, false);

            // Cria um novo ViewHolder e associa ao convertView
            holder = new ViewHolder();
            holder.tvNome = convertView.findViewById(R.id.tv_nome_contato_lista);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Define os dados do contato na View
        Contato contato = contatos.get(position);
        holder.tvNome.setText(contato.getNome());

        return convertView;
    }

    //metodo para atualizar a lista
    public void updateData(List<Contato> novosContatos) {
        contatos.clear();
        contatos.addAll(novosContatos);
        notifyDataSetChanged();
    }

    /**
     Classe interna que guarda referências dos componentes de cada item da lista.
     Evita chamadas repetidas de findViewById, melhorando a performance.
     */
    static class ViewHolder {
        TextView tvNome;
    }
}