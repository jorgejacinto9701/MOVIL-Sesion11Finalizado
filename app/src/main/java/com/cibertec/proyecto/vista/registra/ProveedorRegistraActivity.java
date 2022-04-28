package com.cibertec.proyecto.vista.registra;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.entity.Proveedor;
import com.cibertec.proyecto.entity.Revista;
import com.cibertec.proyecto.service.ServiceProveedor;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.FunctionUtil;
import com.cibertec.proyecto.util.NewAppCompatActivity;
import com.cibertec.proyecto.util.ValidacionUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProveedorRegistraActivity extends NewAppCompatActivity {

    EditText txtRazonSocial, txtRuc,txtGerente,txtDireccion,txtTelefono,txtFechaFundacion;
    Spinner spnPais;
    Button btnRegistrar;
    ServiceProveedor rest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_proveedor_registra);

        rest = ConnectionRest.getConnection().create(ServiceProveedor.class);

        txtRazonSocial = findViewById(R.id.txtRegProvRazonSocial);
        txtRuc = findViewById(R.id.txtRegProvRuc);
        txtGerente = findViewById(R.id.txtRegProvGerente);
        txtDireccion = findViewById(R.id.txtRegProvDirec);
        txtTelefono = findViewById(R.id.txtRegProvTelef);
        spnPais = findViewById(R.id.spnRegProvPais);
        txtFechaFundacion = findViewById(R.id.txtRegProvFechaFundacion);
        btnRegistrar = findViewById(R.id.btnRegProvEnviar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String raz = txtRazonSocial.getText().toString();
                String ruc = txtRuc.getText().toString();
                String gere = txtGerente.getText().toString();
                String dire = txtDireccion.getText().toString();
                String telef = txtTelefono.getText().toString();
                String pa = spnPais.getSelectedItem().toString();
                String fec = txtFechaFundacion.getText().toString();

                if (!raz.matches(ValidacionUtil.TEXTO)){
                    mensajeAlert("La razon social es de 2 a 20 caracteres");
                }else if (!ruc.matches(ValidacionUtil.RUC)){
                    mensajeAlert("El RUC son 11 dígitos");
                }else if (!gere.matches(ValidacionUtil.NOMBRE)){
                    mensajeAlert("El Gerente es de 3 a 30 caracteres");
                }else if (!dire.matches(ValidacionUtil.DIRECCION)){
                    mensajeAlert("La direccion es de 3 a 30 caracteres");
                }else if (!telef.matches(ValidacionUtil.TELEF)){
                    mensajeAlert("El telefono son 9 dígitos");
                }else if (spnPais.getSelectedItemPosition() == 0){
                    mensajeAlert("Selecciona un país");
                }else if (!fec.matches(ValidacionUtil.FECHA)){
                    mensajeAlert("La fecha tiene formato yyyy-MM-dd");
                }else{
                    Proveedor obj = new Proveedor();
                    obj.setRazonSocial(raz);
                    obj.setRuc(ruc);
                    obj.setGerente(gere);
                    obj.setDireccion(dire);
                    obj.setTelefono(telef);
                    obj.setPais(pa);
                    obj.setFechaFundacion(txtFechaFundacion.getText().toString());
                    obj.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                    obj.setEstado(FunctionUtil.ESTADO_ACTIVO);
                    registraProveedor(obj);

                }

            }
        });

    }


    public void registraProveedor(Proveedor obj){
        Call<Proveedor> call = rest.registraProveedor(obj);
        call.enqueue(new Callback<Proveedor>() {
            @Override
            public void onResponse(Call<Proveedor> call, Response<Proveedor> response) {
                if (response.isSuccessful()){
                    Proveedor obj = response.body();
                    mensajeAlert("Se registró la Editorial : " + obj.getIdProveedor() + " => "+ obj.getFechaRegistro());
                }else{
                    mensajeAlert("Error de acceso al servicio");
                }
            }

            @Override
            public void onFailure(Call<Proveedor> call, Throwable t) {
                mensajeAlert("Error en el servicio Rest :" + t.getMessage());
            }
        });
    }



}