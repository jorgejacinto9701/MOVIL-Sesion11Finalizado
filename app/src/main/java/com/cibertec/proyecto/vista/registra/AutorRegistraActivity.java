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
            txtGrado,txtFechanacimiento,txtFecharegistro,txtEstado;
    ServiceAutor rest;
    Button btnRegistro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_autor_registra);

        txtidAutor = findViewById(R.id.txtidAutor);
        txtNombres = findViewById(R.id.txtNombres);
        txtDni = findViewById(R.id.txtDni);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtNacionalidad = findViewById(R.id.txtNacionalidad);
        txtGrado = findViewById(R.id.txtGrado);
        txtFechanacimiento = findViewById(R.id.txtFechaNacimiento);
        txtFecharegistro = findViewById(R.id.txtFechaRegistro);
        txtEstado = findViewById(R.id.txtEstado);
        btnRegistro=findViewById(R.id.btnRegistro);


       rest = ConnectionRest.getConnection().create(ServiceAutor.class);

       btnRegistro.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               int id=Integer.parseInt(txtidAutor.getText().toString());
               String nomb=txtNombres.getText().toString();
               String ape=txtApellidos.getText().toString();
               String dir=txtDireccion.getText().toString();
               String nac=txtNacionalidad.getText().toString();
               String gra=txtGrado.getText().toString();
               String FecNac=txtFechanacimiento.getText().toString();
               String FecReg=txtFecharegistro.getText().toString();
               int dni=Integer.parseInt(txtDni.getText().toString());
               int est=Integer.parseInt(txtEstado.getText().toString());

               if(!nomb.matches(ValidacionUtil.TEXTO)){
                   mensajeAlert("El nombre es de 3 a 10 caracteres");
               }else if(!ape.matches(ValidacionUtil.TEXTO)){
                   mensajeAlert("El apellido es de 4 a 20 caracteres");
               }else if(!dir.matches(ValidacionUtil.DIRECCION)){
                   mensajeAlert("La direccion consiste en el siguiente formato");
               }else if(!nac.matches(ValidacionUtil.TEXTO)){
                   mensajeAlert("Ingrese nacionalidad");
               }else if (FecNac.matches(ValidacionUtil.FECHA)) {
                   mensajeAlert("El orden de la fecha es yy-mm-dd");
               }else if (FecReg.matches(ValidacionUtil.FECHA)) {
                   mensajeAlert("El orden de la fecha es yy-mm-dd");
               } else
               {
                   {
                       Autor A = new Autor();
                       A.setIdAutor(id);
                       A.setNombres(nomb);
                       A.setApellidos(ape);
                       A.setDni(dni);
                       A.setDireccion(dir);
                       A.setNacionalidad(nac);
                       A.setFechaNacimiento(txtFechanacimiento.getText().toString());
                       A.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                       A.setEstado(FunctionUtil.ESTADO_ACTIVO);
                       registrarAutor(A);
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