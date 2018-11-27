package com.example.edgar.asesoriasinformales;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Edgar on 11-Nov-18 with the Project Name: AsesoriasInformales.
 */
@IgnoreExtraProperties
public class Asesoria {
    public String alumno;
    public String asesor;
    public String inicio;
    public String horas;
    public String fin;

    public Asesoria(){

    }

    public Asesoria(String alumno, String asesor, String inicio, String fin,String horas) {
        this.alumno = alumno;
        this.asesor = asesor;
        this.inicio = inicio;
        this.horas= horas;
        this.fin = fin;
    }
}
