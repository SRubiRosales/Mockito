package org.srosales.appmockito.examples.repositories;

import org.srosales.appmockito.examples.models.Examen;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExamenRepositoryOtraImpl implements ExamenRepository {
    @Override
    public List<Examen> findAll() {
        try {
            System.out.println("Implementacion de ExamenRepository");
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
