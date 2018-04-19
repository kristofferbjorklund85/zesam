package zesam.src;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class PickAccountSpinner extends AppCompatActivity {

    Spinner sItems;
    FakeData.Company selectedCompany;
    FakeData fd;
    String selectedContact;

    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_account_spinner);

        Toolbar t = (Toolbar) findViewById(R.id.toolbar_logged_in);
        setSupportActionBar(t);
        Button b = (Button) findViewById(R.id.back_button);
        b.setVisibility(View.INVISIBLE);

        ctx = this;
        fd = new FakeData();
        initCompanySpinner(fd.getCompanies());
    }



    public void initCompanySpinner(List spinnerArray) {


        ArrayAdapter<FakeData.Company> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sItems = (Spinner) findViewById(R.id.company_spinner);
        sItems.setAdapter(adapter);

        sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FakeData.Company company = (FakeData.Company) parent.getItemAtPosition(position);
                Log.d("Object", company.id + " " + company.name);
                selectedCompany = company;
                initContactSpinner(fd.getContacts(company.name));

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void initContactSpinner(List spinnerArray) {


        ArrayAdapter<FakeData.Company> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sItems = (Spinner) findViewById(R.id.contact_spinner);
        sItems.setAdapter(adapter);

        sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String contactName = (String) parent.getItemAtPosition(position);
                Log.d("ContactName: ", contactName);
                selectedContact = contactName;

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void startSalesMeeting(View v) {
        Intent intent = new Intent(this, CreateMeeting.class);
        ArrayList<String> list = new ArrayList<>();
        list.add(selectedCompany.id);
        list.add(selectedCompany.name);
        list.add(selectedContact);

        intent.putStringArrayListExtra("selected", list);

        startActivity(intent);
    }



    /*public void getTest() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                "https://ghibliapi.herokuapp.com/films/",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray array) {
                        //Log.d("Volley Success", "Downloaded JSONArray");
                        convertJsonToArray(array);
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
*/

    /*public void convertJsonToArray(JSONArray array) {
        List<FakeData.Company> spinnerArray =  new ArrayList<>();

        if (array != null) {
            int len = array.length();
            for (int i=0;i<len;i++){
                try{
                    JSONObject object = array.getJSONObject(i);
                    FakeData.Company com = new FakeData.Company(object.getString("id"), object.getString("title"));
                    spinnerArray.add(com);
                } catch (JSONException e) {
                    Log.d("JSONException", e.getMessage());
                    break;
                }
            }
        }

        initSpinner(spinnerArray);
    }*/

}
