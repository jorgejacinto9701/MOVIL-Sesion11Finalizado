package com.cibertec.proyecto.vista.registra;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.entity.Editorial;
import com.cibertec.proyecto.service.ServiceEditorial;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.FunctionUtil;
import com.cibertec.proyecto.util.NewAppCompatActivity;
import com.cibertec.proyecto.util.ValidacionUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorialRegistraActivity extends NewAppCompatActivity {

    EditText txtNombre, txtDireccion, txtFecha;
    Spinner spnPais;
    Button btnRegistrar;
    ServiceEditorial rest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_editorial_registra);

        rest = ConnectionRest.getConnection().create(ServiceEditorial.class);

        txtNombre = findViewById(R.id.txtRegEdiNombre);
        txtDireccion = findViewById(R.id.txtRegEdiDirecccion);
        spnPais = findViewById(R.id.spnRegEdiPais);
        txtFecha = findViewById(R.id.txtRegEdiFechaCreacion);
        btnRegistrar = findViewById(R.id.btnRegEdiEnviar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = txtNombre.getText().toString();
                String dir = txtDireccion.getText().toString();
                String pa = spnPais.getSelectedItem().toString();
                String fec = txtFecha.getText().toString();

                if (!nom.matches(ValidacionUtil.TEXTO)){
                    mensajeAlert("El nombre es de 2 a 20 caracteres");
                }else if (!dir.matches(ValidacionUtil.DIRECCION)){
                    mensajeAlert("La dirección es de 3 a 20 caracteres");
                }else if (spnPais.getSelectedItemPosition() == 0){
                    mensajeAlert("Selecciona un pais");
                }else if (!fec.matches(ValidacionUtil.FECHA)){
                    mensajeAlert("La fecha tiene formato yyyy-MM-dd");
                }else{
                    Editorial obj = new Editorial();
                    obj.setNombre(nom);
                    obj.setDireccion(dir);
                    obj.setPais(pa);
                    obj.setFechaCreacion(txtFecha.getText().toString());
                    obj.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                    obj.setEstado(FunctionUtil.ESTADO_ACTIVO);
                    registraEditorial(obj);

                }

            }
        });

    }


    public void registraEditorial(Editorial obj){
        Call<Editorial> call = rest.registraEditorial(obj);
        call.enqueue(new Callback<Editorial>() {
            @Override
            public void onResponse(Call<Editorial> call, Response<Editorial> response) {
                if (response.isSuccessful()){
                    Editorial obj = response.body();
                    mensajeAlert("Se registró la Editorial : " + obj.getIdEditorial());
                }else{
                    mensajeAlert("Error de acceso al servicio");
                }
            }

            @Override
            public void onFailure(Call<Editorial> call, Throwable t) {
                mensajeAlert("Error en el servicio Rest :" + t.getMessage());
            }
        });
    }


}