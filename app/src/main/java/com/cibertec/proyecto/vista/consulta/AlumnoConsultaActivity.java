package com.cibertec.proyecto.vista.consulta;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.adapter.AlumnoAdapter;
import com.cibertec.proyecto.entity.Alumno;
import com.cibertec.proyecto.service.ServiceAlumno;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlumnoConsultaActivity extends NewAppCompatActivity {

    EditText txtNombres;
    Button btnFiltrar;


    ListView lstAlumno;
    ArrayList<Alumno>data=new ArrayList<Alumno>();
    AlumnoAdapter adaptador;

    ServiceAlumno api;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno_consulta);

        txtNombres=findViewById(R.id.txtRegEdiNombres);
        lstAlumno=findViewById(R.id.lstConsultaAlumno);
        adaptador=new AlumnoAdapter(this,R.layout.activity_alumno_crud_item,data);
        lstAlumno.setAdapter(adaptador);
        api= ConnectionRest.getConnection().create(ServiceAlumno.class);
        btnFiltrar=findViewById(R.id.btnRegEdiEnviar);
        btnFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filtro=txtNombres.getText().toString();
                consulta(filtro);
            }
        });


    }

    public void consulta(String filtro){
        Call<List<Alumno>> call = api.consulta(filtro);
        call.enqueue(new Callback<List<Alumno>>() {
            @Override
            public void onResponse(Call<List<Alumno>> call, Response<List<Alumno>> response) {
                if(response.isSuccessful()){
                    List<Alumno>lstSalida=response.body();
                    data.clear();
                    data.addAll(lstSalida);
                    adaptador.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<Alumno>> call, Throwable t) {
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