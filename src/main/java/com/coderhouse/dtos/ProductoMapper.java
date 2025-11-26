package com.coderhouse.dtos;

import com.coderhouse.models.Producto;

public class ProductoMapper {
    public static ProductoResponse toResponse(Producto p){
        ProductoResponse r = new ProductoResponse();
        r.id = p.getId();
        r.titulo = p.getTitulo();
        r.descripcion = p.getDescripcion();
        r.precio = p.getPrecio();
        r.stock = p.getStock();
        r.imageUrl = p.getImagenUrl(); // se serializa como "imageUrl"
        if (p.getCategoria()!=null){
            r.categoriaId = p.getCategoria().getId();
            r.categoriaNombre = p.getCategoria().getNombre();
        }
        return r;
    }
}
