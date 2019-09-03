package com.example.pontosturisticos.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.pontosturisticos.MainActivity;
import com.example.pontosturisticos.R;
import com.example.pontosturisticos.modelos.PontosTuristicos;

import java.util.ArrayList;
import java.util.List;

public class MeuAdapter extends ArrayAdapter<PontosTuristicos>{
    private Context context;
    private List<PontosTuristicos> pTuristicos;

    public MeuAdapter(Context context, ArrayList<PontosTuristicos> list)
    {
        super(context, 0, list);
        this.context = context;
        pTuristicos = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if(listItem==null)
        {
            listItem = LayoutInflater.from(context).inflate(R.layout.layout_pontos_turisticos, parent, false);
        }

        ConstraintLayout constraintLayoutLista = listItem.findViewById(R.id.constraint_layout_pTuristicos);

        PontosTuristicos pTuristicoAtual = pTuristicos.get(position);
        TextView nome = listItem.findViewById(R.id.text_view_titulo_pTuristico);

        // Define o nome
        nome.setText(pTuristicoAtual.getNome());

        TextView distancia = listItem.findViewById(R.id.text_view_distancia_pTuristicos);
        // Define a distancia
        distancia.setText("Distância "+pTuristicoAtual.getDistancia().toString().replace(".",",")+"km");


        ImageView imageViewFoto = listItem.findViewById(R.id.image_view_pTuristicos);
        // Define a Imagem
        //imageViewFoto.setImageResource(R.drawable.+pTuristicoAtual.getImagem());
        imageViewFoto.setImageResource(context.getResources().getIdentifier(pTuristicoAtual.getImagem(), "drawable", context.getPackageName()));

        /*if(Boolean.parseBoolean(tarefaAtual.getStatus().toString()))
        {
            status.setText("");
            //constraintLayout_Lista.setBackground(context, R.color.colorConcluido);
            constraintLayoutLista.setBackgroundColor(context.getResources().getColor(R.color.colorConcluido));
            constraintLayoutLista.invalidate();
        }
        else
        {
            status.setText("Concluir");
            constraintLayoutLista.setBackgroundColor(context.getResources().getColor(R.color.colorNConcluido));
            constraintLayoutLista.invalidate();

        }*/



        return listItem;
    }

}
