package zesam.src;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
                        //Log.d("Volley Success", "Downloaded JSONArray");
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
        List<Company> spinnerArray =  new ArrayList<Company>();

        if (array != null) {
            int len = array.length();
            for (int i=0;i<len;i++){
                try{
                    JSONObject object = array.getJSONObject(i);
                    Company com = new Company(object.getString("id"), object.getString("title"));
                    spinnerArray.add(com);
                } catch (JSONException e) {
                    Log.d("JSONException", e.getMessage());
                    break;
                }
            }
        }


        ArrayAdapter<Company> adapter = new ArrayAdapter<Company>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        Log.d("TEST", adapter.toString());

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sItems = (Spinner) findViewById(R.id.selectspinner);
        sItems.setAdapter(adapter);

        sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Company company = (Company) parent.getItemAtPosition(position);

                Log.d("Object", company.id + " " + company.name);

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }



}

class Company {
    String id;
    String name;

    public Company(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
