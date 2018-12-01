package com.example.edgar.asesoriasinformales;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HistorialAlumnoActivity extends AppCompatActivity implements View.OnClickListener{
    private AsesoriaAdapter asesoriaAdapter;
    private List<Asesoria> asesoriaList;
    public boolean conectado=false;
    public int numero;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_alumno);
        final Button miBoton= (Button) findViewById(R.id.boton);
        miBoton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Plip le pique al boton","que maduro");
                for(int i=0;i<5;i++){
                    numero=i;
                    Log.e("Ciclo","ENTRE AL CICLO DE LOS NUMEROS!!!!");
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            Log.e("Estoy en el hilo?","Estas en el hilo");
                            if(!conectado){

                                conectado=prepararMetodo(numero);
                            }

                        }
                    }, 5000);
                }
                if(!conectado){


                }



            }
        });
        fillTable();
        asesoriaAdapter= new AsesoriaAdapter(this, asesoriaList);
        asesoriaAdapter.notifyDataSetChanged();
        final ListView asesoriaView = (ListView) findViewById(R.id.asesoria_view);
        asesoriaView.setAdapter(asesoriaAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.boton:
                Log.e("Plip le pique al boton","que maduro");
                for(int i=0;i<5;i++){
                    Log.e("Ciclo","ENTRE AL CICLO DE LOS NUMEROS!!!!");
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            Log.e("Estoy en el hilo?","Estas en el hilo");
                            if(!conectado){
                                conectado=prepararMetodo(4);
                            }

                        }
                    }, 1000);
                }
                if(!conectado){
                    Toast.makeText(getApplicationContext(), "Error al conectar con asesor. Revisar que ambos tengan al comunicacion prendida", Toast.LENGTH_LONG).show();

                }



                break;
        }
        }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void fillTable(){
        RequestQueue queue = Volley.newRequestQueue(HistorialAlumnoActivity.this);
        String databaseURLAsesorias = "https://tutorias-220600.firebaseio.com/";

            Asesoria asesoria = new Asesoria();
        // Request a string response from the provided URL.

        for(int i=1; i<=3;i++) {
        String query = databaseURLAsesorias + "asesorias/"+i+".json";
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
            (Request.Method.GET, query, null, new Response.Listener<JSONObject>() {


                @Override
                public void onResponse(JSONObject response) {
                    asesoriaList = new ArrayList<>();


                    try {
                        asesoria.setAlumno(response.getString("alumno"));
                        asesoria.setAsesor(response.getString("asesor"));
                        asesoria.setHoras(response.getString("duracion"));
                        asesoria.setInicio(response.getString("inicio")) ;
                        asesoria.setFin(response.getString("fin"));
                        asesoriaList.add(asesoria);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                        }



            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Error requesting user info. Please check Internet connection and try again", Toast.LENGTH_LONG).show();
                    Log.e("Check User Error", error.toString());
                }
            });
            queue.add(jsonObjectRequest);
        }





    }


public boolean prepararMetodo(int n){
if(n==4){
    Toast.makeText(getApplicationContext(), "Error al conectar con alumno. Revisar que ambos tengan al comunicacion prendida", Toast.LENGTH_LONG).show();
}
        return false;
}
}
