package com.example.edgar.asesoriasinformales;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Edgar on 11-Nov-18 with the Project Name: AsesoriasInformales.
 */
@IgnoreExtraProperties
public class Asesoria {
    private String alumno;
    private String asesor;
    private String inicio;
    private String horas;
    private String fin;

    public Asesoria(){

    }

    public Asesoria(String alumno, String asesor, String inicio, String fin,String horas) {
        this.setAlumno(alumno);
        this.setAsesor(asesor);
        this.setInicio(inicio);
        this.setHoras(horas);
        this.setFin(fin);
    }


    public String getAlumno() {
        return alumno;
    }

    public void setAlumno(String alumno) {
        this.alumno = alumno;
    }

    public String getAsesor() {
        return asesor;
    }

    public void setAsesor(String asesor) {
        this.asesor = asesor;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }
}
