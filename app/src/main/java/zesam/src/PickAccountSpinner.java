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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PickAccountSpinner extends AppCompatActivity {

    Spinner sItems;
    Spinner sContacts;
    TextView tContact;
    Button createButton;

    ArrayAdapter<FakeData.Company> companyAdapter;

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
        sContacts = (Spinner) findViewById(R.id.contact_spinner);
        sContacts.setVisibility(View.INVISIBLE);
        tContact = (TextView) findViewById(R.id.contact_text);
        tContact.setVisibility(View.INVISIBLE);
        createButton = (Button) findViewById(R.id.start_create_meeting_button);
        createButton.setVisibility(View.INVISIBLE);

        ctx = this;
        fd = new FakeData();
        initCompanySpinner(fd.getCompanies());
    }

    public void initCompanySpinner(final List spinnerArray) {
        companyAdapter = new ArrayAdapter<FakeData.Company>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        companyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sItems = (Spinner) findViewById(R.id.company_spinner);
        sItems.setAdapter(companyAdapter);

        sItems.setSelection(0);

        sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FakeData.Company company = (FakeData.Company) parent.getItemAtPosition(position);
                selectedCompany = company;

                if(!selectedCompany.name.equals("")) {
                    if(companyAdapter.getItem(0).name.equals("")) {
                        spinnerArray.remove(0);
                    }

                    initContactSpinner(fd.getContacts(company.name));
                    companyAdapter.notifyDataSetChanged();
                    sContacts.setVisibility(View.VISIBLE);
                    tContact.setVisibility(View.VISIBLE);
                }

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void initContactSpinner(final List contactArray) {
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, contactArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sItems = (Spinner) findViewById(R.id.contact_spinner);
        sItems.setAdapter(adapter);

        sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String contactName = (String) parent.getItemAtPosition(position);
                selectedContact = contactName;

                if(!selectedContact.equals("")) {
                    if(adapter.getItem(0).equals("")) {
                        contactArray.remove(0);
                    }

                    adapter.notifyDataSetChanged();
                    createButton.setVisibility(View.VISIBLE);
                }

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

    public void logOut(View v) {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void back(View v) {
        Intent intent = new Intent(this, PickAccountSpinner.class);
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
