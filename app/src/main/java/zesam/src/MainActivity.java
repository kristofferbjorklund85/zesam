package zesam.src;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(this, Login.class);
        checkCache();

        startActivity(intent);
    }

    private void checkCache() {

        try {
            InputStream inputStream = this.openFileInput("cache.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                ArrayList<String> list = new ArrayList<>();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    list.add(receiveString);
                }

                inputStream.close();
                Log.d("Output", list.get(0));
                Log.d("Output", list.get(1));
                intent.putExtra("User", list.get(0));
                intent.putExtra("Pass", list.get(1));

            }

        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (IndexOutOfBoundsException e) {
            Log.e("login activity", "Index Out of Bounds: " + e.toString());
        }

    }


}
