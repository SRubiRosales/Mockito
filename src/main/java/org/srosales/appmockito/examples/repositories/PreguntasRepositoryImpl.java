package org.srosales.appmockito.examples.repositories;

import org.srosales.appmockito.examples.Datos;

import java.util.List;

public class PreguntasRepositoryImpl implements PreguntaRepository {
    @Override
    public List<String> findQuestionsByExamId(Long id) {
        System.out.println("PreguntasRepositoryImpl.findQuestionsByExamId");
        return Datos.PREGUNTAS;
    }

    @Override
    public void saveSeveral(List<String> preguntas) {
        System.out.println("PreguntasRepositoryImpl.saveSeveral");
    }
}
