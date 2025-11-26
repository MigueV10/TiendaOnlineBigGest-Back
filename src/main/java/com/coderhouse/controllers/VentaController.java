package com.coderhouse.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coderhouse.dtos.VentaRequestDTO;
import com.coderhouse.models.Venta;
import com.coderhouse.services.VentaServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Venta", description = "Manejo de Ventas")
@RestController 
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaServices ventaService;

    @Operation(summary = "Obtener Lista de Ventas")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Lista de ventas obtenida correctamente",
    				content = {
    						@Content(mediaType = "application/json", schema = @Schema(implementation = Venta.class))
    				}),
    		@ApiResponse(responseCode = "404", description = "Error al intentar  obtener las ventas",
    				content = @Content ),
    		@ApiResponse(responseCode = "500", description = "Error interno del servidor",
			content = @Content )
    })
    @GetMapping
    public ResponseEntity<List<Venta>> getAllVentas() {
        try {
            List<Venta> ventas = ventaService.getAllVentas();
            return ResponseEntity.ok(ventas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Obtener una Venta por su ID")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "205", description = "Venta Obtenida Correctamente",
    				content = {
    						@Content(mediaType = "application/json", schema = @Schema(implementation = Venta.class))
    				}),
    		@ApiResponse(responseCode = "404", description = "Error al obtener la venta",
    				content = @Content ),
    		@ApiResponse(responseCode = "500", description = "Error interno del servidor",
			content = @Content )
    })
    @GetMapping("/{id}")
    public ResponseEntity <Venta> getVentaById(@PathVariable Long id) {
        try {
            Venta venta = ventaService.getVentaById(id);
            return ResponseEntity.ok(venta);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Creacion de venta correctamente")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "201", description = "Venta Creada Correctamente",
    				content = {
    						@Content(mediaType = "application/json", schema = @Schema(implementation = Venta.class))
    				}),
    		@ApiResponse(responseCode = "404", description = "Error al crear la venta",
			content = @Content ),
    		@ApiResponse(responseCode = "500", description = "Error interno del servidor",
			content = @Content )
    })
    @PostMapping
	public ResponseEntity <?> createVenta(@RequestBody VentaRequestDTO ventaRequestDTO) {
		try {
			Venta venta = ventaService.createVenta(ventaRequestDTO);
			return ResponseEntity.ok(venta);//ok se creo la venta
		} catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();//404
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno: "+ e.getMessage());
		}
	}

    @Operation(summary = "Eliminar UNA Venta por su ID")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "204", description = "Venta Eliminada Correctamente",
    				content = {
    						@Content(mediaType = "application/json", schema = @Schema(implementation = Venta.class))
    				}),
    		@ApiResponse(responseCode = "404", description = "Error al eliminar la venta",
    				content = @Content ),
    		@ApiResponse(responseCode = "500", description = "Error interno del servidor",
			content = @Content )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenta(@PathVariable Long id) {
        try {
            ventaService.deleteVenta(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Actualizacion de venta correctamente por su ID")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Venta Actualizada Correctamente",
    				content = {
    						@Content(mediaType = "application/json", schema = @Schema(implementation = Venta.class))
    				}),
    		@ApiResponse(responseCode = "404", description = "Error al actualizar la venta",
    				content = @Content ),
    		@ApiResponse(responseCode = "500", description = "Error interno del servidor",
			content = @Content )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Venta> updateVenta(@PathVariable Long id, @RequestBody Venta ventaDetails) {
        try {
            Venta updatedVenta = ventaService.updateVenta(id, ventaDetails);
            return ResponseEntity.ok(updatedVenta);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}