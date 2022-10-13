package org.srosales.appmockito.examples.services;

import org.junit.jupiter.api.Test;
import org.srosales.appmockito.examples.models.Examen;
import org.srosales.appmockito.examples.repositories.ExamenRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExamenServiceImplTest {

    @Test
    void findByName() {
        ExamenRepository repository = mock(ExamenRepository.class);//Crea una simulacion de la implementacion del repositorio
        ExamenService service = new ExamenServiceImpl(repository);
        List<Examen> examenes = Arrays.asList(new Examen(5L, "Matemáticas"),
                new Examen(6L, "Ciencias"),
                new Examen(7l, "Historia"));
        //Simulacion del comportamiento
        when(repository.findAll()).thenReturn(examenes);
        Optional<Examen> examen = service.findByName("Matemáticas");

        assertTrue(examen.isPresent());
        assertEquals(5L, examen.get().getId());
        assertEquals("Matemáticas", examen.get().getNombre());
    }

    @Test
    void findByNameListaVacia() {
        ExamenRepository repository = mock(ExamenRepository.class);//Crea una simulacion de la implementacion del repositorio
        ExamenService service = new ExamenServiceImpl(repository);
        List<Examen> examenes = Collections.emptyList();
        //Simulacion del comportamiento
        when(repository.findAll()).thenReturn(examenes);
        Optional<Examen> examen = service.findByName("Matemáticas");

        assertTrue(examen.isPresent());
        assertEquals(5L, examen.get().getId());
        assertEquals("Matemáticas", examen.get().getNombre());
    }
}