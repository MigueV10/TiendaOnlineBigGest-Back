package com.coderhouse.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
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
@Table(name="detalle_venta")
@Schema(description = "Modelo de Venta-Detalle", title = "Modelo Final del Detalle De Venta")
public class DetalleVenta {

	@Schema(description = "ID del detelle de venta", requiredMode= Schema.RequiredMode.REQUIRED, example = "1")
	@Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "venta_id")
	private Venta venta;
	
	@Schema(description = "Producto generado para su venta por ID", requiredMode= Schema.RequiredMode.REQUIRED, example = "Teclado Mecanico")
	@ManyToOne
	@JoinColumn(name="producto_venta_id")
	private ProductoVenta producto;
	@Schema(description = "Cantidad en Pesos MXMN", requiredMode= Schema.RequiredMode.REQUIRED, example = "2100")
	private int cantidad;
	@Schema(description = "Precio Unitario", requiredMode= Schema.RequiredMode.REQUIRED, example = "1400.90")
	private double precioUnitario;
	
}
