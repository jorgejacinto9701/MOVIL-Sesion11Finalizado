package com.cibertec.proyecto.vista.consulta;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.adapter.LibroAdapter;
import com.cibertec.proyecto.entity.Libro;
import com.cibertec.proyecto.service.ServiceLibro;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LibroConsultaActivity extends NewAppCompatActivity {

    EditText txtTitulo;
    Button btnFiltrar;

    // ListView
    ListView lstLibro;
    ArrayList<Libro> data = new ArrayList<Libro>();
    LibroAdapter adaptador;

    // Coneccion al servicio Rest
    ServiceLibro api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libro_consulta);

        txtTitulo = findViewById(R.id.txtRegEdiTitulo);

        lstLibro = findViewById(R.id.lstConsultaLibro);
        adaptador = new LibroAdapter(this, R.layout.activity_libro_crud_item, data);
        lstLibro.setAdapter(adaptador);

        api = ConnectionRest.getConnection().create(ServiceLibro.class);

        btnFiltrar = findViewById(R.id.btnRegEdiEnviar);
        btnFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filtro = txtTitulo.getText().toString();
                consulta(filtro);
            }
        });
    }

    public void consulta(String filtro){
        Call<List<Libro>> call = api.consulta(filtro);
        call.enqueue(new Callback<List<Libro>>() {
            @Override
            public void onResponse(Call<List<Libro>> call, Response<List<Libro>> response) {
                List<Libro> lstSalida = response.body();
                mensajeAlert("Resulta " + lstSalida.size() + " elementos");
                data.clear();
                data.addAll(lstSalida);
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Libro>> call, Throwable t) {
                mensajeAlert("ERROR -> Error en la respuesta" + t.getMessage());
            }
        });
    }

    public void mensajeAlert(String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

}