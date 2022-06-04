package com.cibertec.proyecto.vista.crud;

import android.os.Bundle;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.entity.Editorial;
import com.cibertec.proyecto.service.ServiceEditorial;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.NewAppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorialCrudFormularioActivity extends NewAppCompatActivity {
    //COnexion
    ServiceEditorial api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editorial_crud_formulario);

        api = ConnectionRest.getConnection().create(ServiceEditorial.class);
    }

    public void inserta(Editorial obj){
        Call<Editorial> call = api.registraEditorial(obj);
        call.enqueue(new Callback<Editorial>() {
            @Override
            public void onResponse(Call<Editorial> call, Response<Editorial> response) {
                if (response.isSuccessful()){
                    Editorial objSalida = response.body();
                    if (objSalida == null){
                        mensajeAlert("ERROR -> NO se insertó");
                    }else{
                        mensajeAlert("ÉXITO -> Se insertó correctamente : " + objSalida.getIdEditorial());
                    }
                }else{
                    mensajeAlert("ERROR -> Error en la respuesta");
                }
            }
            @Override
            public void onFailure(Call<Editorial> call, Throwable t) {
                mensajeAlert("ERROR -> " +   t.getMessage());
            }
        });
    }

    public void actualiza(Editorial obj){
        Call<Editorial> call = api.actualizaEditorial(obj);
        call.enqueue(new Callback<Editorial>() {
            @Override
            public void onResponse(Call<Editorial> call, Response<Editorial> response) {
                if (response.isSuccessful()){
                    Editorial objSalida = response.body();
                    if (objSalida == null){
                        mensajeToastLong("ERROR -> NO se actualizó");
                    }else{
                        mensajeToastLong("ÉXITO -> Se actualizó correctamente : " + objSalida.getIdEditorial());
                    }
                }else{
                    mensajeAlert("ERROR -> Error en la respuesta");
                }
            }
            @Override
            public void onFailure(Call<Editorial> call, Throwable t) {
                mensajeAlert("ERROR -> " +   t.getMessage());
            }
        });
    }

    public void elimina(int id) {
        Call<Editorial> call = api.eliminaEditorial(id);
        call.enqueue(new Callback<Editorial>() {
            @Override
            public void onResponse(Call<Editorial> call, Response<Editorial> response) {
                if (response.isSuccessful()) {
                    Editorial objSalida = response.body();
                    if (objSalida == null){
                        mensajeAlert("ERROR -> NO se eliminó");
                    }else{
                        mensajeAlert("ÉXITO -> Se eliminó correctamente : " + objSalida.getIdEditorial());
                    }
                }else{
                    mensajeAlert("ERROR -> Error en la respuesta");
                }
            }
            @Override
            public void onFailure(Call<Editorial> call, Throwable t) {
                mensajeAlert("ERROR -> " +   t.getMessage());
            }
        });
    }

}