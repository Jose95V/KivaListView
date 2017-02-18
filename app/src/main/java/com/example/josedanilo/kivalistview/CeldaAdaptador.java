package com.example.josedanilo.kivalistview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Jose Danilo on 17/02/2017.
 */

public class CeldaAdaptador extends ArrayAdapter<JSONObject> {

    public CeldaAdaptador (Context context, int textViewResourseId){
        super(context, textViewResourseId);
    }
    public CeldaAdaptador(Context context, int resourse, List<JSONObject> items){
        super(context,resourse,items);
    }
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent)
    {
        View celda = convertView;
        if (celda==null)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            celda = layoutInflater.inflate(R.layout.celda_adaptador,null);
        }

        TextView id = (TextView) celda.findViewById(R.id.id);
        TextView nombre=(TextView) celda.findViewById(R.id.nombre);
        TextView ubicacion=(TextView) celda.findViewById(R.id.ubicacion);
        NetworkImageView niv= (NetworkImageView)celda.findViewById(R.id.imagen);

        JSONObject elemento=this.getItem(position);
        try {
            id.setText(elemento.getString("name"));
            nombre.setText(elemento.getString("loan_amount"));
            ubicacion.setText(elemento.getString("use"));

            String imagen=elemento.getString("id");
            int img= Integer.parseInt(imagen);
            String url = "https://www.kiva.org/img/512/"+img+".jpg";
            niv.setImageUrl(url,MySingleton.getInstance(MainActivity.mContext).getImageLoader());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        celda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetalleActivity.class);
                intent.putExtra("JSONObject", getItem(position).toString());
                getContext().startActivity(intent);
            }
        });

        return celda;
    }

}

