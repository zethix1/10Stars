package fr.sio.a10stars;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;

import fr.sio.a10stars.Chambre.MyAdapter;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sample data
        // Sample data
        String[] items = {"Chambre"};

        // Create custom adapter
        this.arrayAdapter = new MyAdapter(this, R.layout.liste_view, Arrays.asList(items));
        // Set the adapter to the ListView
        this.listView = findViewById(R.id.liste_view);
        this.listView.setAdapter(this.arrayAdapter);
    }

}