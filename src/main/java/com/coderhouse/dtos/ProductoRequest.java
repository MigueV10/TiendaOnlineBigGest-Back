package com.coderhouse.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductoRequest {
     public String  titulo;
    public String  descripcion;
    public Double  precio;
    public Integer stock;
    public String  imageUrl;    // DataURL base64 O una URL http(s)
    public Long    categoriaId; // opcional 
}
