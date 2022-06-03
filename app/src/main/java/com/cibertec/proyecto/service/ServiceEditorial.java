package com.cibertec.proyecto.service;

import com.cibertec.proyecto.entity.Editorial;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ServiceEditorial {

    @POST("editorial")
    public abstract Call<Editorial> registraEditorial(@Body Editorial obj);

    @GET("editorial/porNombre/{nombre}")
    public Call<List<Editorial>> listaEditoriPorNombre(@Path("nombre")String nombre);

    @PUT("editorial")
    public abstract Call<Editorial> actualizaEditorial(@Body Editorial obj);

    @DELETE("editorial/{id}")
    public abstract Call<Editorial> eliminaEditorial(@Path("id")int id);

}
