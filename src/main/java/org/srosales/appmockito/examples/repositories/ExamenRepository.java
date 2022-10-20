package org.srosales.appmockito.examples.repositories;

import org.srosales.appmockito.examples.models.Examen;

import java.util.List;

public interface ExamenRepository {
    Examen save(Examen examen);
    List<Examen> findAll();
}
