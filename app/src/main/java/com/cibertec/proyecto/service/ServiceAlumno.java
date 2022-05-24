package com.cibertec.proyecto.service;

import com.cibertec.proyecto.entity.Alumno;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServiceAlumno {

    @GET("alumno/porNombre/{nombres}")
    public Call<List<Alumno>>consulta(@Path("nombres")String nombres);

    @POST("alumno")
    public abstract Call<Alumno> registraAlumno(@Body Alumno obj);



}
