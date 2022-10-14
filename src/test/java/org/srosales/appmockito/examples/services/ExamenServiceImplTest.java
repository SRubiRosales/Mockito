package org.srosales.appmockito.examples.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.srosales.appmockito.examples.models.Examen;
import org.srosales.appmockito.examples.repositories.ExamenRepository;
import org.srosales.appmockito.examples.repositories.PreguntaRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExamenServiceImplTest {

    ExamenRepository repository;
    ExamenService service;
    PreguntaRepository preguntaRepository;

    @BeforeEach
    void setUp() {
        repository = mock(ExamenRepository.class);//Crea una simulacion de la implementacion del repositorio
        preguntaRepository = mock(PreguntaRepository.class);
        service = new ExamenServiceImpl(repository, preguntaRepository);
    }

    @Test
    void findByName() {
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
        List<Examen> examenes = Collections.emptyList();
        //Simulacion del comportamiento
        when(repository.findAll()).thenReturn(examenes);
        Optional<Examen> examen = service.findByName("Matemáticas");

        assertFalse(examen.isPresent());
    }
}