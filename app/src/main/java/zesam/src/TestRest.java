package zesam.src;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

public class TestRest {

    private Context ctx;

    public TestRest(Context context) {
        ctx = context;
        getTest();
    }


    public void getTest() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                "https://ghibliapi.herokuapp.com/films",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray array) {
                        Log.d("Volley Success", "Downloaded JSONArray");
                        Toast.makeText(ctx, "Success!", Toast.LENGTH_SHORT).show();
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

}
