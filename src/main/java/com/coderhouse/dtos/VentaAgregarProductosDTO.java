package com.coderhouse.dtos;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Modelo de asignacion de Categoria DTO", title = "Asignacion De Categoria")
public class VentaAgregarProductosDTO {

	@Schema(description = "Producto ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long productoId;

    @Schema(description = "Categoria ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long categoriaId;
}