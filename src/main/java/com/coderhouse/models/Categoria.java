package com.coderhouse.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "categorias")
@Entity
@Schema(description = "Modelo de Categoria", title = "Modelo Final Categorias")
public class Categoria {

	@Schema(description = "ID de la categoria", requiredMode= Schema.RequiredMode.REQUIRED, example = "1")
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Schema(description = "Nombre de la categoria", requiredMode= Schema.RequiredMode.REQUIRED, example = "Teclados")
    @Column(unique = true, nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "categoria", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Producto> productos = new ArrayList<>();
	
}
