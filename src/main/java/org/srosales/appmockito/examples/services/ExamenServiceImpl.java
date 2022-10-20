package org.srosales.appmockito.examples.services;

import org.srosales.appmockito.examples.models.Examen;
import org.srosales.appmockito.examples.repositories.ExamenRepository;
import org.srosales.appmockito.examples.repositories.PreguntaRepository;

import java.util.List;
import java.util.Optional;

public class ExamenServiceImpl implements ExamenService{
    private ExamenRepository examenRepository;
    private PreguntaRepository preguntaRepository;

    public ExamenServiceImpl(ExamenRepository examenRepository, PreguntaRepository preguntaRepository) {
        this.examenRepository = examenRepository;
        this.preguntaRepository = preguntaRepository;
    }

    @Override
    public Optional<Examen> findByName(String nombre) {
        return examenRepository.findAll().stream().filter(e -> e.getNombre().contains(nombre)).findFirst();
    }

    @Override
    public Examen findExamByNameWithQuestions(String nombre) {
        Optional<Examen> examenOptional = findByName(nombre);
        Examen examen = null;
        if (examenOptional.isPresent()) {
            examen = examenOptional.get();
            List<String> preguntas = preguntaRepository.findQuestionsByExamId(examen.getId());
            examen.setPreguntas(preguntas);
        }
        return examen;
    }

    @Override
    public Examen save(Examen examen) {
        if (!examen.getPreguntas().isEmpty()) {
            preguntaRepository.saveSeveral(examen.getPreguntas());
        }
        return examenRepository.save(examen);
    }
}
