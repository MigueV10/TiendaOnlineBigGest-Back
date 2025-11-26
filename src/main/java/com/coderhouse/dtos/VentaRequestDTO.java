package com.coderhouse.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaRequestDTO {

    private ClienteRequest cliente;
    private List<Linea> lineas;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClienteRequest {
        private Long clienteId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Linea {
        private int cantidad;
        private ProductoRequest producto;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductoRequest {
        private Long productoId;
    }
}
