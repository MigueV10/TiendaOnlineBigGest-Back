package com.coderhouse.repositories;
import com.coderhouse.models.ProductoVenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoVentaRepository extends JpaRepository<ProductoVenta, Long> {
}