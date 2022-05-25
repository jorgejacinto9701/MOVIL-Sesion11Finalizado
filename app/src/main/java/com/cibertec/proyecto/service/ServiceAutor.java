package com.cibertec.proyecto.service;

import com.cibertec.proyecto.entity.Autor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServiceAutor {

    @POST("autor")
    public Call<Autor> insertAutor(@Body Autor obj);

    @GET("autor/porNombre/{nombre}")
    public Call<List<Autor>> consulta(@Path("nombre") String nombre);

    Call<Autor> registraAutor(Autor a);
}


