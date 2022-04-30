package com.cibertec.proyecto.vista.registra;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Callback;
import retrofit2.Call;
import retrofit2.Response;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.entity.Autor;
import com.cibertec.proyecto.util.NewAppCompatActivity;
import com.cibertec.proyecto.service.ServiceAutor;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.FunctionUtil;
import com.cibertec.proyecto.util.ValidacionUtil;


public class AutorRegistraActivity extends NewAppCompatActivity {

    EditText txtidAutor,txtNombres,
    txtApellidos,txtDni,txtDireccion,txtNacionalidad,
            txtGrado,txtFechanacimiento;
    ServiceAutor rest;
    Button btnRegistro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_autor_registra);

        txtNombres = findViewById(R.id.txtRegAutNombres);
        txtDni = findViewById(R.id.txtRegAutDni);
        txtApellidos = findViewById(R.id.txtRegAutApellidos);
        txtDireccion = findViewById(R.id.txtRegAutDireccion);
        txtNacionalidad = findViewById(R.id.txtRegAutNacionalidad);
        txtGrado = findViewById(R.id.txtRegAutGrados);
        txtFechanacimiento = findViewById(R.id.txtRegAutFechaNacimiento);
        btnRegistro=findViewById(R.id.btnRegAutRegistro);


       rest = ConnectionRest.getConnection().create(ServiceAutor.class);

       btnRegistro.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               String nomb=txtNombres.getText().toString();
               String ape=txtApellidos.getText().toString();
               String dir=txtDireccion.getText().toString();
               String nac=txtNacionalidad.getText().toString();
               String gra=txtGrado.getText().toString();
               String FecNac=txtFechanacimiento.getText().toString();
               String dni=txtDni.getText().toString();

               if(!nomb.matches(ValidacionUtil.TEXTO)){
                   mensajeAlert("El nombre es de 3 a 10 caracteres");
               }else if(!ape.matches(ValidacionUtil.TEXTO)){
                   mensajeAlert("El apellido es de 4 a 20 caracteres");
               }else if(!dir.matches(ValidacionUtil.DIRECCION)){
                   mensajeAlert("La direccion consiste en el siguiente formato");
               }else if(!dni.matches(ValidacionUtil.DNI)){
                   mensajeAlert("El DNI no tiene 8 dígitos");
               }else if(!nac.matches(ValidacionUtil.TEXTO)){
                   mensajeAlert("Ingrese nacionalidad");
               }else if (!FecNac.matches(ValidacionUtil.FECHA)) {
                   mensajeAlert("El orden de la fecha es yy-mm-dd");
               } else
               {
                   {
                       Autor obj = new Autor();
                       obj.setNombres(nomb);
                       obj.setApellidos(ape);
                       obj.setDni(dni);
                       obj.setDireccion(dir);
                       obj.setGrado(gra);
                       obj.setNacionalidad(nac);
                       obj.setFechaNacimiento(txtFechanacimiento.getText().toString());
                       obj.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                       obj.setEstado(FunctionUtil.ESTADO_ACTIVO);
                       registrarAutor(obj);
                   }
               }



           }
       });

    }

    public void registrarAutor (Autor A){
        Call<Autor> call = rest.registraAutor(A);
        call.enqueue(new Callback<Autor>() {
            @Override
            public void onResponse(Call<Autor> call, Response<Autor> response) {
                if (response.isSuccessful()) {
                    Autor A = response.body();
                    mensajeAlert("Se registró correctamente : " + A.getIdAutor() + " => " + A.getFechaRegistro());
                } else {
                    mensajeAlert("Error de acceso al servicio");
                }
            }
            @Override
            public void onFailure(Call<Autor> call, Throwable t) {
                mensajeAlert("Error" + t.getMessage());
            }
        });
    }





}