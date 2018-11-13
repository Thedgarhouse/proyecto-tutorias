package com.example.edgar.asesoriasinformales;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Edgar on 11-Nov-18 with the Project Name: AsesoriasInformales.
 */
@IgnoreExtraProperties
public class Post {
    public String rol;
    public String nombre;
    public String correo;


    public Post () {

    }

    public Post(String rol, String nombre, String correo) {
        this.rol = rol;
        this.nombre = nombre;
        this.correo = correo;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("rol", rol);
        result.put("nombre", nombre);
        result.put("correo", correo);
        return result;
    }
}
