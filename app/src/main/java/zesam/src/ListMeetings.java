package zesam.src;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListMeetings extends AppCompatActivity {

    FakeData fd;
    ArrayList<FakeData.Meeting> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listmeetings);

        Toolbar t = findViewById(R.id.toolbar_logged_in);
        setSupportActionBar(t);

        fd = new FakeData();
        list = fd.getFutureMeetings();

        TextView tv = findViewById(R.id.test_text);
        tv.setText(list.get(0).description);

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
