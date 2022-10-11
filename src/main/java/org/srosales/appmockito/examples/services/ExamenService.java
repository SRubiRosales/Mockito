package org.srosales.appmockito.examples.services;

import org.srosales.appmockito.examples.models.Examen;

public interface ExamenService {
    Examen findByName(String nombre);
}
