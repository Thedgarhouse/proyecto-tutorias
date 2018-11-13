package com.example.edgar.asesoriasinformales.connector;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Edgar on 11-Nov-18 with the Project Name: AsesoriasInformales.
 */
public class DatabaseConnector {
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = firebaseDatabase.getReference("message");
    private DatabaseReference userReference = firebaseDatabase.getReference("usuarios");
    private DatabaseReference asesoryReference = firebaseDatabase.getReference("asesorias");
    private String databaseURLusers = "https://tutorias-220600.firebaseio.com/usuarios.json";
    private String simpleResponse;

    public DatabaseConnector (){

    }

    public void testHelloWorld(){
        myRef.setValue("Hello, World!");
        Log.i("myRefTest", myRef.toString());
    }

    public FirebaseDatabase getDatabase() {
        return firebaseDatabase;
    }

    public DatabaseReference getMyRef() {
        return myRef;
    }

    public DatabaseReference getUserReference() {
        return userReference;
    }

    public DatabaseReference getAsesoryReference() {
        return asesoryReference;
    }

    public void checkUser(String email, Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
        String emailURLparameter = databaseURLusers +  "?orderBy=\"correo\"&equalTo=\"" + email + "\"&print=pretty";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, emailURLparameter,
                response -> simpleResponse = response, error -> simpleResponse = error.toString());
        queue.add(stringRequest);
    }
}
