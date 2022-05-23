package com.cibertec.proyecto.service;

import com.cibertec.proyecto.entity.Sala;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServiceSala {

    @POST("sala")
    public Call<Sala> insertaSal(@Body Sala obj);

    @GET("sala/porNumero/{numero}")

    public Call<List<Sala>> consulta (@Path("numero") String numero);

}
