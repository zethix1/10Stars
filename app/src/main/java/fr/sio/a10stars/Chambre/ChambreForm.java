package fr.sio.a10stars.Chambre;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import fr.sio.a10stars.R;
import fr.sio.a10stars.Stars10DB;

public class ChambreForm extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private EditText emax,eEtage,eNum,ecomm;

    private Spinner estatut,etype;


    private Button bModifAjout,bRetour;

    private ArrayAdapter<CharSequence> statut;

    private ArrayAdapter<CharSequence> type;

    private Object statutR;

    private Object typeR;

    private String[] statutT = {"disponible,occupe,maintenance"};

    private String[] typeT = {"simple,double"};

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
        this.estatut.setOnItemSelectedListener(this);
        this.etype.setOnItemSelectedListener(this);

        this.statut = ArrayAdapter.createFromResource(this,R.array.statut, com.google.android.material.R.layout.support_simple_spinner_dropdown_item);
        this.type = ArrayAdapter.createFromResource(this,R.array.type, com.google.android.material.R.layout.support_simple_spinner_dropdown_item);
        //this.statut = new ArrayAdapter<String>(this,R.layout.spinner,R.id.statut,statutT);
        this.statut.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        this.type.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        this.etype.setAdapter(this.type);
        this.estatut.setAdapter(this.statut);
        if(ajouter == false) {
            this.bModifAjout.setText("Appliquer");
            //Cursor cursor = this.writeBD.execSQL("select * FROM chambre WHERE id = "+ );
        }else {
            this.bModifAjout.setText("Ajouter");
        }
        this.bModifAjout.setOnClickListener(this);

    }

    public void AjoutChambre(String emax,String eStatut,String eEtage,String eType,String eNum,String eComm) {
        this.writeBD.execSQL("INSERT INTO chambre(maxPersonne,statut,numeroChambre,etage,typeLit,commentaire)  values ('" +emax+"', '" + eStatut + "', '" + eNum + "', '" + eEtage + "', '" + eType+ "', '" + eComm + "');");
    }



    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.bRetours) {
            this.finish();
        }else if(view.getId() == R.id.bModifAjout) {
                        /*Cursor cursor = this.writeBD.execSQL(
                    "INSERT INTO chambre  values('" +this.emax.getText().toString()+"', '" +
                     this.estatut.getText().toString() + "', '" + this.eEtage.getText().toString() + "', '"
                    + this.etype.getText().toString() + "', '" + this.eNum.getText().toString() + "', '"
                    + this.ecomm.getText().toString() + "');");*/
            AjoutChambre(this.emax.getText().toString(),
                    this.statutR.toString(),
                    this.eEtage.getText().toString(),
                    this.typeR.toString(),
                    this.eNum.getText().toString(),
                    this.ecomm.getText().toString());
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.typelit_chambre) {
            this.typeR = adapterView.getItemAtPosition(i);
        }else {
            this.statutR = adapterView.getItemAtPosition(i);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}