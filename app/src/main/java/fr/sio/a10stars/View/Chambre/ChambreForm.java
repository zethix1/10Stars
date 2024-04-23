package fr.sio.a10stars.View.Chambre;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import fr.sio.a10stars.Db.DataAccess.Dao.Chambre.ChambreDao;
import fr.sio.a10stars.Db.SingletonDb;
import fr.sio.a10stars.Modele.Chambre.Chambre;
import fr.sio.a10stars.R;
import fr.sio.a10stars.Db.Stars10DB;

public class ChambreForm extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private EditText emax,eEtage,eNum,ecomm,eSimple,eDouble;

    private Spinner estatut;


    private Button bModifAjout,bRetour,bSuppr,bMaintenance;

    private ArrayAdapter<CharSequence> statut;

    private ProgressDialog progressDialog;


    private Object statutR;

    private Object typeR;

    private String[] statutT = {"disponible,occupe,maintenance"};

    private Bundle bundle;

    private Intent intent;

    private Stars10DB maBD;
    private SQLiteDatabase writeBD;

    private Boolean ajouter;

    private List<Chambre> list;

    private Chambre chambre;

    private SingletonDb instance;

    private ChambreDao chambreDao;

    private int id;

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
        this.instance = SingletonDb.getInstance(this);
        this.maBD = this.instance.getAppDatabase();
        this.chambreDao = this.maBD.chambreDao();
        this.estatut.setOnItemSelectedListener(this);

        this.statut = ArrayAdapter.createFromResource(this,R.array.statut, com.google.android.material.R.layout.support_simple_spinner_dropdown_item);
        this.statut.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        this.estatut.setAdapter(this.statut);

        this.emax.setBackgroundColor(getColor(R.color.white));
        this.eEtage.setBackgroundColor(getColor(R.color.white));
        this.eNum.setBackgroundColor(getColor(R.color.white));
        this.eSimple.setBackgroundColor(getColor(R.color.white));
        this.eDouble.setBackgroundColor(getColor(R.color.white));


        if(!ajouter) {
            this.id = this.bundle.getInt("chambreId");
            this.bSuppr.setOnClickListener(this);
            this.bModifAjout.setText("Appliquer");
            this.chambre = this.instance.getChambreHashMap().get(this.id);
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
        }else {
            this.bSuppr.setVisibility(View.GONE);
            if(this.bundle.getString("nomChambre") != null) {
                this.eNum.setText(this.bundle.getString("nomChambre"));
            }
            this.bModifAjout.setText("Ajouter");
        }
        this.bModifAjout.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bRetours) {
            this.finish();
        } else if(this.emax.getText().toString().isEmpty() || this.eEtage.getText().toString().isEmpty() || this.eNum.getText().toString().isEmpty() ||this.eSimple.getText().toString().isEmpty() || this.eDouble.getText().toString().isEmpty()) {
            this.emax.setBackgroundColor(getColor(R.color.maintenance));
            this.eEtage.setBackgroundColor(getColor(R.color.maintenance));
            this.eNum.setBackgroundColor(getColor(R.color.maintenance));
            this.eSimple.setBackgroundColor(getColor(R.color.maintenance));
            this.eDouble.setBackgroundColor(getColor(R.color.maintenance));
            Toast.makeText(this,"veuillez remplir tous les champs obligatoire",Toast.LENGTH_SHORT).show();
        }else {
            this.progressDialog = new ProgressDialog(this);
            this.progressDialog.setMessage("traitement de la requete");
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();
             if (view.getId() == R.id.bModifAjout) {
                if (this.ajouter) {
                    this.chambre = new Chambre(Integer.parseInt(this.emax.getText().toString()),
                            this.statutR.toString(),
                            Integer.parseInt(this.eSimple.getText().toString()),
                            Integer.parseInt(this.eDouble.getText().toString()),
                            Integer.parseInt(this.eEtage.getText().toString()),
                            this.eNum.getText().toString(),
                            this.ecomm.getText().toString());
                    this.chambreDao.insert(this.chambre);
                    this.instance.getChambreHashMap().clear();
                    for (Chambre chambre1 : this.chambreDao.findAll()) {
                        this.instance.addToChambreHashMap(chambre1);
                    }
                    this.progressDialog.dismiss();
                    Toast.makeText(this, "Chambre crée avec succèe", Toast.LENGTH_SHORT).show();
                    this.finish();
                } else {
                    this.chambre = new Chambre(this.id,
                            Integer.parseInt(this.emax.getText().toString()),
                            this.statutR.toString(),
                            Integer.parseInt(this.eSimple.getText().toString()),
                            Integer.parseInt(this.eDouble.getText().toString()),
                            Integer.parseInt(this.eEtage.getText().toString()),
                            this.eNum.getText().toString(),
                            this.ecomm.getText().toString());
                    this.chambreDao.update(this.chambre);
                    this.instance.removeFromChambreHashMap(this.chambre.getId());
                    this.instance.addToChambreHashMap(this.chambre);
                    this.progressDialog.dismiss();
                    Toast.makeText(this, "Chambre modifié avec succèe", Toast.LENGTH_SHORT).show();
                    this.finish();
                }
            } else if (view.getId() == R.id.bSuppr) {
                this.chambreDao.delete(this.instance.getChambreHashMap().get(this.id));
                this.instance.removeFromChambreHashMap(this.id);
                this.progressDialog.dismiss();
                Toast.makeText(this, "Client supprimé avec succèe", Toast.LENGTH_SHORT).show();
                this.finish();
            }
        }
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