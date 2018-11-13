package com.example.edgar.asesoriasinformales.connector;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Edgar on 11-Nov-18 with the Project Name: AsesoriasInformales.
 */
public class DatabaseConnector {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("message");
    private DatabaseReference userReference = database.getReference("usuarios");
    private DatabaseReference asesoryReference = database.getReference("asesorias");

    public void testHelloWorld(){
        myRef.setValue("Hello, World!");
        Log.i("myRefTest", myRef.toString());
    }

    public FirebaseDatabase getDatabase() {
        return database;
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
}
