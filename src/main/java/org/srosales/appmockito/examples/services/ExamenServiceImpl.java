package org.srosales.appmockito.examples.services;

import org.srosales.appmockito.examples.models.Examen;
import org.srosales.appmockito.examples.repositories.ExamenRepository;

import java.util.Optional;

public class ExamenServiceImpl implements ExamenService{
    private ExamenRepository examenRepository;

    public ExamenServiceImpl(ExamenRepository examenRepository) {
        this.examenRepository = examenRepository;
    }

    @Override
    public Optional<Examen> findByName(String nombre) {
        return examenRepository.findAll().stream().filter(e -> e.getNombre().contains(nombre)).findFirst();
    }
}
