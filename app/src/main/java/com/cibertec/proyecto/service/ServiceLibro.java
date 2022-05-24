package com.cibertec.proyecto.service;

import com.cibertec.proyecto.entity.Libro;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServiceLibro {

    @POST("libro")
    public Call<Libro> insertLibro(@Body Libro obj);

    /**
     * Cl2
     */
    @GET("libro/porTitulo/{titulo}")
    public Call<List<Libro>> consulta (@Path("titulo") String titulo);
}
