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
import com.cibertec.proyecto.entity.Alumno;


import java.util.List;

public class AlumnoAdapter extends ArrayAdapter<Alumno>  {

    private Context context;
    private List<Alumno> lista;

    public AlumnoAdapter(@NonNull Context context, int resource, @NonNull List<Alumno> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View row=inflater.inflate(com.cibertec.proyecto.R.layout.activity_alumno_crud_item,parent,false);

        Alumno obj=lista.get(position);

        TextView txtID=row.findViewById(R.id.idItemIdAlumno);
        txtID.setText(String.valueOf(obj.getIdAlumno()));

        TextView txtNombres=row.findViewById(R.id.idItemNombresAlumno);
        txtNombres.setText(String.valueOf(obj.getNombres()));

        TextView  txtApellidos = row.findViewById(R.id.idItemApellidosAlumno);
        txtApellidos.setText(String.valueOf(obj.getApellidos()));

        TextView  txtDni = row.findViewById(R.id.idItemDniAlumno);
        txtDni.setText(String.valueOf(obj.getDni()));

        return row;


    }
}


