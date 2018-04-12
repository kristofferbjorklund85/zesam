package zesam.src;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    Spinner sItems;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intent intent = new Intent(this, MainActivity.class);


        ctx = this;
        getTest();

        //startActivity(intent);
        Log.d("MainActivity", "Started");
    }


    public void getTest() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                "https://ghibliapi.herokuapp.com/films/",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray array) {
                        Log.d("Volley Success", "Downloaded JSONArray");
                        Toast.makeText(ctx, "Success!", Toast.LENGTH_SHORT).show();
                        initSpinner(array);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR getTest cause: ", error.toString());
                if(error.networkResponse.statusCode == 404) {
                    Toast.makeText(ctx, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        VolleySingleton.getInstance(ctx).addToRequestQueue(jsonArrayRequest);


    }

    public void initSpinner(JSONArray array) {
        List<String> spinnerArray =  new ArrayList<String>();

        if (array != null) {
            int len = array.length();
            for (int i=0;i<len;i++){
                try{
                    spinnerArray.add(array.get(i).toString());
                    Log.d("JSONArray", array.get(i).toString());
                } catch (JSONException e) {
                    Log.d("JSONException", e.getMessage());
                    break;
                }
            }
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        Log.d("TEST", adapter.toString());

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sItems = (Spinner) findViewById(R.id.selectspinner);
        sItems.setAdapter(adapter);


    }



}
