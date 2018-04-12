package zesam.src;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Intent intent = new Intent(this, MainActivity.class);

        ctx = this;
        TestRest teeest = new TestRest(ctx);

        //startActivity(intent);
        Log.d("MainActivity", "Started");
    }




}
