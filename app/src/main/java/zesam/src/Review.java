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
import android.widget.CheckBox;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Review extends AppCompatActivity {

    private TextView resultText;
    ArrayList<String> list;
    CheckBox cb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        Intent intent = getIntent();

        Toolbar t = (Toolbar) findViewById(R.id.toolbar_logged_in);
        setSupportActionBar(t);

        cb = (CheckBox) findViewById(R.id.reminder);

        String text = "";

        String desc = intent.getStringExtra("text");
        String date = intent.getStringExtra("date");
        list = intent.getStringArrayListExtra("selected");

        text = "CompanyId: " + list.get(0) + "\n";
        text = text + "Company: " + list.get(1) + "\n";
        text = text + "Contact: " + list.get(2) + "\n";

        text = text + date + "\n\n";
        text = text + desc;

        resultText = (TextView) findViewById(R.id.resultText);
        resultText.setText(text);

    }

    public void backToPickAccount(View v) {
        PickAccountSpinner.act.finish();
        CreateMeeting.act.finish();

        if(cb.isChecked()) {
            createReminder();
        }

        finish();
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


    }

    public void createReminder() {
        String title = "Påminnelse säljmöte " + list.get(1);
        String desc = "Påminnelse om uppföljning av möte med " + list.get(2);
        String place = "";

        long startDate = Calendar.getInstance().getTimeInMillis() + 3600000;
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

}

