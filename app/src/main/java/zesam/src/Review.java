package zesam.src;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Review extends AppCompatActivity {

    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        Intent intent = getIntent();

        Toolbar t = (Toolbar) findViewById(R.id.toolbar_logged_in);
        setSupportActionBar(t);

        String text = "";

        String desc = intent.getStringExtra("text");
        String date = intent.getStringExtra("date");
        ArrayList<String> list = intent.getStringArrayListExtra("selected");

        text = "CompanyId: " + list.get(0) + "\n";
        text = text + "Company: " + list.get(1) + "\n";
        text = text + "Contact: " + list.get(2) + "\n";

        text = text + date + "\n\n";
        text = text + desc;

        resultText = (TextView) findViewById(R.id.resultText);
        resultText.setText(text);

    }

    public void backToPickAccount(View v) {
        Intent intent = new Intent(this, PickAccountSpinner.class);

        startActivity(intent);
    }

    public void logOut(View v) {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void back(View v) {
        Intent intent = new Intent(this, CreateMeeting.class);
        startActivity(intent);
    }

    public void setReminder(View v) {
        createReminder();

    }

    public void createReminder() {

        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2012, 0, 19, 7, 30);
        Calendar endTime = Calendar.getInstance();
        endTime.set(2012, 0, 19, 8, 30);
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, "Test")
                .putExtra(CalendarContract.Events.DESCRIPTION, "testestestsetsetset")
                .putExtra(CalendarContract.Events.EVENT_LOCATION, "Diadrom")
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
        startActivity(intent);

    }


    /*
    public void createReminder() {
        while(!checkPermissions()) {

        }

        Date currentTime = Calendar.getInstance().getTime();

        String title = "Test";
        String desc = "Testar testar testar";
        String place = "Diadrom";
        long startDate = Calendar.getInstance().getTimeInMillis() + 3600000;
        long eventID;


        try {
            String eventUriString = "content://com.android.calendar/events";
            ContentValues eventValues = new ContentValues();
            eventValues.put("calendar_id", 3); // id, We need to choose from
            // our mobile for primary its 1
            eventValues.put("title", title);
            eventValues.put("description", desc);
            eventValues.put("eventLocation", place);

            long endDate = startDate + 1000 * 10 * 10; // For next 10min
            eventValues.put("dtstart", startDate);
            eventValues.put("dtend", endDate);
            //eventValues.put("duration", "PT1H");

            eventValues.put("allDay", 0); //If it is bithday alarm or such
            // kind (which should remind me for whole day) 0 for false, 1
            // for true
            eventValues.put("eventStatus", 1); // This information is
            // sufficient for most
            // entries tentative (0),
            // confirmed (1) or canceled
            // (2):
            eventValues.put("eventTimezone", TimeZone.getDefault().getID());
            *//*
             * Comment below visibility and transparency column to avoid
             * java.lang.IllegalArgumentException column visibility is invalid
             * error
             *//*

            // eventValues.put("visible", 1); // visibility to default (0),
            // confidential (1), private
            // (2), or public (3):
            // eventValues.put("transparency", 0); // You can control whether
            // an event consumes time
            // opaque (0) or transparent
            // (1).
            eventValues.put("hasAlarm", 1); // 0 for false, 1 for true

            Uri eventUri = this.getApplicationContext()
                    .getContentResolver()
                    .insert(Uri.parse(eventUriString), eventValues);
            eventID = Long.parseLong(eventUri.getLastPathSegment());

            String reminderUriString = "content://com.android.calendar/reminders";
            ContentValues reminderValues = new ContentValues();
            reminderValues.put("event_id", eventID);
            reminderValues.put("minutes", 5); // Default value
            //set time in min which occur before event start
            reminderValues.put("method", 1); // Alert Methods: Default(0),
            // Alert(1), Email(2),SMS(3)
            Uri reminderUri = this.getApplicationContext()
                    .getContentResolver()
                    .insert(Uri.parse(reminderUriString), reminderValues);


        } catch (Exception ex) {
            Log.d("Reminder: ","Error in adding event on calendar" + ex.getMessage());
        }



    }

    */

    private boolean checkPermissions() {
        int MY_PERMISSIONS_REQUEST_CALENDAR = 1;

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR},
                    MY_PERMISSIONS_REQUEST_CALENDAR);

            return true;
        } else {
            // Permission has already been granted
            return true;
        }



    }
}

