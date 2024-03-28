package fr.sio.a10stars.Client;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

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

    private List<Client> list = ClientMenu.list;

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
        this.maBD = new Stars10DB(this);
        this.writeBD = this.maBD.getWritableDatabase();

        if(!ajouter) {
            for (int i=0;i<list.size();i++) {
                this.client = list.get(i);
                if(this.client.getId() == Client.CurrentIdItem) {
                    this.bModifAjout.setText("Appliquer");
                    this.enom.setText((this.client.getNom()));
                    this.eprenom.setText(this.client.getPrenom());
                    this.eEmail.setText(this.client.getEmail());
                    this.ecomm.setText(this.client.getComm());
                    this.etelephone.setText(this.client.getTelephone());
                    this.bSuppr.setOnClickListener(this);
                }
            }
        }else {
            this.bModifAjout.setText("Ajouter");
            this.bSuppr.setVisibility(View.INVISIBLE);
        }
        this.bModifAjout.setOnClickListener(this);

    }

    public void AjoutClient(String enom,String eprenom,String email,String etelephone,String eComm) {
        this.writeBD.execSQL("INSERT INTO clients(nom,prenom,email,telephone,commentaire)  values ('" +enom+"', '" + eprenom + "', '" + email + "', '" + etelephone +"', '" + eComm + "');");
    }

    public void ModifClient(int id,String enom,String eprenom,String email,String etelephone,String eComm) {
        this.writeBD.execSQL("UPDATE clients SET nom = '" + enom + "' , prenom = '" + eprenom + "' , email = '" + email + "' , telephone = '" + etelephone +"' , commentaire = '" + eComm + "' WHERE id = " + id + ";");
    }

    public void SupprClient(int id) {
        this.writeBD.execSQL("DELETE FROM clients WHERE id = " + id + ";");
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.bRetoursClientForm) {
            this.finish();
        }else if(view.getId() == R.id.bModifAjoutClient) {
            if(this.ajouter) {
                AjoutClient(this.enom.getText().toString(),
                        this.eprenom.getText().toString(),
                        this.eEmail.getText().toString(),
                        this.etelephone.getText().toString(),
                        this.ecomm.getText().toString());
                this.finish();
            } else if (!this.ajouter) {
                ModifClient(this.client.getId(),
                        this.enom.getText().toString(),
                        this.eprenom.getText().toString(),
                        this.etelephone.getText().toString(),
                        this.eEmail.getText().toString(),
                        this.ecomm.getText().toString());
                this.finish();
            }
        }else if(view.getId() == R.id.bSupprClient) {
            SupprClient(this.client.getId());
            this.finish();
        }
    }

}