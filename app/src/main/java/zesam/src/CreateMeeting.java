package zesam.src;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
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

    Calendar myCalendar;
    EditText date_text;


    public static Activity act;

    private EditText recordedText;
    private ArrayList<String> inputArray = new ArrayList<>();
    private String textblock = "";

    private static final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);
        Intent intent = getIntent();
        act = this;

        Toolbar t = findViewById(R.id.toolbar_logged_in);
        setSupportActionBar(t);

        list = intent.getStringArrayListExtra("selected");

        format = new SimpleDateFormat("dd/MM/yyyy");

        recordedText = (EditText) findViewById(R.id.recorded_text);
        textblock = "";
        setDates();
        initDateSpinner(getDay(nMonth), getMonth(), getYear());

        myCalendar = Calendar.getInstance();

        date_text = (EditText) findViewById(R.id.date_text);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

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
                new DatePickerDialog(this, date, myCalendar
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
        String myFormat = "yy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        date_text.setText(sdf.format(myCalendar.getTime()));
    }

    public void showResult(View v) {
        Intent intent = new Intent(this, Review.class);
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
