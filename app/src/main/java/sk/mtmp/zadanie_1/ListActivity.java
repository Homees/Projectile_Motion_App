package sk.mtmp.zadanie_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ArrayList<Suradnice> coordinates = (ArrayList<Suradnice>) getIntent().getSerializableExtra("COORDINATES");

        // Create the adapter to convert the array to views
        SuradniceAdapter adapter = new SuradniceAdapter(this, coordinates);

        // Attach the adapter to a ListView
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}
