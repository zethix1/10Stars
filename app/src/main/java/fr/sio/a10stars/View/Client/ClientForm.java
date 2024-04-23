package fr.sio.a10stars.View.Client;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.regex.Pattern;

import fr.sio.a10stars.Db.DataAccess.Dao.Client.ClientDao;
import fr.sio.a10stars.Db.SingletonDb;
import fr.sio.a10stars.Modele.Client.Client;
import fr.sio.a10stars.R;
import fr.sio.a10stars.Db.Stars10DB;

public class ClientForm extends AppCompatActivity implements View.OnClickListener {

    private EditText enom,eprenom,eEmail,ecomm,etelephone;



    private Button bModifAjout,bRetour,bSuppr,bMaintenance;


    private Bundle bundle;

    private Intent intent;

    private Stars10DB maBD;
    private SQLiteDatabase writeBD;

    private Boolean ajouter;

    private List<Client> list;

    private ClientDao clientDao;

    private SingletonDb instance;

    private int id;


    private ProgressDialog progressDialog;

    private Client client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_form);
        this.enom = findViewById(R.id.nom);
        this.eprenom = findViewById(R.id.prenom);
        this.eEmail = findViewById(R.id.email);
        this.etelephone = findViewById(R.id.telephone);
        this.ecomm = findViewById(R.id.commentaire_client);
        this.bModifAjout = findViewById(R.id.bModifAjoutClient);
        this.bRetour = findViewById(R.id.bRetoursClientForm);
        this.bSuppr = findViewById(R.id.bSupprClient);
        this.bRetour.setOnClickListener(this);
        this.bundle = this.getIntent().getExtras();
        this.ajouter = this.bundle.getBoolean("ajouter");
        this.instance = SingletonDb.getInstance(this);
        this.maBD = this.instance.getAppDatabase();
        this.clientDao = this.maBD.clientDao();

        this.enom.setBackgroundColor(getColor(R.color.white));
        this.eprenom.setBackgroundColor(getColor(R.color.white));
        this.eEmail.setBackgroundColor(getColor(R.color.white));

        if(!ajouter) {
            this.id = this.bundle.getInt("idClient");
            this.client = this.instance.getClientHashMap().get(this.id);
            this.bModifAjout.setText("Appliquer");
            this.enom.setText((this.client.getNom()));
            this.eprenom.setText(this.client.getPrenom());
            this.eEmail.setText(this.client.getEmail());
            this.ecomm.setText(this.client.getComm());
            this.etelephone.setText(this.client.getTelephone());
            this.bSuppr.setOnClickListener(this);
        }else {
            this.bModifAjout.setText("Ajouter");
            if(this.bundle.getString("nomClient") != null) {
                this.enom.setText(this.bundle.getString("nomClient"));
            }
            this.bSuppr.setVisibility(View.GONE);
        }
        this.bModifAjout.setOnClickListener(this);

    }



    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bRetoursClientForm) {
            this.finish();
        }
        else if(this.enom.getText().toString().isEmpty() || this.eprenom.getText().toString().isEmpty() || this.eEmail.getText().toString().isEmpty()) {
            this.enom.setBackgroundColor(getColor(R.color.maintenance));
            this.eprenom.setBackgroundColor(getColor(R.color.maintenance));
            this.eEmail.setBackgroundColor(getColor(R.color.maintenance));
            Toast.makeText(this,"veuillez remplir tous les champs obligatoire",Toast.LENGTH_SHORT).show();
        }else {
            if(!Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",this.eEmail.getText().toString())) {
                this.enom.setBackgroundColor(getColor(R.color.white));
                this.eprenom.setBackgroundColor(getColor(R.color.white));
                this.eEmail.setBackgroundColor(getColor(R.color.maintenance));
                Toast.makeText(this,"veuillez entrez une adresse email valide",Toast.LENGTH_SHORT).show();
            }else {
                this.progressDialog = new ProgressDialog(this);
                this.progressDialog.setMessage("traitement de la requete");
                this.progressDialog.setCancelable(false);
                this.progressDialog.show();
                if (view.getId() == R.id.bModifAjoutClient) {
                    if (this.ajouter) {
                        this.client = new Client(this.enom.getText().toString(),
                                this.eprenom.getText().toString(),
                                this.eEmail.getText().toString(),
                                this.etelephone.getText().toString(),
                                this.ecomm.getText().toString());
                        this.clientDao.insert(this.client);
                        this.instance.getClientHashMap().clear();
                        for (Client client1 : this.clientDao.findAll()) {
                            this.instance.addToClientHashMap(client1);
                        }
                        this.progressDialog.dismiss();
                        Toast.makeText(this, "Client crée avec succèe", Toast.LENGTH_SHORT).show();
                        this.finish();
                    } else {
                        this.client = new Client(this.id,
                                this.enom.getText().toString(),
                                this.eprenom.getText().toString(),
                                this.etelephone.getText().toString(),
                                this.eEmail.getText().toString(),
                                this.ecomm.getText().toString());
                        this.clientDao.update(this.client);
                        this.instance.removeFromClientHashMap(this.client.getId());
                        this.instance.addToClientHashMap(this.client);
                        this.progressDialog.dismiss();
                        Toast.makeText(this, "Client modifié avec succèe", Toast.LENGTH_SHORT).show();
                        this.finish();
                    }
                } else if (view.getId() == R.id.bSupprClient) {
                    this.clientDao.delete(this.instance.getClientHashMap().get(this.id));
                    this.instance.removeFromClientHashMap(this.id);
                    this.progressDialog.dismiss();
                    Toast.makeText(this, "Client supprimé avec succèe", Toast.LENGTH_SHORT).show();
                    this.finish();
                }
            }
        }
    }

}