package com.cibertec.proyecto.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.entity.Editorial;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class EditorialAdapter extends ArrayAdapter<Editorial>  {

    private Context context;
    private List<Editorial> lista;

    public EditorialAdapter(@NonNull Context context, int resource, @NonNull List<Editorial> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_editorial_crud_item, parent, false);

        final Editorial obj = lista.get(position);

        TextView txtId = row.findViewById(R.id.idItemIdEditorial);
        txtId.setText(String.valueOf(obj.getIdEditorial()));

        TextView txtNombre = row.findViewById(R.id.idItemNombEditorial);
        txtNombre.setText(String.valueOf(obj.getNombre()));

        TextView txtPais = row.findViewById(R.id.idItemPaisEditorial);
        txtPais.setText(String.valueOf(obj.getPais()));

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL rutaImagen = new URL(obj.getDireccion());
                    if (rutaImagen == null){
                        rutaImagen = new URL("https://www.timandorra.com/wp-content/uploads/2016/11/Imagen-no-disponible.png");
                    }
                    InputStream is = new BufferedInputStream(rutaImagen.openStream());
                    Bitmap b = BitmapFactory.decodeStream(is);
                    ImageView vista = row.findViewById(R.id.idItemImagenEditorial);
                    vista.setImageBitmap(b);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

        return row;
    }

}
