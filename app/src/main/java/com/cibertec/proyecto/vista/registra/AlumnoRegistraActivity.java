package com.cibertec.proyecto.vista.registra;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.service.ServiceAlumno;
import com.cibertec.proyecto.entity.Alumno;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.NewAppCompatActivity;
import com.cibertec.proyecto.util.FunctionUtil;
import com.cibertec.proyecto.util.ValidacionUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlumnoRegistraActivity extends NewAppCompatActivity {

    EditText txtNombres,txtApellidos,txtDni,txtDireccion,txtCorreo,txtFechaNacimiento;
    Button btnRegistrar;
    ServiceAlumno rest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alumno_registra);

        rest =ConnectionRest.getConnection().create(ServiceAlumno.class);


        txtNombres=findViewById(R.id.txtNombres);
        txtApellidos=findViewById(R.id.txtApellidos);
        txtDni=findViewById(R.id.txtDni);
        txtDireccion=findViewById(R.id.txtDireccion);
        txtCorreo=findViewById(R.id.txtCorreo);
        txtFechaNacimiento=findViewById(R.id.txtFechaNacimiento);
        btnRegistrar=findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nom=txtNombres.getText().toString();
                String ape=txtApellidos.getText().toString();
                int dni=Integer.parseInt(txtDni.getText().toString());
                String dir=txtDireccion.getText().toString();
                String corr=txtCorreo.getText().toString();
                String FecNac=txtFechaNacimiento.getText().toString();




                if (!nom.matches(ValidacionUtil.NOMBRE)){
                    mensajeAlert("El nombre es de 3 a 30 caracteres");
                }else if (!ape.matches(ValidacionUtil.NOMBRE)){
                    mensajeAlert("El apellido es de 3 a 30 caracteres");
                }else if (!dir.matches(ValidacionUtil.DIRECCION)){
                    mensajeAlert("La direccion tiene el siguiente formato");
                }else if (!corr.matches(ValidacionUtil.CORREO)){
                    mensajeAlert("El correo tiene el siguiente formato");
                }else if (!FecNac.matches(ValidacionUtil.FECHA)){
                    mensajeAlert("La fecha tiene formato yyyy-MM-dd");
                }else{
                    Alumno obj = new Alumno();

                    obj.setNombres(nom);
                    obj.setApellidos(ape);
                    obj.setDni(dni);
                    obj.setDireccion(dir);
                    obj.setCorreo(corr);
                    obj.setFechaNacimiento(txtFechaNacimiento.getText().toString());
                    obj.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                    obj.setEstado(1);
                    registraAlumno(obj);

                }
            }
        });

    }

    public void registraAlumno(Alumno obj){
        Call<Alumno> call = rest.registraAlumno(obj);
        call.enqueue(new Callback<Alumno>() {
            @Override
            public void onResponse(Call<Alumno> call, Response<Alumno> response) {
                if (response.isSuccessful()){
                    Alumno obj = response.body();
                    mensajeAlert("Se registró el Alumno : " + obj.getIdAlumno() + " => "+ obj.getFechaRegistro());
                }else{
                    mensajeAlert("Error de acceso al servicio");
                }
            }

            @Override
            public void onFailure(Call<Alumno> call, Throwable t) {
                mensajeAlert("Error en el servicio Rest :" + t.getMessage());
            }
        });

    }


}