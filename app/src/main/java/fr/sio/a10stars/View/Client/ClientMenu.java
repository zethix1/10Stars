package fr.sio.a10stars.View.Client;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.sio.a10stars.Controller.Client.AdapterModifClient;
import fr.sio.a10stars.Db.DataAccess.Dao.Client.ClientDao;
import fr.sio.a10stars.Db.SingletonDb;
import fr.sio.a10stars.Modele.Client.Client;
import fr.sio.a10stars.R;
import fr.sio.a10stars.Db.Stars10DB;

public class ClientMenu extends AppCompatActivity implements View.OnClickListener {

    private Button bModif,bAjout,bRetours;

    private Intent intent;

    private int id;

    private String comm,nom,prenom,email,telephone;

    private ListView listView;

    private ArrayAdapter<Client> arrayAdapter;

    public List<Client> list;

    private Stars10DB maBD;

    private SQLiteDatabase writeBD;

    private Client client;


    private ClientDao clientDao;

    private SingletonDb instance;
    @Override
    protected void onResume() {
        super.onResume();
        findClient();
    }



    public void searchClient(String query) {
        this.list = new ArrayList<>();
        if(!this.instance.getClientHashMap().isEmpty()) {
            for (Map.Entry<Integer, Client> entry : this.instance.getClientHashMap().entrySet()) {
                if(entry.getValue().getNom().contains(query)) {
                    this.list.add(entry.getValue());
                }
            }
            this.list.addAll(this.instance.getClientHashMap().values());
        }else {
            this.list = this.clientDao.findClientsByName(query);
            this.arrayAdapter.clear();
            for (Client client1 : this.list) {
                this.arrayAdapter.add(client1);
            }
        }
    }

    public void findClient() {
        this.list = new ArrayList<>();
        if(!this.instance.getClientHashMap().isEmpty()) {
            this.list.addAll(this.instance.getClientHashMap().values());
        }else {
            this.list = this.clientDao.findAll();
        }
        this.arrayAdapter.clear();
        this.instance.getClientHashMap().clear();
        for (Client client1 : this.list) {
            this.instance.addToClientHashMap(client1);
            this.arrayAdapter.add(client1);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_menu);
        this.bAjout = findViewById(R.id.bAjoutClient);
        this.listView = findViewById(R.id.client2);

        this.instance = SingletonDb.getInstance(this);
        this.maBD = this.instance.getAppDatabase();
        this.clientDao = this.maBD.clientDao();

        this.arrayAdapter = new AdapterModifClient(this,R.layout.liste_view_client,new ArrayList<>());
        findClient();
        this.listView.setAdapter(this.arrayAdapter);
        this.bRetours = findViewById(R.id.bRetours2Client);
        this.bAjout.setOnClickListener(this);
        this.bRetours.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.bAjoutClient) {
            this.intent = new Intent(this, ClientForm.class);
            this.intent.putExtra("ajouter",true);
            startActivity(this.intent);
        }else if(view.getId() == R.id.bRetours2Client) {
            this.finish();
        }
    }
}