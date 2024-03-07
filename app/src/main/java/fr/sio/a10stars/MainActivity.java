package fr.sio.a10stars;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.Arrays;

import fr.sio.a10stars.Chambre.ChambreForm;
import fr.sio.a10stars.Chambre.ChambreMenu;
import fr.sio.a10stars.Chambre.MyAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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


    @Override
    public void onClick(View view) {

    }
}