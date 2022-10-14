package org.srosales.appmockito.examples.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.srosales.appmockito.examples.models.Examen;
import org.srosales.appmockito.examples.repositories.ExamenRepository;
import org.srosales.appmockito.examples.repositories.PreguntaRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)//Habilita las anotaciones e inyeccion de dependencias
class ExamenServiceImplTest {

    @Mock
    ExamenRepository repository;
    @Mock
    PreguntaRepository preguntaRepository;
    @InjectMocks
    ExamenServiceImpl service;

    @BeforeEach
    void setUp() {
        //Habilitar uso de anotaciones e inyeccion de dependencias
        //MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByName() {
        //Simulacion del comportamiento
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
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

    @Test
    void testPreguntasExamen() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findQuestionsByExamId(anyLong())).thenReturn(Datos.PREGUNTAS);
        Examen examen = service.findExamByNameWithQuestions("Matemáticas");
        assertEquals(5, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("aritmética"));
    }

    @Test
    void testPreguntasExamenVerify() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findQuestionsByExamId(anyLong())).thenReturn(Datos.PREGUNTAS);
        Examen examen = service.findExamByNameWithQuestions("Matemáticas");
        assertEquals(5, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("aritmética"));
        //Verify
        verify(repository).findAll();//Se invoca el metodo findAll?
        verify(preguntaRepository).findQuestionsByExamId(4L);
    }

    @Test
    void testNoExiteExamenVerify() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        when(preguntaRepository.findQuestionsByExamId(anyLong())).thenReturn(Datos.PREGUNTAS);
        Examen examen = service.findExamByNameWithQuestions("Química");
        assertNull(examen);
        //Verify
        verify(repository).findAll();//Se invoca el metodo findAll?
        verify(preguntaRepository).findQuestionsByExamId(5L);
    }
}