package com.example.pontosturisticos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.example.pontosturisticos.adapters.MeuAdapter;
import com.example.pontosturisticos.modelos.PontosTuristicos;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<PontosTuristicos> pTuristicos = new ArrayList<>();
    private ArrayAdapter<PontosTuristicos> arrayAdapterPTuristicos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        criarPTuristico();
        listView = findViewById(R.id.list_view_pTuristicos);

        arrayAdapterPTuristicos = new MeuAdapter(MainActivity.this, (ArrayList<PontosTuristicos>) pTuristicos);
        listView.setAdapter(arrayAdapterPTuristicos);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //faz alguma coisa
                // Exibe uma mensagem
                Toast toast = Toast.makeText(getApplicationContext(), "Você está há "
                        +pTuristicos.get(i).getDistancia().toString().
                        replace(".",",")+"km do destino", Toast.LENGTH_LONG);
                toast.show();

                //Abrindo a nova activity
               /* Intent intent = new Intent(MainActivity.this, TarefaActivity.class);
                intent.putExtra("TITULO",tarefas.get(i).getNome());
                intent.putExtra("DESCRICAO", tarefas.get(i).getDescricao());
                startActivity(intent);*/
            }
        });

    }

    public void criarPTuristico()
    {
        this.pTuristicos.add(new PontosTuristicos("1", "Parque Ecológico", 5.500, "pqecologico"));
        this.pTuristicos.add(new PontosTuristicos("2", "Catedral", 0.800, "catedral"));
        this.pTuristicos.add(new PontosTuristicos("3", "Fazenda Santa Maria do Monjolinho", 11.200, "fazenda"));
        this.pTuristicos.add(new PontosTuristicos("4", "Paróquia São Sebastião", 2.400, "paroquia"));
    }
}
