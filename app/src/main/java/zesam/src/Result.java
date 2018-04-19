package zesam.src;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

public class Result extends AppCompatActivity {

    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();

        Toolbar t = findViewById(R.id.toolbar_logged_in);
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



}
