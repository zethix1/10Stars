package fr.sio.a10stars.Chambre;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import fr.sio.a10stars.R;
import fr.sio.a10stars.Stars10DB;

public class ChambreForm extends AppCompatActivity implements View.OnClickListener {

    private EditText emax,estatut,eEtage,etype,eNum,ecomm;

    private Button bModifAjout,bRetour;

    private Bundle bundle;

    private Intent intent;

    private Stars10DB maBD;
    private SQLiteDatabase writeBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chambre_form);
        this.emax = findViewById(R.id.max_personne);
        this.estatut = findViewById(R.id.statut_chambre);
        this.eEtage = findViewById(R.id.etage_chambre);
        this.etype = findViewById(R.id.typelit_chambre);
        this.eNum = findViewById(R.id.num_chambre);
        this.ecomm = findViewById(R.id.commentaire_chambre);
        this.bModifAjout = findViewById(R.id.bModifAjout);
        this.bRetour = findViewById(R.id.bRetours);
        this.bRetour.setOnClickListener(this);
        this.bundle = this.getIntent().getExtras();
        Boolean ajouter = this.bundle.getBoolean("ajouter");
        this.maBD = new Stars10DB(this);
        this.writeBD = this.maBD.getWritableDatabase();

        if(ajouter == false) {
            this.bModifAjout.setText("Appliquer");
            //Cursor cursor = this.writeBD.execSQL("select * FROM chambre WHERE id = "+ );
        }else {
            this.bModifAjout.setText("Ajouter");
        }
        this.bModifAjout.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.bRetours) {
            this.finish();
        }else if(view.getId() == R.id.bModifAjout) {

        }
    }
}