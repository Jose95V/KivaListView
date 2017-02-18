package com.example.josedanilo.kivalistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jose Danilo on 17/02/2017.
 */

public class DetalleActivity extends AppCompatActivity {

    String jsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        jsonString = getIntent().getStringExtra("JSONObject");

        try {
            JSONObject json = new JSONObject(jsonString);
            ((TextView) findViewById(R.id._nombre)).setText(json.getString("name"));
            ((TextView) findViewById(R.id._pais)).setText(json.getJSONObject("location").getString("country"));
            ((TextView) findViewById(R.id._cantidad)).setText(json.getString("loan_amount"));
            ((TextView) findViewById(R.id._uso)).setText(json.getString("use"));

            JSONObject imagenJSON = json.getJSONObject("image");
            final int idImagen = imagenJSON.getInt("id");
            final int idPlantilla = imagenJSON.getInt("id_template");
            final String patronURL;

            JsonObjectRequest plantillaRequest = new JsonObjectRequest("http://api.kivaws.org/v1/templates/images.json",null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response){

                    try {
                        String patronURL;
                        JSONArray plantillas = response.getJSONArray("templates");
                        if (plantillas == null) {
                            Log.d("DetalleActivity", "Plantilla es null");
                        }
                        for (int i = 0; i < plantillas.length(); i++) {
                            JSONObject patron = plantillas.getJSONObject(i);
                            if (patron.getInt("id") == idPlantilla) {
                                patronURL = patron.getString("pattern");
                                cargarImagen(patronURL, idImagen);
                                break;
                            } else {
                                Log.d("DetalleActivity", "is no coincide");
                            }
                        }


        } catch (JSONException exception) {

                        Log.d("DetalleActivity", "JSON Exception");
                    }
        }

    },new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("DetalleActivity", "Error al cargar imagen");
                }
            });
            MySingleton.getInstance(this).getRequestQueue().add(plantillaRequest);

        } catch (JSONException excepcion) {

        } catch (Exception excepcion) {

            Log.d("DetalleActivity", excepcion.getMessage());
        }

        Log.d("DetalleActivity", "Terminar de crear actividad");
    }

    private void cargarImagen(String patronURL, int idImagen) {
        String url = patronURL.replace("<size>", "400");
        url = url.replace("<id>", "" + idImagen);

        NetworkImageView imagen = (NetworkImageView) findViewById(R.id.imagen);
        imagen.setImageUrl(url, MySingleton.getInstance(this).getImageLoader());

        Log.d("DetalleActivity", "Cargando imagen");
    }
}
