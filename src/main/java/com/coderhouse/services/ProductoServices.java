package com.coderhouse.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhouse.dtos.ProductoRequest;
import com.coderhouse.error.AgregarCategoriaException;
import com.coderhouse.models.Categoria;
import com.coderhouse.models.Producto;
import com.coderhouse.repositories.CategoriaRepository;
import com.coderhouse.repositories.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductoServices {
	
	@Autowired
	private ProductoRepository productoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    
	public List<Producto> getAllProductos(){
        return productoRepository.findAll();
    }

	public Producto getProductoById(Long id) {
		return productoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

	}

	 @Transactional
	    public Producto saveProducto(Producto producto){
	        return productoRepository.save(producto);
	    }

	@Transactional
	public Producto createProducto(Producto producto) {
		return productoRepository.save(producto);
	}

	@Transactional
public Producto updateProducto(Long id, ProductoRequest req){
    Producto p = productoRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("No se encuentra producto con ID: " + id));

    if (req.titulo != null)      p.setTitulo(req.titulo);
    if (req.descripcion != null) p.setDescripcion(req.descripcion);
    if (req.precio != null)      p.setPrecio(req.precio);
    if (req.stock != null)       p.setStock(req.stock);
    if (req.imageUrl != null)    p.setImagenUrl(req.imageUrl);

    if (req.categoriaId != null) {
        Categoria cat = categoriaRepository.findById(req.categoriaId)
                .orElseThrow(() -> new IllegalArgumentException("No se encuentra categoria con ID: " + req.categoriaId));
        p.setCategoria(cat);
    }

    return productoRepository.save(p);
}



	//NUEVOS METODOS PARA DTO
	@Transactional
    public Producto createFromDto(ProductoRequest dto){
        Producto p = new Producto();
        applyDto(p, dto);
        return productoRepository.save(p);
    }

    @Transactional
    public Producto updateFromDto(Long id, ProductoRequest dto){
        Producto p = getProductoById(id);
        applyDto(p, dto); // parcial: solo campos != null
        return productoRepository.save(p);
    }

    private void applyDto(Producto p, ProductoRequest dto){
        if (dto.titulo      != null) p.setTitulo(dto.titulo);
        if (dto.descripcion != null) p.setDescripcion(dto.descripcion);
        if (dto.precio      != null) p.setPrecio(dto.precio);
        if (dto.stock       != null) p.setStock(dto.stock);
        if (dto.imageUrl    != null) p.setImagenUrl(dto.imageUrl); // <- CLAVE

        if (dto.categoriaId != null){
            Categoria c = categoriaRepository.findById(dto.categoriaId)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));
            p.setCategoria(c);
        }
    }
	
	 public void deleteProductoById(Long id){
	        if(productoRepository.existsById(id)){
	            productoRepository.deleteById(id);
	        } else {
	            throw new IllegalArgumentException
				("No se encuentra producto con ID: "+id);
	        }
	    }

	 @Transactional
	    public Producto addCategoryToProduct(Long productoId, Long categoriaId)
								throws AgregarCategoriaException {
	        Categoria categoria = categoriaRepository.findById(categoriaId)
	                .orElseThrow(() -> new AgregarCategoriaException
					("No se encuentra categoria con ID: " + categoriaId));
	        Producto producto = productoRepository.findById(productoId)
	                .orElseThrow(() -> new AgregarCategoriaException
					("No se encuentra producto con ID: " + productoId));

	        producto.setCategoria(categoria);

	        return productoRepository.save(producto);
	    }
}