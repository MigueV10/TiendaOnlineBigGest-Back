package com.coderhouse.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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


import com.coderhouse.models.Cliente;
import com.coderhouse.models.Venta;
import com.coderhouse.services.ClienteServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/clientes") 
@Tag(name = "Cliente", description  = "Gestion O Manejo de Clientes")
public class ClienteController { 

	
	@Autowired
	private ClienteServices clienteServices;
	
	@Operation(summary = "Listar Clientes")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Listado de Clientes Correctamente",
    				content = {
    						@Content(mediaType = "application/json", schema = @Schema(implementation = Venta.class))
    				}),
    		@ApiResponse(responseCode = "404", description = "Error al listar los clientes",
    				content = @Content ),
    		@ApiResponse(responseCode = "500", description = "Error interno del servidor",
			content = @Content (mediaType = "aplication/json",
    				schema = @Schema(implementation = ErrorResponse.class)))
    })
	@GetMapping
	public ResponseEntity <List<Cliente>> getAllClientes(){
		try {
			List<Cliente> clientes = clienteServices.getAllClients();
			return ResponseEntity.ok(clientes);
		}catch(IllegalArgumentException e){
			return ResponseEntity.notFound().build();	
		}catch(Exception e){
				e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	

	@Operation(summary = "Listar Clientes por su ID")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Clientes listados Correctamente",
    				content = {
    						@Content(mediaType = "application/json", schema = @Schema(implementation = Venta.class))
    				}),
    		@ApiResponse(responseCode = "404", description = "Error al listar los Clientes",
    				content = @Content ),
    		@ApiResponse(responseCode = "500", description = "Error interno del servidor",
			content = @Content (mediaType = "aplication/json",
    				schema = @Schema(implementation = ErrorResponse.class)))
    })
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> getClienteById(@PathVariable Long id){
		try {
			Cliente cliente = clienteServices.getClienteById(id);
			return ResponseEntity.ok(cliente);
		}catch(IllegalArgumentException e){
			return ResponseEntity.notFound().build();
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	 @Operation(summary = "Creacion de un Cliente")
	    @ApiResponses(value = {
	    		@ApiResponse(responseCode = "201", description = "Cliente Cread@ Correctamente",
	    				content = {
	    						@Content(mediaType = "application/json", schema = @Schema(implementation = Venta.class))
	    				}),
	    		@ApiResponse(responseCode = "404", description = "Error al crear al cliente",
	    				content = @Content ),
	    		@ApiResponse(responseCode = "500", description = "Error interno del servidor",
				content = @Content (mediaType = "aplication/json",
	    				schema = @Schema(implementation = ErrorResponse.class)))
	    })
	@PostMapping
	public ResponseEntity<Cliente> newCliente(@RequestBody Cliente cliente){
		try {
			Cliente clienteNuevo = clienteServices.newCliente(cliente);
			return ResponseEntity.ok(clienteNuevo);
		 }catch (IllegalArgumentException e) {
			 return ResponseEntity.notFound().build();
		 }catch(Exception e) {
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); 
			}
	}
	 @Operation(summary = "Actualizacion de un Cliente por su ID")
	    @ApiResponses(value = {
	    		@ApiResponse(responseCode = "200", description = "Cliente actualizado Correctamente",
	    				content = {
	    						@Content(mediaType = "application/json", schema = @Schema(implementation = Venta.class))
	    				}),
	    		@ApiResponse(responseCode = "404", description = "Error al actualizar un cliente",
	    				content = @Content ),
	    		@ApiResponse(responseCode = "500", description = "Error interno del servidor",
				content = @Content (mediaType = "aplication/json",
	    				schema = @Schema(implementation = ErrorResponse.class)))
	    })
		@PutMapping("/{id}")
		public ResponseEntity<Cliente> updateClienteById(@PathVariable Long id, @RequestBody Cliente clienteModificado) {
			try {
				Cliente updateCliente = clienteServices.updateClienteById(id, clienteModificado);
				return ResponseEntity.ok(updateCliente);
			} catch (IllegalArgumentException e) {
				return ResponseEntity.notFound().build(); 
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); 
			}

		}

	 @Operation(summary = "ELiminar un Cliente por SU ID")
	    @ApiResponses(value = {
	    		@ApiResponse(responseCode = "204", description = "Cliente eliminado Correctamente por su ID",
	    				content = {
	    						@Content(mediaType = "application/json", schema = @Schema(implementation = Venta.class))
	    				}),
	    		@ApiResponse(responseCode = "404", description = "Error AL eliminar un Cliente",
	    				content = @Content ),
	    		@ApiResponse(responseCode = "500", description = "Error interno del servidor",
				content = @Content (mediaType = "aplication/json",
	    				schema = @Schema(implementation = ErrorResponse.class)))
	    })
		@DeleteMapping("/{id}")
		public ResponseEntity<?> deleteClienteById(@PathVariable Long id) {
			try {
				clienteServices.deleteClienteById(id);
				return ResponseEntity.ok().body("Cliente eliminado correctamente...! ");//204
			}catch (DataIntegrityViolationException e) {
				return ResponseEntity.status(HttpStatus.CONFLICT).
						body("No se puede elminar el cliente por que tiene ventas registradas...!");
			} catch (IllegalArgumentException e) {
				return ResponseEntity.notFound().build(); //400
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Error Interno: " + e.getMessage());//500
			}
		}
		
}
