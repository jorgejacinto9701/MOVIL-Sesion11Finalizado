package com.cibertec.proyecto.vista.registra;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.entity.Sala;
import com.cibertec.proyecto.service.ServiceSala;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.FunctionUtil;
import com.cibertec.proyecto.util.NewAppCompatActivity;
import com.cibertec.proyecto.util.ValidacionUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalaRegistraActivity extends NewAppCompatActivity {

    EditText txtNumero, txtPiso,txtCapacidad, txtRecursos, txtFechaSeparacion;
    Button btnEnviar;

    ServiceSala service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_registra);

        txtNumero = findViewById(R.id.txtRegEdiNumero);
        txtPiso = findViewById(R.id.txtRegEdiPiso);
        txtCapacidad = findViewById(R.id.txtRegEdiCapacidad);
        txtRecursos = findViewById(R.id.txtRegEdiRecursos);
        txtFechaSeparacion = findViewById(R.id.txtRegEdiFechaSeparacion);
        btnEnviar = findViewById(R.id.btnRegEdiEnviar);

        service = ConnectionRest.getConnection().create(ServiceSala.class);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = txtNumero.getText().toString();
                String pis = txtPiso.getText().toString();
                String cap = txtCapacidad.getText().toString();
                String rec = txtRecursos.getText().toString();
                String fec = txtFechaSeparacion.getText().toString();

                if (!num.matches(ValidacionUtil.NUMERO)){
                    mensajeAlert("El numero es 3 a 10 caracteres");
                }else if (!pis.matches(ValidacionUtil.PISO)){
                    mensajeAlert("El piso es de 2 caracteres");
                }else if (!cap.matches(ValidacionUtil.CAPACIDAD)){
                    mensajeAlert("La capacidad es de 4 caracteres");
                }else if (!rec.matches(ValidacionUtil.RECURSOS)){
                    mensajeAlert("El numero es 3 a 30 caracteres");
                }else if (!fec.matches(ValidacionUtil.FECHA)){
                    mensajeAlert("La fecha es yyyy-MM-dd");
                }else{

                    Sala obj = new Sala();
                    obj.setNumero(num);
                    obj.setPiso(Integer.parseInt(pis));
                    obj.setCapacidad(Integer.parseInt(cap));
                    obj.setRecursos(rec);
                    obj.setFechaSeparacion(fec);
                    obj.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                    obj.setEstado(1);

                    insertaSala(obj);
                }
            }
        });
    }

    private void insertaSala(Sala obj){

        Call<Sala> call = service.insertaSal(obj);
        call.enqueue(new Callback<Sala>() {
            @Override
            public void onResponse(Call<Sala> call, Response<Sala> response) {
                if (response.isSuccessful()){
                    Sala obj = response.body();
                    mensajeAlert("Se registro la Sala => " + obj.getIdSala() + " - " + obj.getNumero());
                }
            }

            @Override
            public void onFailure(Call<Sala> call, Throwable t) {

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