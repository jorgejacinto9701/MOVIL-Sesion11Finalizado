package com.cibertec.proyecto.vista.crud;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.adapter.EditorialAdapter;
import com.cibertec.proyecto.entity.Editorial;
import com.cibertec.proyecto.service.ServiceEditorial;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditorialCrudListaActivity extends NewAppCompatActivity {

    EditText txtNombre;
    Button btnFiltrar, btnRegistra;


    //Listview y Adaptador
    ListView lstEditorial;
    List<Editorial> data = new ArrayList<Editorial>();
    EditorialAdapter adaptador;

    //COnexion
    ServiceEditorial api;
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editorial_crud_lista);

        txtNombre = findViewById(R.id.txtCrudEdiNombre);
        btnFiltrar = findViewById(R.id.btnCrudEdiFiltrar);

        //construir el listview y adaptador
        lstEditorial = findViewById(R.id.lstCrudEditorial);
        adaptador = new EditorialAdapter(this,R.layout.activity_editorial_crud_item, data);
        lstEditorial.setAdapter(adaptador);

        api = ConnectionRest.getConnection().create(ServiceEditorial.class);

        btnFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filtro = txtNombre.getText().toString();
                consulta(filtro);
            }
        });
    }


    public void consulta(String filtro){
        Call<List<Editorial>> call = api.listaEditoriPorNombre(filtro);
        call.enqueue(new Callback<List<Editorial>>() {
            @Override
            public void onResponse(Call<List<Editorial>> call, Response<List<Editorial>> response) {
                if (response.isSuccessful()){
                    List<Editorial> lstEditorial = response.body();
                    mensajeAlert("Llegaron " + lstEditorial.size() + " elementos ") ;
                    data.clear();
                    data.addAll(lstEditorial);
                    adaptador.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<Editorial>> call, Throwable t) {
                mensajeAlert("ERROR -> Error en la respuesta" + t.getMessage());
            }
        });
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