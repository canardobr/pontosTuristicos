package com.example.pontosturisticos;

import androidx.annotation.NonNull;
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
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ListView listView;
    private List<PontosTuristicos> pTuristicos = new ArrayList<PontosTuristicos>();
    private ArrayAdapter<PontosTuristicos> arrayAdapterPTuristicos;
    private FusedLocationProviderClient fusedLocClient;
    private static final int CODIGO_REQUISICAO = 123;
    private Location minhaLocalizacao = new Location("Minha localização");
    private String nomeBanco = "pontos_turisticos";

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

        listView = findViewById(R.id.list_view_pTuristicos);

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
                    //criarPTuristico();
                    //criarListView();
                    conectarBanco();
                    eventoBanco();
                    //salvarDado();
                }
            }
        });

        //criarPTuristico();


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

        arrayAdapterPTuristicos = new MeuAdapter(MainActivity.this, (ArrayList<PontosTuristicos>) pTuristicos);
        listView.setAdapter(arrayAdapterPTuristicos);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Abrindo a nova activity
               /* Intent intent = new Intent(MainActivity.this, TarefaActivity.class);
                intent.putExtra("TITULO",tarefas.get(i).getNome());
                intent.putExtra("DESCRICAO", tarefas.get(i).getDescricao());
                startActivity(intent);*/
            }
        });
    }

    private void conectarBanco()
    {
        FirebaseApp.initializeApp(MainActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void eventoBanco() {

        databaseReference
                .child(nomeBanco)
                .orderByChild("nome")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        pTuristicos.clear();
                        for(DataSnapshot snapshot: dataSnapshot.getChildren())
                        {
                            PontosTuristicos pTurisco = snapshot.getValue(PontosTuristicos.class);
                            pTuristicos.add(pTurisco);
                        }

                        arrayAdapterPTuristicos = new MeuAdapter(MainActivity.this, (ArrayList<PontosTuristicos>) pTuristicos);
                        listView.setAdapter(arrayAdapterPTuristicos);

                        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                                //apagarDado(tarefas.get(i));
                                return true;
                            }
                        });

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                if(Float.parseFloat(pTuristicos.get(i).getDistancia().toString()) >= 1)
                                {
                                    String distancia = pTuristicos.get(i).getDistancia().toString();
                                    String msg = "Você está há "
                                            + String.format("%.2f", Float.parseFloat(distancia))
                                            .replace(".",",")+"km do destino";

                                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();

                                }
                                else
                                {

                                    Float calc = pTuristicos.get(i).getDistancia() * 1000;
                                    String msg = "Você está há "
                                            + String.format("%.0f",calc)
                                            .replace(".",",")+"m do destino";

                                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();

                                }

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(MainActivity.this, "Erro ao se comunicar com o banco!", Toast.LENGTH_LONG).show();
                    }
                });

    }

    public void salvarDado(View v) {
        PontosTuristicos pTurisco = new PontosTuristicos(UUID.randomUUID().toString(), "Parque Ecológico", calcularDistancia(-21.98480695, -47.87445318), "pqecologico");
        databaseReference.child(this.nomeBanco).child(pTurisco.getId()).setValue(pTurisco);

        pTurisco = new PontosTuristicos(UUID.randomUUID().toString(), "Catedral", calcularDistancia(-22.018229, -47.8934227), "catedral");
        databaseReference.child(this.nomeBanco).child(pTurisco.getId()).setValue(pTurisco);

        pTurisco = new PontosTuristicos(UUID.randomUUID().toString(), "Fazenda Santa Maria do Monjolinho", calcularDistancia(-22.038795, -47.9661216), "fazenda");
        databaseReference.child(this.nomeBanco).child(pTurisco.getId()).setValue(pTurisco);

        pTurisco = new PontosTuristicos(UUID.randomUUID().toString(), "Paróquia São Sebastião", calcularDistancia(-22.01109315, -47.88724716), "paroquia");
        databaseReference.child(this.nomeBanco).child(pTurisco.getId()).setValue(pTurisco);

        pTurisco = new PontosTuristicos(UUID.randomUUID().toString(), "Shopping Iguatemi", calcularDistancia(-22.0186133, -47.9165911), "iguatemi");
        databaseReference.child(this.nomeBanco).child(pTurisco.getId()).setValue(pTurisco);

        pTurisco = new PontosTuristicos(UUID.randomUUID().toString(), "Shopping Passeio", calcularDistancia(-22.00513986, -47.90430933), "passeio");
        databaseReference.child(this.nomeBanco).child(pTurisco.getId()).setValue(pTurisco);


    }
}
