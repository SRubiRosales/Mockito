package org.srosales.appmockito.examples.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.srosales.appmockito.examples.models.Examen;
import org.srosales.appmockito.examples.repositories.ExamenRepository;
import org.srosales.appmockito.examples.repositories.ExamenRepositoryImpl;
import org.srosales.appmockito.examples.repositories.PreguntaRepository;
import org.srosales.appmockito.examples.repositories.PreguntasRepositoryImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)//Habilita las anotaciones e inyeccion de dependencias
class ExamenServiceImplSpyTest {

    @Spy
    ExamenRepositoryImpl repository;
    @Spy
    PreguntasRepositoryImpl preguntaRepository;
    @InjectMocks
    ExamenServiceImpl service;

    @Test
    void testSpy() {
        List<String> preguntas = Arrays.asList("álgebra");
        //when(preguntaRepository.findQuestionsByExamId(anyLong())).thenReturn(preguntas);
        doReturn(preguntas).when(preguntaRepository).findQuestionsByExamId(anyLong());

        Examen examen = service.findExamByNameWithQuestions("Matemáticas");
        assertEquals(5, examen.getId());
        assertEquals("Matemáticas", examen.getNombre());
        assertEquals(1, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("álgebra"));

        verify(repository).findAll();
        verify(preguntaRepository).findQuestionsByExamId(anyLong());
    }
}