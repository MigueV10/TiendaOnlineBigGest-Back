package com.coderhouse.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.multipart.MultipartFile;

import com.coderhouse.dtos.ProductoMapper;
import com.coderhouse.dtos.ProductoRequest;
import com.coderhouse.dtos.ProductoResponse;
import com.coderhouse.dtos.VentaAgregarProductosDTO;
import com.coderhouse.error.AgregarCategoriaException;
import com.coderhouse.models.Producto;
import com.coderhouse.models.Venta;
import com.coderhouse.services.CategoriaServices;
import com.coderhouse.services.ProductoServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "Manejador De Productos")
@PreAuthorize("hasAnyRole('ADMINISTRADOR','EMPLEADO','CLIENTE')")
public class ProductoController {

    @Autowired
    private ProductoServices productoService;

    @SuppressWarnings("unused")
    @Autowired
    private CategoriaServices categoriaService;

    // ========= LISTAR =========
    @Operation(summary = "Listar Productos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Productos listados correctamente",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Venta.class)) }),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Producto>> getAllProductos() {
        try {
            return ResponseEntity.ok(productoService.getAllProductos());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ========= OBTENER POR ID =========
    @Operation(summary = "Obtener un producto por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto obtenido correctamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productoService.getProductoById(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ========= CREAR (UN SOLO POST!) =========
    @Operation(summary = "Creación de un producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Petición inválida", content = @Content)
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','EMPLEADO')")
    public ResponseEntity<ProductoResponse> create(@RequestBody ProductoRequest dto) {
        try {
            Producto p = productoService.createFromDto(dto); // copia imageUrl/imagenUrl dentro
            return ResponseEntity.status(HttpStatus.CREATED).body(ProductoMapper.toResponse(p));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ========= ACTUALIZAR (UN SOLO PUT!) =========
    @Operation(summary = "Actualización de producto por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','EMPLEADO')")
    public ResponseEntity<Producto> updateProducto(@PathVariable Long id, @RequestBody ProductoRequest productoRequest) {
        try {
            Producto updateProducto = productoService.updateProducto(id, productoRequest);
            return ResponseEntity.ok(updateProducto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ========= ELIMINAR =========
    @Operation(summary = "Eliminar un producto por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','EMPLEADO')")
    public ResponseEntity<Void> deleteProductoById(@PathVariable Long id) {
        try {
            productoService.deleteProductoById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ========= ASIGNAR CATEGORÍA =========
    @Operation(summary = "Asignar una categoría a un producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría asignada correctamente")
    })
    @PostMapping("/asignar-categoria")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','EMPLEADO')")
    public ResponseEntity<Producto> addCategoryToProduct(@RequestBody VentaAgregarProductosDTO dto) {
        try {
            Producto upd = productoService.addCategoryToProduct(dto.getProductoId(), dto.getCategoriaId());
            return ResponseEntity.ok(upd);
        } catch (AgregarCategoriaException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ========= SUBIDA DE IMÁGENES =========
    private static final Path UPLOAD_DIR = Paths.get("uploads");

    @PostMapping("/upload")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','EMPLEADO')")
    public Map<String, String> upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (!Files.exists(UPLOAD_DIR)) Files.createDirectories(UPLOAD_DIR);

        String ext = Optional.ofNullable(file.getOriginalFilename())
            .filter(n -> n.contains("."))
            .map(n -> n.substring(n.lastIndexOf(".")))
            .orElse(".png");

        String filename = Instant.now().toEpochMilli() + "_" + UUID.randomUUID() + ext;
        Path target = UPLOAD_DIR.resolve(filename);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        return Map.of("url", "/uploads/" + filename);
    }

    // /api/productos/upload/base64  body: { "dataUrl": "data:image/png;base64,AAA..." }
    public static class Base64Body { public String dataUrl; }

    @PostMapping("/upload/base64")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','EMPLEADO')")
    public Map<String, String> uploadBase64(@RequestBody Base64Body body) throws Exception {
        if (!Files.exists(UPLOAD_DIR)) Files.createDirectories(UPLOAD_DIR);

        String[] parts = body.dataUrl.split(",");
        String meta = parts[0];      // data:image/xxx;base64
        String base64 = parts[1];

        String ext = meta.contains("image/") ? "." + meta.substring(meta.indexOf("image/") + 6, meta.indexOf(";")) : ".png";

        byte[] bytes = Base64.getDecoder().decode(base64);
        String filename = Instant.now().toEpochMilli() + "_" + UUID.randomUUID() + ext;
        Path target = UPLOAD_DIR.resolve(filename);
        Files.write(target, bytes);

        return Map.of("url", "/uploads/" + filename);
    }
}
