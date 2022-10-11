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
    public Examen findByName(String nombre) {
       Optional<Examen> examenOptional = examenRepository.findAll()
               .stream()
               .filter(e -> e.getNombre().contains(nombre))
               .findFirst();
       Examen examen = null;
       if (examenOptional.isPresent()) {
           examen = examenOptional.get();
       }
       return examen;
    }
}
