package com.coderhouse.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhouse.dtos.VentaRequestDTO;
import com.coderhouse.error.VentaException;
import com.coderhouse.models.Cliente;
import com.coderhouse.models.DetalleVenta;
import com.coderhouse.models.Producto;
import com.coderhouse.models.ProductoVenta;
import com.coderhouse.models.Venta;
import com.coderhouse.repositories.ClienteRepository;
import com.coderhouse.repositories.ProductoRepository;
import com.coderhouse.repositories.ProductoVentaRepository;
import com.coderhouse.repositories.VentaRepository;

import jakarta.transaction.Transactional;

@Service
public class VentaServices {

	@Autowired
	private VentaRepository ventaRepository;
	@Autowired
	private ProductoRepository productoRepository;
	@Autowired
    ClienteRepository clienteRepository;
	  @Autowired
	  ProductoVentaRepository productoVentaRepository;

	public List<Venta> getAllVentas(){
		return ventaRepository.findAll();
	}
	
	 public Venta getVentaById(Long id) throws VentaException{
	        return ventaRepository.findById(id)
	                .orElseThrow(() -> new VentaException("No se encontró venta con ID: " + id));
	    }
	 @Transactional
		public Venta createVenta(VentaRequestDTO ventaRequestDTO)throws VentaException {
	        Cliente cliente = clienteRepository.findById(ventaRequestDTO.getCliente().getClienteId())
	                .orElseThrow(() -> new VentaException("Cliente no encontrado con ID: " + ventaRequestDTO.getCliente().getClienteId()));

	        Venta venta = new Venta();
	        venta.setCliente(cliente);
	        venta.setDetalles(new ArrayList<>());

	        double precioTotal = 0;
	        int cantidadProductos = 0;

	        for (VentaRequestDTO.Linea linea : ventaRequestDTO.getLineas()) {
	            Producto producto = productoRepository.findById(linea.getProducto().getProductoId())
	                    .orElseThrow(() -> new VentaException("Producto no encontrado con ID: " + linea.getProducto().getProductoId()));

	            if (producto.getTitulo() == null || producto.getTitulo().isEmpty()) {
	                throw new VentaException("El producto con ID " + producto.getId() + " no tiene un título válido.");
	            }
	            if (producto.getStock() < linea.getCantidad()) {
	                throw new VentaException("Stock insuficiente para el producto: " + producto.getTitulo());
	            }

	            producto.setStock(producto.getStock() - linea.getCantidad());//restando el stock
	            producto.getClientes().add(cliente);

	            productoRepository.save(producto);


	            ProductoVenta productoVenta = new ProductoVenta();
	            productoVenta.setTitulo(producto.getTitulo());
	            productoVenta.setDescripcion(producto.getDescripcion());
	            productoVenta.setPrecio(producto.getPrecio());
	            productoVenta.setStock(producto.getStock());
	            productoVenta.setCategoria(producto.getCategoria());

	            productoVentaRepository.save(productoVenta);

	            DetalleVenta detalle = new DetalleVenta();
	            detalle.setVenta(venta);
	            detalle.setProducto(productoVenta);
	            detalle.setCantidad(linea.getCantidad());
	            detalle.setPrecioUnitario(producto.getPrecio());

	            venta.getDetalles().add(detalle);

	            precioTotal += producto.getPrecio() * linea.getCantidad();
	            cantidadProductos += linea.getCantidad();
	        }

	        venta.setPrecioTotal(precioTotal);
	        venta.setCantidadProductos(cantidadProductos);
	        venta.setFecha(LocalDateTime.now());
	        
	        //lógica de puntos por monto de compra
	        int puntosGanados;
	        if (precioTotal > 1500) {
	            puntosGanados = 15;
	        } else if (precioTotal >= 800) {
	            puntosGanados = 10;
	        } else {
	            puntosGanados = 5;
	        }
	        
	        //Sumar los puntos al cliente
	        cliente.setPuntosTotales(cliente.getPuntosTotales() + puntosGanados);
	        clienteRepository.save(cliente); // guardar los nuevos puntos en BD

	     //  Asocia al cliente actualizado antes de guardar la venta
	        venta.setCliente(clienteRepository.findById(cliente.getId()).orElseThrow());

	        return ventaRepository.save(venta);
	        
	        
		}
	 

	@Transactional
	public Venta updateVenta(Long id, Venta ventaDetails) {
		Venta venta = ventaRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("Venta no encontrada con su ID: "+ id));
		venta.setCliente(ventaDetails.getCliente());
		return ventaRepository.save(venta);
	}
	
	public void deleteVenta(Long id) {
		if (ventaRepository.existsById(id)) {
			ventaRepository.deleteById(id);
		} else {
			throw new IllegalArgumentException("Venta no encontrado CON SU ID" + id);
		}
	}
}