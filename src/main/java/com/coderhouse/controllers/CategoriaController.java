package com.coderhouse.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coderhouse.models.Categoria;
import com.coderhouse.models.Venta;
import com.coderhouse.services.CategoriaServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/categoria")
@Tag(name = "Categoria", description  = "Categoria Manejador Sistematica")
public class CategoriaController {
	 @Autowired
	    private CategoriaServices categoriaService;

	 @Operation(summary = "Listar Categorias")
	    @ApiResponses(value = {
	    		@ApiResponse(responseCode = "200", description = "Listar Categorias Correctamente",
	    				content = {
	    						@Content(mediaType = "application/json", schema = @Schema(implementation = Venta.class))
	    				}),
	    		@ApiResponse(responseCode = "404", description = "Error al listar las categorias",
	    				content = @Content ),
	    		@ApiResponse(responseCode = "500", description = "Error interno del servidor",
				content = @Content (mediaType = "aplication/json",
	    				schema = @Schema(implementation = ErrorResponse.class)))
	    })
	    @GetMapping
	    public List<Categoria> getAllCategorias() {
	        return categoriaService.getAllCategorias();
	    }
	 @Operation(summary = "Listar Categorias por su ID")
	    @ApiResponses(value = {
	    		@ApiResponse(responseCode = "200", description = "Categorias listadas Correctamente",
	    				content = {
	    						@Content(mediaType = "application/json", schema = @Schema(implementation = Venta.class))
	    				}),
	    		@ApiResponse(responseCode = "404", description = "Error al listar las categorias",
	    				content = @Content ),
	    		@ApiResponse(responseCode = "500", description = "Error interno del servidor",
				content = @Content (mediaType = "aplication/json",
	    				schema = @Schema(implementation = ErrorResponse.class)))
	    })
	    @GetMapping("/{id}")
	    public ResponseEntity<Categoria> getCategoriaById(@PathVariable Long id) {
	        try {
	            Categoria categoria = categoriaService.getCategoriaById(id);
	            return ResponseEntity.ok(categoria);
	        } catch (IllegalArgumentException e) {
	            return ResponseEntity.notFound().build();
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    }
	 @Operation(summary = "Creacion de una Categoria")
	    @ApiResponses(value = {
	    		@ApiResponse(responseCode = "201", description = "Categoria Creada Correctamente",
	    				content = {
	    						@Content(mediaType = "application/json", schema = @Schema(implementation = Venta.class))
	    				}),
	    		@ApiResponse(responseCode = "404", description = "Error al crear la categoria",
	    				content = @Content ),
	    		@ApiResponse(responseCode = "500", description = "Error interno del servidor",
				content = @Content (mediaType = "aplication/json",
	    				schema = @Schema(implementation = ErrorResponse.class)))
	    })
	    @PostMapping
	    public ResponseEntity<Categoria> createCategoria(@RequestBody Categoria categoria) {
	        try {
	            Categoria createdCategoria = categoriaService.createCategoria(categoria);
	            return ResponseEntity.status(HttpStatus.CREATED).body(createdCategoria);
	        } catch (IllegalArgumentException e) {
	            return ResponseEntity.notFound().build();
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    }

	 @Operation(summary = "Actualizacion de categoria por su ID")
	    @ApiResponses(value = {
	    		@ApiResponse(responseCode = "200", description = "Producto actualizada Correctamente",
	    				content = {
	    						@Content(mediaType = "application/json", schema = @Schema(implementation = Venta.class))
	    				}),
	    		@ApiResponse(responseCode = "404", description = "Error al actualizar la categoria",
	    				content = @Content ),
	    		@ApiResponse(responseCode = "500", description = "Error interno del servidor",
				content = @Content (mediaType = "aplication/json",
	    				schema = @Schema(implementation = ErrorResponse.class)))
	    })
	    @PutMapping("/{id}")
	    public ResponseEntity<Categoria> updateCategoria(@PathVariable Long id, @RequestBody Categoria categoriaDetails) {
	        try {
	            Categoria updateCategoria = categoriaService.updateCategoria(id, categoriaDetails);
	            return ResponseEntity.status(HttpStatus.CREATED).body(updateCategoria);
	        } catch (IllegalArgumentException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    }
	 
	 
	 @Operation(summary = "ELiminar una Categoria por SU ID")
	    @ApiResponses(value = {
	    		@ApiResponse(responseCode = "204", description = "Categoria eliminado Correctamente por su ID",
	    				content = {
	    						@Content(mediaType = "application/json", schema = @Schema(implementation = Venta.class))
	    				}),
	    		@ApiResponse(responseCode = "404", description = "Error AL eliminar Categoria",
	    				content = @Content ),
	    		@ApiResponse(responseCode = "500", description = "Error interno del servidor",
				content = @Content (mediaType = "aplication/json",
	    				schema = @Schema(implementation = ErrorResponse.class)))
	    })
	    @DeleteMapping("/{id}")
	    public ResponseEntity<?> deleteCategoriaById(@PathVariable Long id) {
	        try {
	        	categoriaService.deleteCategoriaById(id);
	        	return ResponseEntity.ok("Categoria eliminada correctamente...!");
	        }catch (DataIntegrityViolationException e) {
	        	return ResponseEntity.status(HttpStatus.CONFLICT).
	        			body("No se puede eliminar la categoria por que esta asignada a uno o mas producto");
	            //if(categoriaService.existCategoriaById(id)) {
	               //categoriaService.deleteCategoriaById(id);
	                //return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	            //} else {
	             //   return ResponseEntity.notFound().build();
	            //}
	        } catch (Exception e) {
	        	e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno: "+ e.getMessage());
	        }
	    }
}
