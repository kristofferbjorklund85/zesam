package zesam.src;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

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

public class CreateMeeting extends AppCompatActivity {
    private String companyId;

    Spinner daySpinner;
    Spinner monthSpinner;
    Spinner yearSpinner;

    int year;
    int day;
    String sMonth;

    Date date;
    DateFormat format;
    LocalDate localDate;

    private EditText recordedText;
    private ArrayList<String> inputArray = new ArrayList<>();
    private String textblock = "";

    private static final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);
        Intent intent = getIntent();

        companyId = intent.getStringExtra("id");

        format = new SimpleDateFormat("dd/MM/yyyy");

        recordedText = (EditText) findViewById(R.id.recorded_text);
        textblock = "";

        initDateSpinner(getDay(), getMonth(), getYear());
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

    public void showResult(View v) {
        Intent intent = new Intent(this, Result.class);
        intent.putExtra("text", recordedText.getText().toString());

        startActivity(intent);
    }

    public void initDateSpinner(List dayArray, List monthArray, List yearArray) {
        Log.d("initDate", String.valueOf(dayArray.size()));
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, dayArray);
        final ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, monthArray);
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, yearArray);

        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        daySpinner   = (Spinner) findViewById(R.id.day_spinner);
        monthSpinner = (Spinner) findViewById(R.id.month_spinner);
        yearSpinner  = (Spinner) findViewById(R.id.year_spinner);

        daySpinner.setAdapter(dayAdapter);
        monthSpinner.setAdapter(monthAdapter);
        yearSpinner.setAdapter(yearAdapter);

        daySpinner.setSelection(dayAdapter.getPosition(String.valueOf(day)));
        monthSpinner.setSelection(monthAdapter.getPosition(String.valueOf(sMonth)));
        yearSpinner.setSelection(yearAdapter.getPosition(String.valueOf(year)));

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateDateSpinner(getDay(monthAdapter.getPosition(monthSpinner.getSelectedItem().toString()) + 1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void updateDateSpinner(List dayArray) {
        Log.d("Spinner", "Update spinner start");
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, dayArray);

        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        daySpinner = (Spinner) findViewById(R.id.day_spinner);

        daySpinner.setAdapter(dayAdapter);
        Log.d("Spinner", "Update spinner end");
    }

    public List getDay() {
        DateTimeFormatter dtfd = DateTimeFormatter.ofPattern("dd");
        DateTimeFormatter dtfm = DateTimeFormatter.ofPattern("MMMM");
        DateTimeFormatter dtfnm = DateTimeFormatter.ofPattern("MM");
        localDate = LocalDate.now();
        ArrayList<String> list = new ArrayList<>();

        day = Integer.parseInt(dtfd.format(localDate));
        sMonth = dtfm.format(localDate);
        int nMonth = Integer.parseInt(dtfnm.format(localDate));

        YearMonth yearMonth = YearMonth.of(year, nMonth);
        int daysInMonth = yearMonth.lengthOfMonth();

        for(int i = daysInMonth; i >= 1; i--) {
            list.add(String.valueOf(i));
        }
        Log.d("List in first get day", String.valueOf(list.size()));
        return list;
    }

    public List getDay(int month) {
        Log.d("Month", String.valueOf(month));
        ArrayList<String> list = new ArrayList<>();

        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();

        for(int i = daysInMonth; i >= 1; i--) {
            list.add(String.valueOf(i));
        }
        Log.d("List in second get day", String.valueOf(list.size()));
        return list;
    }

    public List getMonth() {
        String [] monthArray = new DateFormatSymbols().getMonths();
        return new ArrayList<>(Arrays.asList(monthArray));
    }

    public List getYear() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy");
        ArrayList<String> list = new ArrayList<>();

        year = Integer.parseInt(dtf.format(localDate));

        for(int i = year; i >= 1990; i--) {
            list.add(String.valueOf(i));
        }
        return list;
    }
}
