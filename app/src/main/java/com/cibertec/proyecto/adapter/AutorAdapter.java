package com.cibertec.proyecto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.entity.Autor;


import java.util.List;

public class AutorAdapter extends ArrayAdapter<Autor>  {

    private Context context;
    private List<Autor> lista;

    public AutorAdapter(@NonNull Context context, int resource, @NonNull List<Autor> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View row=inflater.inflate(R.layout.activity_autor_crud_item,parent,false);

        Autor obj=lista.get(position);

        TextView txtID=row.findViewById(R.id.idItemIdAutor);
        txtID.setText(String.valueOf(obj.getIdAutor()));

        TextView txtNombre=row.findViewById(R.id.idItemNombreAutor);
        txtNombre.setText(String.valueOf(obj.getNombres()));

        TextView  txtApellido = row.findViewById(R.id.idItemApellidoAutor);
        txtApellido.setText(String.valueOf(obj.getApellidos()));

        TextView  txtDni = row.findViewById(R.id.idItemDniAutor);
        txtDni.setText(String.valueOf(obj.getDni()));

        return row;
    }
}
