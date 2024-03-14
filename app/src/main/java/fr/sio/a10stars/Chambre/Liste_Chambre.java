package fr.sio.a10stars.Chambre;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import fr.sio.a10stars.R;

public class Liste_Chambre extends AppCompatActivity {

    private Button chambre;

    private ListView liste_chambre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_chambre);
        this.liste_chambre = findViewById(R.id.liste_chambre);
    }
}