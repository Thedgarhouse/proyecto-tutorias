package com.example.edgar.asesoriasinformales;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
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
        final Button miBoton = (Button) findViewById(R.id.boton);
        miBoton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 5; i++) {
                    numero = i;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
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
                for(int i=0;i<5;i++){
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
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
        String query = databaseURLAsesorias + ".json";
        //   Asesoria asesoria;
        // Request a string response from the provided URL.

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, query, null, new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Entre al response!!!", "*******Otro MENSAJE!!!");

                        asesoriaList = new ArrayList<>();
                        try {
                            Log.e("Entre al try!!!", "******Otro MENSAJEEEEEEEEEEEEEEE!!!");
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

                            TableRow row = new TableRow(HistorialAlumnoActivity.this);

                            TextView taskdate = new TextView(HistorialAlumnoActivity.this);
                            taskdate.setTextSize(10);
                            taskdate.setText(asesoriaList.get(x).getAsesor()+"\t\t\t\t");
                            row.addView(taskdate);

                            TextView title = new TextView(HistorialAlumnoActivity.this);
                            title.setTextSize(10);
                            title.setText(asesoriaList.get(x).getInicio()+"\t\t\t\t");
                            row.addView(title);

                            TextView taskhour = new TextView(HistorialAlumnoActivity.this);
                            taskhour.setTextSize(10);
                            taskhour.setText(asesoriaList.get(x).getFin()+"\t\t\t\t");
                            row.addView(taskhour);

                            TextView description = new TextView(HistorialAlumnoActivity.this);
                            description.setTextSize(10);
                            description.setText(asesoriaList.get(x).getHoras());
                            row.addView(description);

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


public boolean prepararMetodo(int n){
if(n==4){
    Toast.makeText(getApplicationContext(), "Error al conectar con asesor. Revisar que ambos tengan al comunicacion prendida", Toast.LENGTH_LONG).show();
}
        return false;
}
}
