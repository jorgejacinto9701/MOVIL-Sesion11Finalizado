package com.cibertec.proyecto.service;

import com.cibertec.proyecto.entity.Proveedor;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceProveedor {
    @POST("proveedor")
    public abstract Call<Proveedor> registraProveedor(@Body Proveedor obj);
}
