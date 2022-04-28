package com.cibertec.proyecto.vista.registra;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.entity.Libro;
import com.cibertec.proyecto.service.ServiceLibro;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.FunctionUtil;
import com.cibertec.proyecto.util.NewAppCompatActivity;
import com.cibertec.proyecto.util.ValidacionUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LibroRegistraActivity extends NewAppCompatActivity {

    // Componentes
    EditText txtTitle, txtSerie, txtType, txtCreationDate;
    Spinner spnYear, spnCategory;
    Button btnRegister;

    // Coneccion
    ServiceLibro service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libro_registra);

        txtTitle = findViewById(R.id.txtTitle);
        txtSerie = findViewById(R.id.txtSerie);
        txtType = findViewById(R.id.txtType);
        txtCreationDate = findViewById(R.id.txtCreationDate);
        spnYear = findViewById(R.id.spnYear);
        spnCategory = findViewById(R.id.spnCategory);
        btnRegister = findViewById(R.id.btnRegister);

        service = ConnectionRest.getConnection().create(ServiceLibro.class);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = txtTitle.getText().toString();
                String serie = txtSerie.getText().toString();
                String type = txtType.getText().toString();
                String creationDate = txtCreationDate.getText().toString();
                String year = spnYear.getSelectedItem().toString();
                String category = spnCategory.getSelectedItem().toString();

                if(!title.matches(ValidacionUtil.TEXTO)){
                    mensajeAlert("El titulo es de 2 a 20 caracteres");
                }else if(!serie.matches(ValidacionUtil.STOCK)){
                    mensajeAlert("El numero de serie debe ser solo numero");
                }else if(!type.matches(ValidacionUtil.TEXTO)){
                    mensajeAlert("El tipo de libro es de 2 a 20 caracteres");
                }else if(!creationDate.matches(ValidacionUtil.FECHA)){
                    mensajeAlert("La fecha tiene formato yyyy-MM-dd");
                }else if (spnYear.getSelectedItemPosition() == 0) {
                    mensajeAlert("Selecciona un año");
                }else if (spnCategory.getSelectedItemPosition() == 0) {
                    mensajeAlert("Selecciona una categoria");
                } else {
                    Libro l = new Libro();
                    l.setTitulo(title);
                    l.setSerie(serie);
                    l.setTipo(type);
                    l.setFechacreacion(creationDate);
                    l.setAnio(Integer.parseInt(year));
                    l.setCategoria(category);
                    l.setFechaRegistro(FunctionUtil.getFechaActualString());
                    l.setEstado(1);

                    insertLibro(l);
                }
            }
        });

    }


    public void insertLibro(Libro libro){
        Call<Libro> call = service.insertLibro(libro);
        call.enqueue(new Callback<Libro>() {
            @Override
            public void onResponse(Call<Libro> call, Response<Libro> response) {
                if(response.isSuccessful()){
                    Libro libro = response.body();
                    mensajeAlert("Se registro el libro => " + libro.getTitulo());
                }
            }

            @Override
            public void onFailure(Call<Libro> call, Throwable t) {
                mensajeAlert("Error >> " + t.getMessage());
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