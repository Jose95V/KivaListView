package com.example.josedanilo.kivalistview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mContext=this;
        String url="http://api.kivaws.org/v1/loans/newest.json";
        getKivaLoans(url);
    }

    private void getKivaLoans(String url) {
        final Context context=this;
        JsonObjectRequest jor=new JsonObjectRequest(url,null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("MainActivity", "Procesando respuesta");

                        Logger.getAnonymousLogger().log(Level.INFO,response.toString());
                        try {
                            JSONArray loans=response.getJSONArray("loans");

                            ArrayList<JSONObject> dataSourse=new ArrayList<JSONObject>();
                            for(int i=0;i<loans.length();i++)
                            {
                                dataSourse.add(loans.getJSONObject(i));

                            }
                            CeldaAdaptador adapter=new CeldaAdaptador(context,0,dataSourse);
                            ((ListView)findViewById(R.id.lista)).setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Nota: ponerse a llorar
                        Logger.getAnonymousLogger().log(Level.SEVERE,"Error Fataliti");


                        Log.d("MainActivity", "" + error.getMessage());
                    }
                }
        );
        MySingleton.getInstance(mContext).addToRequestQueue(jor);
    }

}
