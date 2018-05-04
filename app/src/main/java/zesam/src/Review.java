package zesam.src;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Review extends AppCompatActivity {

    private TextView resultText1;
    private TextView resultText2;
    private TextView resultDate;

    CheckBox cb;
    private FusedLocationProviderClient mFusedLocationClient;

    String mapsURL;
    MeetingSingleton ms;
    String coords = "";

    String text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        Intent intent = getIntent();
        mapsURL = getResources().getString(R.string.gMaps_url);

        ms = MeetingSingleton.getMeeting();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getUserLocation();

        Toolbar t = (Toolbar) findViewById(R.id.toolbar_logged_in);
        setSupportActionBar(t);

        cb = (CheckBox) findViewById(R.id.reminder);

        resultText1 = (TextView) findViewById(R.id.resultText1);
        resultText2 = (TextView) findViewById(R.id.resultText2);
        resultDate = (TextView) findViewById(R.id.resultDate);

        String headText = "";

        headText = headText + "Company: " + ms.comapnyName + "\n";
        headText = headText + "Contact: " + ms.contact + "\n";

        resultText1.setText(headText);
        resultDate.setText(ms.date);

        text = text + ms.description + "\n\n";

        resultText2.setClickable(true);
    }

    public void submitReport(View v) {
        PickAccountSpinner.act.finish();
        CreateMeeting.act.finish();

        if (cb.isChecked()) {
            createReminder();
        }
        Toast toast = Toast.makeText(this, "Your report has been submitted", Toast.LENGTH_LONG);
        toast.show();
        finish();
    }

    public void logOut(View v) {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void back(View v) {
        onBackPressed();
    }

    public void createReminder() {
        String title = "Påminnelse säljmöte " + ms.comapnyName;
        String desc = "Påminnelse om uppföljning av möte med " + ms.contact;
        String place = coords;

        long startDate = Calendar.getInstance().getTimeInMillis() + 86400000;
        long endDate = startDate + 1000 * 10 * 10;

        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startDate)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endDate)
                .putExtra(CalendarContract.Events.TITLE, title)
                .putExtra(CalendarContract.Events.DESCRIPTION, desc)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, place)
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
        startActivity(intent);

    }

    public void getUserLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null) {
                    coords = location.getLatitude() + ", " + location.getLongitude();
                    MeetingSingleton.setMapsURL(mapsURL + coords.replaceAll("\\s+",""));
                    resultText2.setText(text + ms.mapsURL);
                }
            }
        });

        /*LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                userLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, locationListener);
        userLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        locationManager.removeUpdates(locationListener);*/
    }
}

