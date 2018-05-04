package zesam.src;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Root extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);

        Toolbar t = (Toolbar) findViewById(R.id.toolbar_logged_in);
        setSupportActionBar(t);
    }

    public void startListMeetings(View v) {
        Intent intent = new Intent(this, ListMeetings.class);
        startActivity(intent);
    }

    public void startPlanMeeting(View v) {
        Intent intent = new Intent(this, PlanMeeting.class);
        startActivity(intent);
    }

    public void startRecordSalesMeeting(View v) {
        Intent intent = new Intent(this, PickAccountSpinner.class);
        startActivity(intent);
    }

    public void logOut(View v) {
        MeetingSingleton.clearMeeting();
        Intent intent = new Intent(getApplicationContext(), Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
