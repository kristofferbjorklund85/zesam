package zesam.src;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

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

    public static Activity act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_account_spinner);

        act = this;

        Toolbar t = (Toolbar) findViewById(R.id.toolbar_logged_in);
        setSupportActionBar(t);
        sContacts = (Spinner) findViewById(R.id.contact_spinner);
        sContacts.setVisibility(View.INVISIBLE);
        tContact = (TextView) findViewById(R.id.contact_text);
        tContact.setVisibility(View.INVISIBLE);
        createButton = (Button) findViewById(R.id.create_meeting_button);
        createButton.setVisibility(View.INVISIBLE);

        Permissions.checkLocationPermission(this);

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
                if(!MeetingSingleton.getCompanyName().equals(sItems.getSelectedItem().toString())) {
                    MeetingSingleton.clearMeeting();
                }
                if(!companyAdapter.getItem(position).name.equals("")) {
                    if(companyAdapter.getItem(0).name.equals("")) {
                        spinnerArray.remove(0);
                        companyAdapter.notifyDataSetChanged();
                        sItems.setSelection(position-1);

                        FakeData.Company company = (FakeData.Company) parent.getItemAtPosition(position-1);
                        selectedCompany = company;
                        initContactSpinner(fd.getContacts(company.name));
                        sContacts.setVisibility(View.VISIBLE);
                        tContact.setVisibility(View.VISIBLE);

                    } else {
                        FakeData.Company company = (FakeData.Company) parent.getItemAtPosition(position);
                        selectedCompany = company;
                        initContactSpinner(fd.getContacts(company.name));
                    }
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
                if(!adapter.getItem(position).equals("")) {
                    if(adapter.getItem(0).equals("")) {
                        contactArray.remove(0);
                        adapter.notifyDataSetChanged();
                        sItems.setSelection(position-1);

                        String contactName = (String) parent.getItemAtPosition(position);
                        selectedContact = contactName;
                        createButton.setVisibility(View.VISIBLE);
                    } else {
                        String contactName = (String) parent.getItemAtPosition(position);
                        selectedContact = contactName;
                    }

                }

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void startSalesMeeting(View v) {
        Intent intent = new Intent(this, CreateMeeting.class);
        MeetingSingleton.setCompanyName(selectedCompany.name);
        MeetingSingleton.setCompanyId(selectedCompany.id);
        MeetingSingleton.setContact(selectedContact);

        startActivity(intent);
    }

    public void logOut(View v) {
        MeetingSingleton.clearMeeting();
        Intent intent = new Intent(getApplicationContext(), Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void back(View v) {
        Intent intent = new Intent(this, Root.class);
        startActivity(intent);
    }

}
