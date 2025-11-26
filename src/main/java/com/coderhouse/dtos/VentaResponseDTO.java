package com.coderhouse.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaResponseDTO {
    private Long id;
    private ClienteResponse cliente;
    private List<DetalleVentaResponse> detalles;
    private LocalDateTime fecha;
    private double precioTotal;
    private int cantidadProductos;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClienteResponse {
        private Long id;
        private String nombre;
        private String apellido;
        private String email;
        private int ine;
        private String numCliente;
        private int puntosTotales;  // este campo se verá en el JSON
        private LocalDateTime createdAt;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetalleVentaResponse {
        private Long id;
        private ProductoResponse producto;
        private int cantidad;
        private double precioUnitario;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductoResponse {
        private Long id;
        private String titulo;
        private String descripcion;
        private double precio;
        private int stock;
        private CategoriaResponse categoria;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoriaResponse {
        private Long id;
        private String nombre;
    }
}