package com.example.pontosturisticos;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.example.pontosturisticos.adapters.MeuAdapter;
import com.example.pontosturisticos.modelos.PontosTuristicos;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<PontosTuristicos> pTuristicos = new ArrayList<>();
    private ArrayAdapter<PontosTuristicos> arrayAdapterPTuristicos;
    private FusedLocationProviderClient fusedLocClient;
    private static final int CODIGO_REQUISICAO = 123;
    private Location minhaLocalizacao = new Location("Minha localização");

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Verificação da permissão de uso do GPS
        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, CODIGO_REQUISICAO);
        }

        fusedLocClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null)
                {
                    //Double latitude = location.getLongitude();
                    // usar a localização
                    //Toast.makeText(MainActivity.this, latitude.toString(), Toast.LENGTH_LONG).show();
                    minhaLocalizacao.setLatitude(location.getLatitude());
                    minhaLocalizacao.setLongitude(location.getLongitude());
                    criarPTuristico();
                    criarListView();
                }
            }
        });

    }

    private Float calcularDistancia(Double latitudeDestino, Double longitudeDestino)
    {
        Location localizacaoDestino = new Location("Localização de destino");
        localizacaoDestino.setLongitude(longitudeDestino);
        localizacaoDestino.setLatitude(latitudeDestino);

        Float distancia = minhaLocalizacao.distanceTo(localizacaoDestino)/1000;

        return distancia;
    }

    public void criarPTuristico()
    {
        this.pTuristicos.add(new PontosTuristicos("1", "Parque Ecológico", calcularDistancia(-21.98480695, -47.87445318), "pqecologico"));
        this.pTuristicos.add(new PontosTuristicos("2", "Catedral", calcularDistancia(-22.018229, -47.8934227), "catedral"));
        this.pTuristicos.add(new PontosTuristicos("3", "Fazenda Santa Maria do Monjolinho", calcularDistancia(-22.038795, -47.9661216), "fazenda"));
        this.pTuristicos.add(new PontosTuristicos("4", "Paróquia São Sebastião", calcularDistancia(-22.01109315, -47.88724716), "paroquia"));
        this.pTuristicos.add(new PontosTuristicos("5", "Shopping Iguatemi", calcularDistancia(-22.0186133, -47.9165911), "iguatemi"));
        this.pTuristicos.add(new PontosTuristicos("6", "Shopping Passeio", calcularDistancia(-22.00513986, -47.90430933), "passeio"));
    }

    private void criarListView()
    {
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
}
