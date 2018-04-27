package zesam.src;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.support.v7.widget.Toolbar;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CreateMeeting extends AppCompatActivity {

    private ArrayList<String> list;

    SimpleDateFormat sdf;
    Calendar myCalendar;
    EditText date_text;
    DatePickerDialog.OnDateSetListener dateListener;

    public static Activity act;

    private EditText recordedText;
    private String textblock = "";

    private static final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);
        act = this;

        Toolbar t = findViewById(R.id.toolbar_logged_in);
        setSupportActionBar(t);

        Intent intent = getIntent();


        recordedText = (EditText) findViewById(R.id.recorded_text);
        textblock = "";

        String myFormat = "yyyy MMM dd"; //In which you need put here
        sdf = new SimpleDateFormat(myFormat);

        myCalendar = Calendar.getInstance();
        date_text = (EditText) findViewById(R.id.date_text);
        updateLabel();

        initDatePicker();

    }

    private void initDatePicker() {
        dateListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        date_text.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(act, dateListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void startVoiceInput(View v) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "sv-SE");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Säg nåt");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    setVoiceInputText(result.get(0));
                }
                break;
            }
        }
    }

    public void setVoiceInputText(String text) {
        textblock = textblock + text + "\n\n";
        recordedText.setText(textblock);
    }

    private void updateLabel() {
        date_text.setText(sdf.format(myCalendar.getTime()));
    }

    public void showResult(View v) {
        Intent intent = new Intent(this, Review.class);
        MeetingSingleton.setDescription(recordedText.getText().toString());
        MeetingSingleton.setDate(sdf.format(myCalendar.getTime()));

        startActivity(intent);
    }

    public void logOut(View v) {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void back(View v) {
        onBackPressed();
    }
}
