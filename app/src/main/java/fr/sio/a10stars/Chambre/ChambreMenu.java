package fr.sio.a10stars.Chambre;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import fr.sio.a10stars.R;
import fr.sio.a10stars.Stars10DB;

public class ChambreMenu extends AppCompatActivity implements View.OnClickListener {

    private Button bModif,bAjout,bRetours;

    private Intent intent;

    private int id;

    private int maxP;

    private String statut;

    private int etage;

    private String type;

    private String num;

    private String comm;

    private ListView listView;

    private ArrayAdapter<Chambre> arrayAdapter;

    public static List<Chambre> list;

    private Stars10DB maBD;

    private SQLiteDatabase writeBD;

    private Chambre chambre;

    @Override
    protected void onResume() {
        super.onResume();
        findChambre();
    }

    public void findChambre() {
        list = new ArrayList<>();
        Cursor cursor = this.writeBD.rawQuery("SELECT * from chambre;",null);

        if(cursor != null) {
            while (cursor.moveToNext()) {
                this.id = cursor.getInt(0);
                this.maxP = cursor.getInt(1);
                this.statut = cursor.getString(2);
                this.etage = cursor.getInt(3);
                this.type = cursor.getString(4);
                this.num = cursor.getString(5);
                this.comm = cursor.getString(6);
                this.chambre = new Chambre(this.id,this.maxP,this.statut,this.etage,this.type,this.num,this.comm);
                list.add(chambre);
            }
            cursor.close();
        }
        this.arrayAdapter.clear();
        this.arrayAdapter.addAll(list);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chambre_menu);
        this.bAjout = findViewById(R.id.bAjout);
        this.listView = findViewById(R.id.chambre2);
        //this.bModif = findViewById(R.id.bModif);
        this.arrayAdapter = new AdapterModifChambre(this,R.layout.liste_view_chambre,new ArrayList<>());

        this.maBD = new Stars10DB(this);
        this.writeBD = this.maBD.getWritableDatabase();

        findChambre();
        this.listView.setAdapter(this.arrayAdapter);
        this.bRetours = findViewById(R.id.bRetours2);
        //this.bModif.setOnClickListener(this);
        this.bAjout.setOnClickListener(this);
        this.bRetours.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.bAjout) {
            this.intent = new Intent(this, ChambreForm.class);
            this.intent.putExtra("ajouter",true);
            startActivity(this.intent);
        }else if(view.getId() == R.id.bRetours2) {
            this.finish();
        }
    }
}