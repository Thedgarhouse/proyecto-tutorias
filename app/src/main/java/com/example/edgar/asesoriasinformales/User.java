package com.example.edgar.asesoriasinformales;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Edgar on 11-Nov-18 with the Project Name: AsesoriasInformales.
 */
@IgnoreExtraProperties
public class User {
    public String correo;
    public String nombre;
    public String rol;

    public User (){

    }

    public User(String correo, String nombre, String rol) {
        this.correo = correo;
        this.nombre = nombre;
        this.rol = rol;
    }
}
