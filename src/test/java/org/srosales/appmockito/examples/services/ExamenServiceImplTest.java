package org.srosales.appmockito.examples.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
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

    @Captor
    ArgumentCaptor<Long> captor;

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

    @Test
    void testGuardarExamen() {
        //Given, dado un entorno de prueba
        Examen newExamen = Datos.EXAMEN;
        newExamen.setPreguntas(Datos.PREGUNTAS);

        when(repository.save(any(Examen.class))).then(new Answer<Examen>() {
            Long sequence = 8L;
            @Override
            public Examen answer(InvocationOnMock invocationOnMock) throws Throwable {
                Examen examen = invocationOnMock.getArgument(0);
                examen.setId(sequence++);
                return examen;
            }
        });
        //When, cuando ejecutamos el metodo que queremos probar
        Examen examen = service.save(newExamen);
        assertNotNull(examen.getId());
        assertEquals(8L, examen.getId());
        assertEquals("Física", examen.getNombre());
        //Then
        verify(repository).save(any(Examen.class));
        verify(preguntaRepository).saveSeveral(anyList());
    }

    @Test
    void testManejoException() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES_ID_NULL);
        when(preguntaRepository.findQuestionsByExamId(isNull())).thenThrow(IllegalArgumentException.class);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.findExamByNameWithQuestions("Matemáticas");
        });
        assertEquals(IllegalArgumentException.class, exception.getClass());

        verify(repository).findAll();
        verify(preguntaRepository).findQuestionsByExamId(null);
    }

    @Test
    void testArgumentMatchers() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findQuestionsByExamId(anyLong())).thenReturn(Datos.PREGUNTAS);
        service.findExamByNameWithQuestions("Matemáticas");

        verify(repository).findAll();
        //verify(preguntaRepository).findQuestionsByExamId(argThat(arg -> arg != null && arg.equals(5L)));
        verify(preguntaRepository).findQuestionsByExamId(argThat(arg -> arg != null && arg >= 5L));
        //verify(preguntaRepository).findQuestionsByExamId(eq(5L));
    }

    @Test
    void testArgumentMatchers2() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES_ID_NEGATIVOS);
        when(preguntaRepository.findQuestionsByExamId(anyLong())).thenReturn(Datos.PREGUNTAS);
        service.findExamByNameWithQuestions("Matemáticas");

        verify(repository).findAll();
        verify(preguntaRepository).findQuestionsByExamId(argThat(new MiArgsMatchers()));
    }

    @Test
    void testArgumentMatchers3() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findQuestionsByExamId(anyLong())).thenReturn(Datos.PREGUNTAS);
        service.findExamByNameWithQuestions("Matemáticas");

        verify(repository).findAll();
        verify(preguntaRepository).findQuestionsByExamId(argThat((argument) -> argument != null && argument > 0));
    }

    public static class MiArgsMatchers implements ArgumentMatcher<Long> {
        private Long argument;

        @Override
        public boolean matches(Long argument) {
            this.argument = argument;
            return argument != null && argument > 0;
        }

        @Override
        public String toString() {
            return "Mensaje personalizado de error que imprime Mockito en coso de que falle el test. " +
                    argument + " debe ser un entero positivo";
        }
    }

    @Test
    void testArgumentCaptor() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        service.findExamByNameWithQuestions("Matemáticas");

        //ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        verify(preguntaRepository).findQuestionsByExamId(captor.capture());

        assertEquals(5L, captor.getValue());
    }

    @Test
    void testDoThrow() {
        Examen examen = Datos.EXAMEN;
        examen.setPreguntas(Datos.PREGUNTAS);
        doThrow(IllegalArgumentException.class).when(preguntaRepository).saveSeveral(anyList());

        assertThrows(IllegalArgumentException.class, () -> {
            service.save(examen);
        });
    }

    @Test
    void testDoAnswer() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
//        when(preguntaRepository.findQuestionsByExamId(anyLong())).thenReturn(Datos.PREGUNTAS);
        doAnswer(invocation -> {
            Long id = invocation.getArgument(0);
            return id == 5 ? Datos.PREGUNTAS : null;
        }).when(preguntaRepository).findQuestionsByExamId(anyLong());

        Examen examen = service.findExamByNameWithQuestions("Matemáticas");
        assertEquals(5, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("geometría"));
        assertEquals(5L, examen.getId());
        assertEquals("Matemáticas", examen.getNombre());

        verify(preguntaRepository).findQuestionsByExamId(anyLong());
    }

    @Test
    void testDoAnswerGuardarExamen() {
        //Given, dado un entorno de prueba
        Examen newExamen = Datos.EXAMEN;
        newExamen.setPreguntas(Datos.PREGUNTAS);
        doAnswer(new Answer<Examen>() {
            Long sequence = 8L;
            @Override
            public Examen answer(InvocationOnMock invocationOnMock) throws Throwable {
                Examen examen = invocationOnMock.getArgument(0);
                examen.setId(sequence++);
                return examen;
            }
        }).when(repository).save(any(Examen.class));
        //When, cuando ejecutamos el metodo que queremos probar
        Examen examen = service.save(newExamen);
        assertNotNull(examen.getId());
        assertEquals(8L, examen.getId());
        assertEquals("Física", examen.getNombre());
        //Then
        verify(repository).save(any(Examen.class));
        verify(preguntaRepository).saveSeveral(anyList());
    }
}