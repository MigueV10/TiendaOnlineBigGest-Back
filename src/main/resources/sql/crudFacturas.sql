CREATE DATABASE IF NOT EXISTS factu;
use factu;

select * from clientes;
select * from productos;
SELECT * FROM vista_ventas_con_cliente;
select * from categorias;
select * from producto_cliente;
select * from producto_venta;
select * from detalle_venta;
SELECT * FROM productos WHERE id IN (1, 4);
SELECT * FROM producto_venta WHERE id IN (2, 4);
SELECT id, titulo FROM productos WHERE id IN (2, 4);


CREATE OR REPLACE VIEW vista_ventas_con_cliente AS
SELECT
    v.id,
    v.cantidad_productos,
    v.fecha,
    v.precio_total,
    v.cliente_id,
    c.nombre,
    c.apellido
FROM
    ventas v
JOIN
    clientes c ON v.cliente_id = c.id;


-- TRIGER PARA EL HISTORIAL DE VENTAS
CREATE TABLE historial_ventas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    venta_id INT,
    cliente_id INT,
    total DECIMAL(10,2),
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DELIMITER //
CREATE TRIGGER after_insert_venta
AFTER INSERT ON ventas
FOR EACH ROW
BEGIN
  INSERT INTO historial_ventas (venta_id, cliente_id, total)
  VALUES (NEW.id, NEW.cliente_id, NEW.precio_total);
END;
//
DELIMITER ;

-- TRIGER PARA VER LA ELIMINACION DE UN PRODUCTO
CREATE TABLE log_eliminacion_productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    producto_id INT,
    titulo VARCHAR(255),
    eliminado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DELIMITER //
CREATE TRIGGER after_delete_producto
AFTER DELETE ON productos
FOR EACH ROW
BEGIN
  INSERT INTO log_eliminacion_productos (producto_id, titulo)
  VALUES (OLD.id, OLD.titulo);
END;
//
DELIMITER ;

SELECT * FROM historial_ventas;
SELECT * FROM log_eliminacion_productos;