package com.coderhouse.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coderhouse.models.Categoria;
import com.coderhouse.repositories.CategoriaRepository;

@ExtendWith(MockitoExtension.class)
class CategoriaServicesTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaServices categoriaServices;

    private Categoria categoria;

    @BeforeEach
    void setup() {
        categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Electrónica");
    }

    @Test
    void getAllCategorias_returnsList() {
        when(categoriaRepository.findAll()).thenReturn(List.of(categoria));

        List<Categoria> result = categoriaServices.getAllCategorias();

        assertEquals(1, result.size());
        assertEquals("Electrónica", result.get(0).getNombre());
    }

    @Test
    void getCategoriaById_found() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));

        Categoria result = categoriaServices.getCategoriaById(1L);

        assertEquals("Electrónica", result.getNombre());
    }

    @Test
    void getCategoriaById_notFound_throws() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> categoriaServices.getCategoriaById(1L));
    }

    @Test
    void createCategoria_saves() {
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        Categoria created = categoriaServices.createCategoria(categoria);

        assertEquals("Electrónica", created.getNombre());
        verify(categoriaRepository).save(eq(categoria));
    }

    @Test
    void updateCategoria_updatesName() {
        Categoria changes = new Categoria();
        changes.setNombre("Hogar");

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        Categoria updated = categoriaServices.updateCategoria(1L, changes);

        assertEquals("Hogar", updated.getNombre());
    }

    @Test
    void deleteCategoriaById_deletes() {
        categoriaServices.deleteCategoriaById(1L);
        verify(categoriaRepository).deleteById(1L);
    }
}
