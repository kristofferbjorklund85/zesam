package zesam.src;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Login extends AppCompatActivity{

    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkCache();

        Toolbar t = (Toolbar) findViewById(R.id.toolbar_logged_out);
        setSupportActionBar(t);
    }

    public void signIn(View v) {
        saveCred(username.getText().toString(), password.getText().toString());
        Intent intent = new Intent(this, PickAccountSpinner.class);
        startActivity(intent);
    }

    private void saveCred(String user, String pass) {
        String filename = "cache.txt";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, this.MODE_PRIVATE);
            outputStream.flush();
            outputStream.write(user.getBytes());
            outputStream.write("\n".getBytes());
            outputStream.write(pass.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void checkCache() {
        username = (EditText) findViewById(R.id.username_text);
        password = (EditText) findViewById(R.id.password_text);

        username.setText(getIntent().getStringExtra("User"));
        password.setText(getIntent().getStringExtra("Pass"));

    }

    private void clearCache() {
        String filename = "cache.txt";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, this.MODE_PRIVATE);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
