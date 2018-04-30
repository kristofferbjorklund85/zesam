package zesam.src;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListMeetings extends AppCompatActivity {

    FakeData fd;
    ArrayList<FakeData.Meeting> list;
    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listmeetings);

        Toolbar t = findViewById(R.id.toolbar_logged_in);
        setSupportActionBar(t);

        fd = new FakeData();
        list = fd.getFutureMeetings();
        ll = findViewById(R.id.linelay);

        initCards();
    }

    public void initCards() {

        for (FakeData.Meeting m : list) {
            CardView cv = new CardView(this);

            cv.setContentPadding(15, 15, 15, 15);
            cv.setPreventCornerOverlap(false);
            cv.setRadius(9);
            cv.setUseCompatPadding(true);

            TextView tv = new TextView(this);
            String text =
                    "Company: " + m.comapnyName + "\n" +
                    "Contact: " + m.contact + "\n" +
                    "Date: " + m.date + "\n" +
                    "Description: " + shortenText(m.description);

            tv.setText(text);
            tv.setTextSize(14);
            tv.setTextColor(getColor(R.color.zesamBlack));
            cv.addView(tv);

            ll.addView(cv);
        }

    }

    public String shortenText(String text) {
        if(text.length() > 75) {
            text = text.substring(0,72) + "...";
        }

        return text;
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
