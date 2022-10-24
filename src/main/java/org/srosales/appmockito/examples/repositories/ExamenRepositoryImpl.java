package org.srosales.appmockito.examples.repositories;

import org.srosales.appmockito.examples.Datos;
import org.srosales.appmockito.examples.models.Examen;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExamenRepositoryImpl implements ExamenRepository {
    @Override
    public Examen save(Examen examen) {
        System.out.println("ExamenRepositoryImpl.save");
        return Datos.EXAMEN;
    }

    @Override
    public List<Examen> findAll() {
        System.out.println("ExamenRepositoryImpl.findAll");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Datos.EXAMENES;
    }
}
