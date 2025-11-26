package com.coderhouse.models;


import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="ventas")
@Schema(description = "Modelo de Venta", title = "Modelo Final de Ventas")
public class Venta {
	
	@Schema(description = "ID de la categoria", requiredMode= Schema.RequiredMode.REQUIRED, example = "1")
	@Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	 @JsonManagedReference
	    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	    private List<DetalleVenta> detalles;
	    @Schema(description = "Hora Local", requiredMode= Schema.RequiredMode.REQUIRED, example = "2025/02/07")
	    private LocalDateTime fecha;
	    @Schema(description = "Precio Total del Ticket Generado", requiredMode= Schema.RequiredMode.REQUIRED, example = "2340.90")
	    private double precioTotal;
	    @Schema(description = "Cantidad de Productos", requiredMode= Schema.RequiredMode.REQUIRED, example = "3")
	    private int cantidadProductos;
	
}
