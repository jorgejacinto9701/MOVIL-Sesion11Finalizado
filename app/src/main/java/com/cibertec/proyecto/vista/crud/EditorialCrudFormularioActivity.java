package com.cibertec.proyecto.vista.crud;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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

public class EditorialCrudFormularioActivity extends NewAppCompatActivity {
    //COnexion
    ServiceEditorial api;

    //Componentes
    TextView txtTitulo, txtTextoEstado;
    EditText txtNombre, txtDireccion, txtFecha;
    Button btnEnviar, btnEliminar;
    Spinner spnPais, spnEstado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editorial_crud_formulario);

        //se obtiene los componentes
        txtTitulo = findViewById(R.id.txtCrudRegistraEditTitulo);
        txtNombre = findViewById(R.id.txtCrudRegistraEdiNombre);
        txtDireccion = findViewById(R.id.txtCrudRegistraEdiDirecccion);
        txtFecha = findViewById(R.id.txtCrudRegistraEdiFechaCreacion);
        btnEnviar = findViewById(R.id.btnCrudRegistraEdiEnviar);
        spnPais = findViewById(R.id.spnCrudRegistraEdiPais);
        spnEstado = findViewById(R.id.spnCrudRegistraEdiEstado);
        btnEliminar = findViewById(R.id.btnCrudRegistraEdiEliminar);
        txtTextoEstado = findViewById(R.id.txtCrudRegistraEdiTextoEstado);

        api = ConnectionRest.getConnection().create(ServiceEditorial.class);

        Bundle extras = getIntent().getExtras();
        String tipo = extras.getString("var_tipo");

        if ("REGISTRAR".equals(tipo)){
            txtTitulo.setText("Mantenimiento Editorial - Registrar");
            btnEnviar.setText("Registrar");
            txtTextoEstado.setVisibility(View.GONE);
            btnEliminar.setVisibility(View.GONE);
            spnEstado.setVisibility(View.GONE);
        }else if ("ACTUALIZAR".equals(tipo)){
            txtTitulo.setText("Mantenimiento Editorial - Actualizar");
            btnEnviar.setText("Actualizar");
            txtTextoEstado.setVisibility(View.VISIBLE);
            btnEliminar.setVisibility(View.VISIBLE);
            spnEstado.setVisibility(View.VISIBLE);

            Editorial objEditorial = (Editorial) extras.get("var_item");
            txtNombre.setText(objEditorial.getNombre());
            txtDireccion.setText(objEditorial.getDireccion());
            txtFecha.setText(objEditorial.getFechaCreacion());

            int pos = FunctionUtil.getIndex(spnPais, objEditorial.getPais());
            spnPais.setSelection(pos);

            spnEstado.setSelection(objEditorial.getEstado() ==1?1:2);
        }

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = txtNombre.getText().toString();
                String dir = txtDireccion.getText().toString();
                String pa = spnPais.getSelectedItem().toString();
                String fec = txtFecha.getText().toString();

                if (!nom.matches(ValidacionUtil.TEXTO)){
                    mensajeAlert("El nombre es de 2 a 20 caracteres");
                }else if (!dir.matches(ValidacionUtil.DIRECCION)){
                    mensajeAlert("La dirección es de 3 a 30 caracteres");
                }else if (spnPais.getSelectedItemPosition() == 0){
                    mensajeAlert("Selecciona un país");
                }else if (!fec.matches(ValidacionUtil.FECHA)){
                    mensajeAlert("La fecha tiene formato yyyy-MM-dd");
                }else{

                    if ("REGISTRAR".equals(tipo)){
                        Editorial obj = new Editorial();
                        obj.setNombre(nom);
                        obj.setDireccion(dir);
                        obj.setPais(pa);
                        obj.setFechaCreacion(txtFecha.getText().toString());
                        obj.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                        obj.setEstado(FunctionUtil.ESTADO_ACTIVO);
                        inserta(obj);
                    } else if ("ACTUALIZAR".equals(tipo)){
                        Editorial obj = (Editorial) extras.get("var_item");
                        obj.setNombre(nom);
                        obj.setDireccion(dir);
                        obj.setPais(pa);
                        obj.setFechaCreacion(txtFecha.getText().toString());
                        obj.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                        int posEstado = spnEstado.getSelectedItemPosition();
                        obj.setEstado(posEstado==1?1:0);
                        actualiza(obj);
                    }
                }
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Editorial objEditorial = (Editorial) extras.get("var_item");
                elimina(objEditorial.getIdEditorial());

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
                        mensajeToastLong("ERROR -> NO se insertó");
                    }else{
                        mensajeToastLong("ÉXITO -> Se insertó correctamente : " + objSalida.getIdEditorial());
                    }
                }else{
                    mensajeToastLong("ERROR -> Error en la respuesta");
                }
            }
            @Override
            public void onFailure(Call<Editorial> call, Throwable t) {
                mensajeToastLong("ERROR -> " +   t.getMessage());
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
                    mensajeToastLong("ERROR -> Error en la respuesta");
                }
            }
            @Override
            public void onFailure(Call<Editorial> call, Throwable t) {
                mensajeToastLong("ERROR -> " +   t.getMessage());
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
                        mensajeToastLong("ERROR -> NO se eliminó");
                    }else{
                        mensajeToastLong("ÉXITO -> Se eliminó correctamente : " + objSalida.getIdEditorial());
                    }
                }else{
                    mensajeToastLong("ERROR -> Error en la respuesta");
                }
            }
            @Override
            public void onFailure(Call<Editorial> call, Throwable t) {
                mensajeToastLong("ERROR -> " +   t.getMessage());
            }
        });
    }

}