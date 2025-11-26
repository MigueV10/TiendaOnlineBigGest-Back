package com.coderhouse.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "producto_venta")
@Schema(description = "Modelo de Producto-Venta", title = "Modelo de Producto Venta")
public class ProductoVenta {

	@Schema(description = "ID del detelle de venta", requiredMode= Schema.RequiredMode.REQUIRED, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Schema(description = "Nombre del producto", requiredMode= Schema.RequiredMode.REQUIRED, example = "E-YESOO2")
    private String titulo;
	@Schema(description = "Descripcioon", requiredMode= Schema.RequiredMode.REQUIRED, example = "Teclado Mecanico")
    private String descripcion;
	 @Schema(description = "Precio del producto", requiredMode= Schema.RequiredMode.REQUIRED, example = "1200.90")
    private double precio;
	 @Schema(description = "Stock existente", requiredMode= Schema.RequiredMode.REQUIRED, example = "4")
    private int stock;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}