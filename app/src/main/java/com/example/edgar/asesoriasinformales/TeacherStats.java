package com.example.edgar.asesoriasinformales;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TeacherStats extends AppCompatActivity implements View.OnClickListener {

    private List<Asesoria> asesoriaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_stats);

    }

    public void getTiempoAsesorias(){

        String url = "https://tutorias-220600.firebaseio.com/asesorias.json";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        asesoriaList = new ArrayList<>();
                        try {
                            JSONArray contacts = response.getJSONArray("asesorias");
                            for (int i = 0; i < contacts.length(); i++) {
                                if (contacts.get(i).toString().equals("null") == false) {
                                    JSONObject c = contacts.getJSONObject(i);

                                    String alumno = c.getString("alumno");
                                    String asesor = c.getString("asesor");
                                    String horas = c.getString("duracion");
                                    String inicio = c.getString("inicio");
                                    String fin = c.getString("fin");

                                    asesoriaList.add(new Asesoria(alumno, asesor, inicio, fin, horas));
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        TextView tiempoTotalAsesorias = (TextView)findViewById(R.id.tiempoTotal);
                        TextView promedioAsesoria = (TextView)findViewById(R.id.promedioAsesoria);
                        TextView cantidadAsesorias = (TextView)findViewById(R.id.cantidadAsesorias);

                        int acumTiempo = 0;

                        for(int x=0; x<asesoriaList.size(); x++)
                        {
                            acumTiempo = acumTiempo + Integer.parseInt(asesoriaList.get(x).getHoras().substring(0,0));
                        }

                        tiempoTotalAsesorias.setText(acumTiempo);
                        promedioAsesoria.setText(acumTiempo/asesoriaList.size());
                        cantidadAsesorias.setText(asesoriaList.size());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // : Handle error


                    }
                });
    }




    public void getPromedioPorAsesoria(){

    }

    public void getAsesoriasImpartidas(){

    }

    @Override
    public void onClick(View v) {

    }
}
