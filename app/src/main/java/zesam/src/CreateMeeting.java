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
import android.support.v7.widget.Toolbar;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CreateMeeting extends AppCompatActivity {
    private ArrayList<String> list;

    Spinner daySpinner;
    Spinner monthSpinner;
    Spinner yearSpinner;

    ArrayAdapter<String> dayAdapter;
    ArrayAdapter<String> monthAdapter;
    ArrayAdapter<String> yearAdapter;

    int year;
    int day;
    String sMonth;
    int nMonth;

    String pickedYear;
    String pickedMonth;
    String pickedDay;

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

        Toolbar t = findViewById(R.id.toolbar_logged_in);
        setSupportActionBar(t);

        list = intent.getStringArrayListExtra("selected");

        format = new SimpleDateFormat("dd/MM/yyyy");

        recordedText = (EditText) findViewById(R.id.recorded_text);
        textblock = "";
        setDates();
        initDateSpinner(getDay(nMonth), getMonth(), getYear());
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

        String pickedDate = pickedYear + "-" + pickedMonth + "-" + pickedDay;
        intent.putExtra("date", pickedDate);

        intent.putStringArrayListExtra("selected", list);

        startActivity(intent);
    }

    public void initDateSpinner(List dayArray, List monthArray, List yearArray) {
        daySpinner   = (Spinner) findViewById(R.id.day_spinner);
        monthSpinner = (Spinner) findViewById(R.id.month_spinner);
        yearSpinner  = (Spinner) findViewById(R.id.year_spinner);

        dayAdapter = createDayAdapter(dayArray);
        monthAdapter = createMonthAdapter(monthArray);
        yearAdapter = createYearAdapter(yearArray);

        daySpinner.setAdapter(dayAdapter);
        monthSpinner.setAdapter(monthAdapter);
        yearSpinner.setAdapter(yearAdapter);

        monthSpinner.setSelection(monthAdapter.getPosition(String.valueOf(sMonth)));
        yearSpinner.setSelection(yearAdapter.getPosition(String.valueOf(year)));

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Item selected", "Triggered");
                pickedMonth = monthSpinner.getSelectedItem().toString();
                daySpinner.setAdapter(createDayAdapter(getDay
                        (monthAdapter.getPosition(monthSpinner.getSelectedItem().toString()) + 1)));
                setDaySpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pickedYear = yearSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pickedDay = daySpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public List getDay(int month) {
        ArrayList<String> list = new ArrayList<>();

        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();

        for(int i = 1; i <= daysInMonth; i++) {
            list.add(String.valueOf(i));
        }
        return list;
    }

    public List getMonth() {
        String [] monthArray = new DateFormatSymbols().getMonths();
        return new ArrayList<>(Arrays.asList(monthArray));
    }

    public List getYear() {
        ArrayList<String> list = new ArrayList<>();

        for(int i = year; i >= 1990; i--) {
            list.add(String.valueOf(i));
        }
        return list;
    }

    public void setDaySpinner() {
        if(day < daySpinner.getAdapter().getCount()) {
            daySpinner.setSelection(dayAdapter.getPosition(String.valueOf(day)));
        } else {
            daySpinner.setSelection(1);
        }
    }

    public ArrayAdapter<String> createDayAdapter(List dayArray) {
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(
                this, R.layout.spinner_item, dayArray);

        dayAdapter.setDropDownViewResource(R.layout.spinner_item);

        return dayAdapter;
    }

    public ArrayAdapter<String> createMonthAdapter(List monthArray) {
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(
                this, R.layout.spinner_item, monthArray);

        monthAdapter.setDropDownViewResource(R.layout.spinner_item);

        return monthAdapter;
    }

    public ArrayAdapter<String> createYearAdapter(List yearArray) {
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(
                this, R.layout.spinner_item, yearArray);

        yearAdapter.setDropDownViewResource(R.layout.spinner_item);

        return yearAdapter;
    }

    public void setDates() {
        DateTimeFormatter dtfd = DateTimeFormatter.ofPattern("dd");
        DateTimeFormatter dtfm = DateTimeFormatter.ofPattern("MMMM");
        DateTimeFormatter dtfnm = DateTimeFormatter.ofPattern("MM");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy");

        localDate = LocalDate.now();

        day = Integer.parseInt(dtfd.format(localDate));
        sMonth = dtfm.format(localDate);
        nMonth = Integer.parseInt(dtfnm.format(localDate));
        year = Integer.parseInt(dtf.format(localDate));
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
}
