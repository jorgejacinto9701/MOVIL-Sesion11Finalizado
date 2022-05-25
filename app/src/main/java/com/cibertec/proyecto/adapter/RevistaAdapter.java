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
import com.cibertec.proyecto.entity.Revista;

import java.util.List;

public class RevistaAdapter extends ArrayAdapter<Revista>  {

    private Context context;
    private List<Revista> lista;

    public RevistaAdapter(@NonNull Context context, int resource, @NonNull List<Revista> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_revista_crud_item, parent, false);

        Revista obj = lista.get(position);

        TextView txtID = row.findViewById(R.id.ItemidRevista);
        txtID.setText(String.valueOf(obj.getIdRevista()));

        TextView txtNombre = row.findViewById(R.id.ItemNombre);
        txtNombre.setText(String.valueOf(obj.getNombre()));

        TextView txtPais = row.findViewById(R.id.ItemPaisRevista);
        txtPais.setText(String.valueOf(obj.getPais()));


        return row;
    }
}
