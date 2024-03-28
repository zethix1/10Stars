package fr.sio.a10stars.Chambre;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import fr.sio.a10stars.R;
import fr.sio.a10stars.Db.Stars10DB;

public class ChambreForm extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private EditText emax,eEtage,eNum,ecomm,eSimple,eDouble;

    private Spinner estatut;


    private Button bModifAjout,bRetour,bSuppr,bMaintenance;

    private ArrayAdapter<CharSequence> statut;


    private Object statutR;

    private Object typeR;

    private String[] statutT = {"disponible,occupe,maintenance"};

    private Bundle bundle;

    private Intent intent;

    private Stars10DB maBD;
    private SQLiteDatabase writeBD;

    private Boolean ajouter;

    private List<Chambre> list = ChambreMenu.list;

    private Chambre chambre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chambre_form);
        this.emax = findViewById(R.id.max_personne);
        this.estatut = findViewById(R.id.statut_chambre_modification);
        this.eEtage = findViewById(R.id.etage_chambre);
        this.eSimple = findViewById(R.id.nb_lit_simple_chambre);
        this.eDouble = findViewById(R.id.nb_lit_double_chambre);
        this.eNum = findViewById(R.id.num_chambre);
        this.ecomm = findViewById(R.id.commentaire_chambre);
        this.bModifAjout = findViewById(R.id.bModifAjout);
        this.bRetour = findViewById(R.id.bRetours);
        this.bSuppr = findViewById(R.id.bSuppr);
        this.bRetour.setOnClickListener(this);
        this.bundle = this.getIntent().getExtras();
        this.ajouter = this.bundle.getBoolean("ajouter");
        this.maBD = new Stars10DB(this);
        this.writeBD = this.maBD.getWritableDatabase();
        this.estatut.setOnItemSelectedListener(this);

        this.statut = ArrayAdapter.createFromResource(this,R.array.statut, com.google.android.material.R.layout.support_simple_spinner_dropdown_item);
        this.statut.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        this.estatut.setAdapter(this.statut);
        if(!ajouter) {
            this.bSuppr.setOnClickListener(this);
            for (int i=0;i<list.size();i++) {
                this.chambre = list.get(i);
                if(this.chambre.getId() == Chambre.CurrentIdItem) {
                    this.bModifAjout.setText("Appliquer");
                    this.emax.setText(Integer.toString(this.chambre.getMaxP()));
                    if(this.chambre.getStatut().equals("disponible")) {
                        this.estatut.setSelection(0);
                    } else if (this.chambre.getStatut().equals("occupe")) {
                        this.estatut.setSelection(1);
                    }else {
                        this.estatut.setSelection(2);
                    }
                    this.eEtage.setText(Integer.toString(this.chambre.getEtage()));
                    this.eNum.setText(this.chambre.getNum());
                    this.ecomm.setText(this.chambre.getComm());
                    this.eSimple.setText(Integer.toString(this.chambre.getNb_lit_simple()));
                    this.eDouble.setText(Integer.toString(this.chambre.getNb_lit_double()));

                }
            }
        }else {
            this.bSuppr.setVisibility(View.INVISIBLE);
            this.bModifAjout.setText("Ajouter");
        }
        this.bModifAjout.setOnClickListener(this);

    }

    public void AjoutChambre(String emax,String eStatut,String nbLitSimple,String nbLitDouble,String eEtage,String eNum,String eComm) {
        this.writeBD.execSQL("INSERT INTO chambre(maxPersonne,statut,numeroChambre,etage,nbLitSimple,nbLitDouble,commentaire)  values ('" +emax+"', '" + eStatut + "', '" + eNum + "', '" + eEtage + "', " + nbLitSimple + ", '" + nbLitDouble + "', '" + eComm + "');");
    }

    public void ModifChambre(int id,String emax,String eStatut,String nbLitSimple,String nbLitDouble,String eEtage,String eNum,String eComm) {
        this.writeBD.execSQL("UPDATE chambre SET maxPersonne = " + emax + " , statut = '" + eStatut + "' , nbLitSimple = " + nbLitSimple + " , nbLitDouble = " + nbLitDouble +" , numeroChambre = " + eNum + " , etage = " + eEtage + " , commentaire = '" + eComm + "' WHERE id = " + id + ";");
    }

    public void SupprChambre(int id) {
        this.writeBD.execSQL("DELETE FROM chambre WHERE id = " + id + ";");
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.bRetours) {
            this.finish();
        }else if(view.getId() == R.id.bModifAjout) {
            if(this.ajouter) {
                AjoutChambre(this.emax.getText().toString(),
                        this.statutR.toString(),
                        this.eSimple.getText().toString(),
                        this.eDouble.getText().toString(),
                        this.eEtage.getText().toString(),
                        this.eNum.getText().toString(),
                        this.ecomm.getText().toString());
                this.finish();
            } else if (this.ajouter == false) {
                ModifChambre(this.chambre.getId(),
                        this.emax.getText().toString(),
                        this.statutR.toString(),
                        this.eSimple.getText().toString(),
                        this.eDouble.getText().toString(),
                        this.eEtage.getText().toString(),
                        this.eNum.getText().toString(),
                        this.ecomm.getText().toString());
                this.finish();
            }
        }else if(view.getId() == R.id.bSuppr) {
            SupprChambre(this.chambre.getId());
            this.finish();
        }/*else if (view.getId() == R.id.bEnMaintenance) {

        }*/
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.statut_chambre_modification) {
            this.statutR = adapterView.getItemAtPosition(i);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}