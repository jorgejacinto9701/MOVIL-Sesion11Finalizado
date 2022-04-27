package com.cibertec.proyecto.vista.registra;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.entity.Revista;
import com.cibertec.proyecto.service.ServiceRevista;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.FunctionUtil;
import com.cibertec.proyecto.util.NewAppCompatActivity;
import com.cibertec.proyecto.util.ValidacionUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RevistaRegistraActivity extends NewAppCompatActivity {

    EditText txtNombre, txtDirector, txtFrecuencia,txtFechaFundacion;
    Spinner spnPais;
    Button btnRegistrar;
    ServiceRevista rest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_revista_registra);

        rest = ConnectionRest.getConnection().create(ServiceRevista.class);

        txtNombre = findViewById(R.id.txtRegRevNombre);
        txtDirector = findViewById(R.id.txtRegRevDirector);
        txtFrecuencia = findViewById(R.id.txtRegRevFrecuencia);
        spnPais = findViewById(R.id.spnRegRevPais);
        txtFechaFundacion = findViewById(R.id.txtRegRevFechaFundacion);
        btnRegistrar = findViewById(R.id.btnRegEdiEnviar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = txtNombre.getText().toString();
                String dir = txtDirector.getText().toString();
                String fre = txtFrecuencia.getText().toString();
                String pa = spnPais.getSelectedItem().toString();
                String fec = txtFechaFundacion.getText().toString();

                if (!nom.matches(ValidacionUtil.NOMBRE)){
                    mensajeAlert("El nombre es de 3 a 30 caracteres");
                }else if (!dir.matches(ValidacionUtil.TEXTO)){
                    mensajeAlert("El director es de 2 a 20 caracteres");
                }else if (!fre.matches(ValidacionUtil.TEXTO)){
                    mensajeAlert("La frecuencia es de 2 a 20 caracteres");
                }else if (spnPais.getSelectedItemPosition() == 0){
                    mensajeAlert("Selecciona un país");
                }else if (!fec.matches(ValidacionUtil.FECHA)){
                    mensajeAlert("La fecha tiene formato yyyy-MM-dd");
                }else{
                    Revista obj = new Revista();
                    obj.setNombre(nom);
                    obj.setDirector(dir);
                    obj.setFrecuencia(fre);
                    obj.setPais(pa);
                    obj.setFechaFundacion(txtFechaFundacion.getText().toString());
                    obj.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                    obj.setEstado(FunctionUtil.ESTADO_ACTIVO);
                    registraRevista(obj);

                }

            }
        });

    }


    public void registraRevista(Revista obj){
        Call<Revista> call = rest.registraRevista(obj);
        call.enqueue(new Callback<Revista>() {
            @Override
            public void onResponse(Call<Revista> call, Response<Revista> response) {
                if (response.isSuccessful()){
                    Revista obj = response.body();
                    mensajeAlert("Se registró la Editorial : " + obj.getIdRevista() + " => "+ obj.getFechaRegistro());
                }else{
                    mensajeAlert("Error de acceso al servicio");
                }
            }

            @Override
            public void onFailure(Call<Revista> call, Throwable t) {
                mensajeAlert("Error en el servicio Rest :" + t.getMessage());
            }
        });
    }



}