package zesam.src;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;

import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CreateMeeting extends AppCompatActivity {
    SimpleDateFormat sdf;
    Calendar myCalendar;
    EditText date_text;
    DatePickerDialog.OnDateSetListener dateListener;
    TextView ccText;

    float speechSpeed = 0.6f;

    TextToSpeech tts;

    String firstLine;
    String secondLine;
    String thirdLine;

    static int recordedLines = 1;

    public static Activity act;

    private EditText recordedText;
    private String textblock = "";

    private static final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);
        act = this;

        firstLine = getResources().getString(R.string.recorded_first_line);
        secondLine = getResources().getString(R.string.recorded_second_line);
        thirdLine = getResources().getString(R.string.recorded_third_line);

        Toolbar t = (Toolbar) findViewById(R.id.toolbar_logged_in);
        setSupportActionBar(t);

        Button record_button = (Button) findViewById(R.id.record_button);
        record_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startVoiceInput();
            }
        });

        ccText = findViewById(R.id.cc_text);

        String s = MeetingSingleton.getContact();
        int trim = s.indexOf("(") - 1;

        ccText.setText(s.substring(0, trim) + "\n" + MeetingSingleton.getCompanyName());

        Intent intent = getIntent();

        recordedText = (EditText) findViewById(R.id.recorded_text);
        if(!MeetingSingleton.getDescription().equals("")) {
            recordedText.setText(MeetingSingleton.getDescription());
        }
        textblock = "";

        String myFormat = "yyyy MMM dd"; //In which you need put here
        sdf = new SimpleDateFormat(myFormat);

        myCalendar = Calendar.getInstance();
        date_text = (EditText) findViewById(R.id.date_text);

        if(!MeetingSingleton.getDate().equals("")) {
            date_text.setText(MeetingSingleton.getDate());
            MeetingSingleton.setDate(sdf.format(myCalendar.getTime()));
        } else {
            updateLabel();
        }

        initDatePicker();

        textToSpeechTest();
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

    public void startVoiceInput() {
        switch(recordedLines) {
            case 1: tts.speak(firstLine, TextToSpeech.QUEUE_FLUSH, null);
                    try {
                        TimeUnit.MILLISECONDS.sleep(3000);
                    } catch(InterruptedException e) {
                        Log.d("Error", e.getMessage());
                    }
                    break;
            case 2: tts.speak(secondLine, TextToSpeech.QUEUE_FLUSH, null);
                    try {
                        TimeUnit.MILLISECONDS.sleep(2900);
                    } catch(InterruptedException e) {
                        Log.d("Error", e.getMessage());
                    }
                    break;
            case 3: tts.speak(thirdLine, TextToSpeech.QUEUE_FLUSH, null);
                    try {
                        TimeUnit.MILLISECONDS.sleep(3300);
                    } catch(InterruptedException e) {
                        Log.d("Error", e.getMessage());
                    }
                    break;
        }

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "sv-SE");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Record Description");
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
                    switch(recordedLines) {
                        case 1: recordedLines++;
                                startVoiceInput();
                                break;
                        case 2: recordedLines++;
                                startVoiceInput();
                                break;
                    }
                }
                break;
            }
        }
    }

    public void setVoiceInputText(String text) {
        textblock = textblock + text + "\n\n";
        MeetingSingleton.setDescription(textblock);
        recordedText.setText(textblock);
    }

    private void updateLabel() {
        date_text.setText(sdf.format(myCalendar.getTime()));
        MeetingSingleton.setDate(sdf.format(myCalendar.getTime()));
    }

    public void showResult(View v) {
        Intent intent = new Intent(this, Review.class);

        startActivity(intent);
    }

    public void clearRecordedText(View v) {
        recordedText.getText().clear();
        textblock = "";
        recordedLines = 1;
    }

    public void textToSpeechTest() {
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                tts.setLanguage(Locale.US);
                tts.setSpeechRate(speechSpeed);
            }
        });
    }

    public void logOut(View v) {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void back(View v) {
        onBackPressed();
    }

    public void photo(View v) {
        Toast toast = Toast.makeText(act, "Photo functionailty is not yet implemented", Toast.LENGTH_SHORT);
        toast.show();
    }
}
