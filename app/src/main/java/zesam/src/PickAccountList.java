package zesam.src;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class PickAccountList extends AppCompatActivity {

    private EditText searchField;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_account_list);

        searchField = (EditText) findViewById(R.id.search_text);
        ListView accountView = (ListView) findViewById(R.id.account_list);

        ArrayList<String> accountList = generateAccounts();

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, accountList);

        accountView.setAdapter(arrayAdapter);

        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterAccounts(searchField.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        accountView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, CreateMeeting.class);
                startActivity(intent);
            }
        });
    }

    public static void filterAccounts(String filter) {
        //TODO Filter accounts in listView based on search string
    }

    public static ArrayList<String> generateAccounts() {
        ArrayList<String> list = new ArrayList<>();

        for(int i = 1; i < 11; i++) {
            list.add("Test " + i);
        }
        return list;
    }
}
