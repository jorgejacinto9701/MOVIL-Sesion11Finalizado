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
import com.cibertec.proyecto.entity.Sala;

import java.util.List;

public class SalaAdapter extends ArrayAdapter<Sala>  {

    private Context context;
    private List<Sala> lista;

    public SalaAdapter(@NonNull Context context, int resource, @NonNull List<Sala> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_sala_crud_item, parent, false);

        Sala obj = lista.get(position);

        TextView txtID = row.findViewById(R.id.idItemIdSala);
        txtID.setText(String.valueOf(obj.getIdSala()));

        TextView  txtNumero = row.findViewById(R.id.idItemNumeroSala);
        txtNumero.setText(String.valueOf(obj.getNumero()));

        TextView  txtRecursos = row.findViewById(R.id.idItemRecursosSala);
        txtRecursos.setText(String.valueOf(obj.getRecursos()));

        return row;
    }
}
