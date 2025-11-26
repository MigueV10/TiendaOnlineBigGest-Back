package com.coderhouse.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@Table(name="productos")
@Schema(description = "Modelo de PRODUCTO",title = "Modelo Final de Producto")
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Producto {

	@Schema(description = "ID del producto", requiredMode= Schema.RequiredMode.REQUIRED, example = "1")
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    @Schema(description = "Nombre del producto", requiredMode= Schema.RequiredMode.REQUIRED, example = "E-YESOO2")
    private String titulo;
    @Schema(description = "Descripcion del producto", requiredMode= Schema.RequiredMode.REQUIRED, example = "Teclado Mecanico")
    private String descripcion;
    @Schema(description = "Precio del producto", requiredMode= Schema.RequiredMode.REQUIRED, example = "1200.90")
    private double precio;
    @Schema(description = "Stock existente", requiredMode= Schema.RequiredMode.REQUIRED, example = "4")
    private int stock;
    
    @JsonProperty("imageUrl")
    @Lob
    @Column(name = "imagen_url")
    private String imagenUrl;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "producto_cliente",
            joinColumns = @JoinColumn(name = "producto_id"),
            inverseJoinColumns = @JoinColumn(name = "cliente_id")
        )

    @JsonIgnore
    private List<Cliente> clientes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private Categoria categoria;

}