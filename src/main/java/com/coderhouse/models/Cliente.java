package com.coderhouse.models;


import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="clientes")
@Schema(description = "Modelo de Cliente", title = "Modelo Final de Cliente")
public class Cliente {
    
	@Schema(description = "ID del cliente", requiredMode= Schema.RequiredMode.REQUIRED, example = "1")
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    
    @Column(name="Nombre")
    @Schema(description = "Nombre del cliente", requiredMode= Schema.RequiredMode.REQUIRED, example = "Miguel Eduardo")
    private String nombre;
    @Schema(description = "Apellido del cliente", requiredMode= Schema.RequiredMode.REQUIRED, example = "Urena Nieto")
    private String apellido;
    @Schema(description = "Email del cliente", requiredMode= Schema.RequiredMode.REQUIRED, example = "miguel109@gmail.com")
    private String email;
    @Column(unique = true, nullable = false)
    @Schema(description = "INE del cliente", requiredMode = Schema.RequiredMode.REQUIRED, example = "21091112")
    private int ine;

    @Column(name = "num_cliente")
    @Schema(description = "Numero de cliente", requiredMode = Schema.RequiredMode.REQUIRED, example = "M  1")
    private String numCliente;
   
    @Column(name= "puntos_totales", nullable = false)
    private int puntosTotales=0;

    @CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false)
    @Schema(description = "Fecha de creación del cliente", requiredMode = Schema.RequiredMode.AUTO, example = "2022-03-13T10:21:27.402135")
    private LocalDateTime createdAt;

//	public int getPuntosTotales() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
   
}