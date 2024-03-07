package fr.sio.a10stars.Chambre;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fr.sio.a10stars.R;

public class ChambreMenu extends AppCompatActivity implements View.OnClickListener {

    private Button bModif,bAjout,bRetours;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chambre_menu);
        this.bAjout = findViewById(R.id.bAjout);
        this.bModif = findViewById(R.id.bModif);
        this.bRetours = findViewById(R.id.bRetours2);

        this.bModif.setOnClickListener(this);
        this.bAjout.setOnClickListener(this);
        this.bRetours.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Boolean ajouter = true;
        if(view.getId() == R.id.bAjout) {
            this.intent = new Intent(this, ChambreForm.class);
            this.intent.putExtra("ajouter",ajouter);
            startActivity(this.intent);
        }else if(view.getId() == R.id.bModif){
            ajouter = false;
            this.intent = new Intent(this, ChambreForm.class);
            this.intent.putExtra("ajouter",ajouter);
            startActivity(this.intent);
        }else if(view.getId() == R.id.bRetours2) {
            this.finish();
        }
    }
}