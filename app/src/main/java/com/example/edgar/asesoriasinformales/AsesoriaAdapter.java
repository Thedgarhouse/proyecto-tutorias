package com.example.edgar.asesoriasinformales;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AsesoriaAdapter extends BaseAdapter {

    private Context asesoriaContext;
    private List<Asesoria> asesoriaList;

    public AsesoriaAdapter(Context context, List<Asesoria> asesorias) {
        asesoriaList = asesorias;
        asesoriaContext = context;

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AsesoriaViewHolder holder;

        if (view ==null){
            LayoutInflater recordInflater = (LayoutInflater)
                    asesoriaContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = recordInflater.inflate(R.layout.asesoria, null);

            holder = new AsesoriaViewHolder();
            holder.alumnoView = (TextView) view.findViewById(R.id.record_age);
            holder.asesorView = (TextView) view.findViewById(R.id.record_name);
            holder.inicioView = (TextView) view.findViewById(R.id.record_position);
            holder.finView = (TextView) view.findViewById(R.id.record_address);
            view.setTag(holder);

        }else {
            holder = (AsesoriaViewHolder) view.getTag();
        }

        Asesoria asesoria = (Asesoria) getItem(i);
        holder.alumnoView.setText(asesoria.getAlumno());
        holder.asesorView.setText(asesoria.getAsesor());
        holder.inicioView.setText(asesoria.getInicio());
        holder.finView.setText(asesoria.getFin());
        return view;
    }

}