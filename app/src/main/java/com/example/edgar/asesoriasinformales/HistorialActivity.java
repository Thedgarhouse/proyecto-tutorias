package com.example.edgar.asesoriasinformales;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.edgar.asesoriasinformales.connector.DatabaseConnector;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import android.widget.TableLayout;
import android.widget.TableRow;

public class HistorialActivity extends AppCompatActivity implements View.OnClickListener {
    private AsesoriaAdapter asesoriaAdapter;
    private List<Asesoria> asesoriaList;
    public boolean conectado = false;
    public int numero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        final Button miBoton = (Button) findViewById(R.id.boton);
        miBoton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Plip le pique al boton", "que maduro");
                for (int i = 0; i < 5; i++) {
                    numero = i;
                    Log.e("Ciclo", "ENTRE AL CICLO DE LOS NUMEROS!!!!");
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            Log.e("Estoy en el hilo?", "Estas en el hilo");
                            if (!conectado) {
                                conectado = prepararMetodo(numero);
                            }

                        }
                    }, 5000);
                }
                if (!conectado) {


                }


            }
        });
        fillTable();

        asesoriaAdapter = new AsesoriaAdapter(this, asesoriaList);
        asesoriaAdapter.notifyDataSetChanged();
        //  final ListView asesoriaView = (ListView) findViewById(R.id.asesoria_view);
        //  asesoriaView.setAdapter(asesoriaAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.boton:
                Log.e("Plip le pique al boton", "que maduro");
                for (int i = 0; i < 5; i++) {
                    Log.e("Ciclo", "ENTRE AL CICLO DE LOS NUMEROS!!!!");
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            Log.e("Estoy en el hilo?", "Estas en el hilo");
                            if (!conectado) {
                                conectado = prepararMetodo(4);
                            }

                        }
                    }, 1000);
                }
                if (!conectado) {
                    Toast.makeText(getApplicationContext(), "Error al conectar con alumno. Revisar que ambos tengan al comunicacion prendida", Toast.LENGTH_LONG).show();

                }


                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void fillTable() {
        RequestQueue queue = Volley.newRequestQueue(HistorialActivity.this);
        String databaseURLAsesorias = "https://tutorias-220600.firebaseio.com/";
        String query = databaseURLAsesorias + ".json";
        //   Asesoria asesoria;
        // Request a string response from the provided URL.

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, query, null, new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Entre al response!!!", "Otro MENSAJE!!!");

                        asesoriaList = new ArrayList<>();
                        try {
                            Log.e("Entre al try!!!", "Otro MENSAJEEEEEEEEEEEEEEE!!!");
                            // Getting JSON Array node
                            JSONArray contacts = response.getJSONArray("asesorias");

                            //  Log.e("Contacts",contacts.toString());
                            //  Log.e("Contacts lenght",""+contacts.length());
                            // looping through All Contacts


                            for (int i = 0; i < contacts.length(); i++) {
                                //               Log.e("Entre al ciclo!!!","*******"+contacts.get(i).toString());
                                if (contacts.get(i).toString().equals("null") == false) {

                                    JSONObject c = contacts.getJSONObject(i);
                                    //   Log.e("Entre al ciclo!!!","MENSAJE");

                                    String alumno = c.getString("alumno");
                                    String asesor = c.getString("asesor");
                                    String horas = c.getString("duracion");
                                    String inicio = c.getString("inicio");
                                    String fin = c.getString("fin");

                                    asesoriaList.add(new Asesoria(alumno, asesor, inicio, fin, horas));
                                }

                            }
                        } catch (final JSONException e) {
                            Log.e("Estoy tronando por aqui", e.getMessage());
                            Log.e("Estoy tronando por aqui", e.toString());
                        }

                        TableLayout table = (TableLayout) findViewById(R.id.table_layout);


                        for (int x = 0; x < asesoriaList.size(); x++) {

                            TableRow row = new TableRow(HistorialActivity.this);

                            TextView taskdate = new TextView(HistorialActivity.this);
                            taskdate.setTextSize(10);
                            taskdate.setText(asesoriaList.get(x).getAsesor());
                            row.addView(taskdate);

                            TextView title = new TextView(HistorialActivity.this);
                            taskdate.setText(asesoriaList.get(x).getInicio());
                            row.addView(title);
                            taskdate.setTextSize(10);

                            TextView taskhour = new TextView(HistorialActivity.this);
                            taskdate.setText(asesoriaList.get(x).getFin());
                            taskhour.setTextSize(10);
                            row.addView(taskhour);

                            TextView description = new TextView(HistorialActivity.this);
                            taskdate.setText(asesoriaList.get(x).getHoras());
                            row.addView(description);
                            description.setTextSize(10);

                            table.addView(row);

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


    public boolean prepararMetodo(int n) {
        if (n == 4) {
            Toast.makeText(getApplicationContext(), "Error al conectar con alumno. Revisar que ambos tengan al comunicacion prendida", Toast.LENGTH_LONG).show();
        }
        return false;
    }
}
