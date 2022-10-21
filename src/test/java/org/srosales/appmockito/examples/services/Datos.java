package org.srosales.appmockito.examples.services;

import org.srosales.appmockito.examples.models.Examen;

import java.util.Arrays;
import java.util.List;

public class Datos {
    public final static List<Examen> EXAMENES = Arrays.asList(
            new Examen(5L, "Matemáticas"),
            new Examen(6L, "Ciencias"),
            new Examen(7L, "Historia"));

    public final static List<Examen> EXAMENES_ID_NULL = Arrays.asList(
            new Examen(null, "Matemáticas"),
            new Examen(null, "Ciencias"),
            new Examen(null, "Historia"));

    public final static List<Examen> EXAMENES_ID_NEGATIVOS = Arrays.asList(
            new Examen(-5L, "Matemáticas"),
            new Examen(-6L, "Ciencias"),
            new Examen(null, "Historia"));

    public final static List<String> PREGUNTAS = Arrays.asList(
            "aritmética",
            "integrales",
            "derivadas",
            "trigonométricas",
            "geometría"
    );
    public final static Examen EXAMEN = new Examen(null, "Física");
}
