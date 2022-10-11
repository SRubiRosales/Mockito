package org.srosales.appmockito.examples.repositories;

import org.srosales.appmockito.examples.models.Examen;

import java.util.Arrays;
import java.util.List;

public class ExamenRepositoryImpl implements ExamenRepository {
    @Override
    public List<Examen> findAll() {
        return Arrays.asList(new Examen(5L, "Matemáticas"),
                new Examen(6L, "Ciencias"),
                new Examen(7l, "Historia"));
    }
}
