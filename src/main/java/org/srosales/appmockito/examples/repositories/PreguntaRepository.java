package org.srosales.appmockito.examples.repositories;

import java.util.List;

public interface PreguntaRepository {
    List<String> findQuestionsByExamId(Long id);
    void saveSeveral(List<String> preguntas);
}
