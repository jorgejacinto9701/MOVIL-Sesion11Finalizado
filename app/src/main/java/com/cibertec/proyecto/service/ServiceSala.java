package com.cibertec.proyecto.service;

import com.cibertec.proyecto.entity.Sala;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceSala {

    @POST("sala")
    public Call<Sala> insertaSal(@Body Sala obj);
}
