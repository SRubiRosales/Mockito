package org.srosales.appmockito.examples.services;

import org.srosales.appmockito.examples.models.Examen;

import java.util.Optional;

public interface ExamenService {
    Optional<Examen> findByName(String nombre);
}
