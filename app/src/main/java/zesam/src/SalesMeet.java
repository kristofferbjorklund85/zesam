package zesam.src;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SalesMeet extends AppCompatActivity {

    private String companyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_meet);
        Intent intent = getIntent();

        companyId = intent.getStringExtra("id");

        Log.d("SalesMeet", companyId);
    }
}
