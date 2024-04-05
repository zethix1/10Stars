package fr.sio.a10stars.Client;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import fr.sio.a10stars.R;
import fr.sio.a10stars.Db.Stars10DB;

public class ClientMenu extends AppCompatActivity implements View.OnClickListener, SearchView.OnQueryTextListener {

    private Button bModif,bAjout,bRetours;

    private Intent intent;

    private int id;

    private String comm,nom,prenom,email,telephone;

    private ListView listView;

    private ArrayAdapter<Client> arrayAdapter;

    public static List<Client> list;

    private Stars10DB maBD;

    private SQLiteDatabase writeBD;

    private Client client;

    private SearchView search_client;

    @Override
    protected void onResume() {
        super.onResume();
        findClient();
    }



    public void searchClient(String query) {
        list = new ArrayList<>();
        Cursor cursor = null;
        cursor = this.writeBD.rawQuery("SELECT * from clients WHERE nom LIKE '%" + query + "%'",null);

        if(cursor != null) {
            while (cursor.moveToNext()) {
                this.id = cursor.getInt(0);
                this.nom = cursor.getString(1);
                this.prenom = cursor.getString(2);
                this.email = cursor.getString(3);
                this.telephone = cursor.getString(4);
                this.comm = cursor.getString(5);
                this.client = new Client(this.id,this.nom,this.prenom,this.email,this.telephone,this.comm);
                list.add(client);
            }
            cursor.close();
        }
        this.arrayAdapter.clear();
        this.arrayAdapter.addAll(list);
    }

    public void findClient() {
        list = new ArrayList<>();
        Cursor cursor = null;
        cursor = this.writeBD.rawQuery("SELECT * from clients",null);

        if(cursor != null) {
            while (cursor.moveToNext()) {
                this.id = cursor.getInt(0);
                this.nom = cursor.getString(1);
                this.prenom = cursor.getString(2);
                this.email = cursor.getString(3);
                this.telephone = cursor.getString(4);
                this.comm = cursor.getString(5);
                this.client = new Client(this.id,this.nom,this.prenom,this.email,this.telephone,this.comm);
                list.add(client);
            }
            cursor.close();
        }
        this.arrayAdapter.clear();
        this.arrayAdapter.addAll(list);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_menu);
        this.bAjout = findViewById(R.id.bAjoutClient);
        this.listView = findViewById(R.id.client2);
        this.arrayAdapter = new AdapterModifClient(this,R.layout.liste_view_client,new ArrayList<>());

        this.maBD = new Stars10DB(this);
        this.writeBD = this.maBD.getWritableDatabase();

        this.search_client = findViewById(R.id.search_client);
        this.search_client.setOnQueryTextListener(this);
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        searchClient(newText);
        return true;
    }
}