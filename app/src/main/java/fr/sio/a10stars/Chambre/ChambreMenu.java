package fr.sio.a10stars.Chambre;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fr.sio.a10stars.R;
import fr.sio.a10stars.Stars10DB;

public class ChambreMenu extends AppCompatActivity implements View.OnClickListener {

    private Button bModif,bAjout,bRetours,bMaintenance;

    private Intent intent;

    private int id;

    private int maxP;

    private Boolean maintenance;

    private String statut;


    private int etage;

    private int simple;

    private int double2;

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
        this.maintenance = false;
        findChambre(this.maintenance);
    }



    public void findChambre(boolean maintenance) {
        list = new ArrayList<>();
        Cursor cursor = null;
        if(!maintenance) {
            cursor = this.writeBD.rawQuery("SELECT * from chambre WHERE statut = 'disponible' OR statut = 'occupe';",null);

        }else {
            cursor = this.writeBD.rawQuery("SELECT * from chambre WHERE statut = 'maintenance';",null);
        }

        if(cursor != null) {
            while (cursor.moveToNext()) {
                this.id = cursor.getInt(0);
                this.maxP = cursor.getInt(1);
                this.statut = cursor.getString(2);
                this.etage = cursor.getInt(3);
                this.simple = cursor.getInt(4);
                this.double2 = cursor.getInt(5);
                this.num = cursor.getString(6);
                this.comm = cursor.getString(7);
                this.chambre = new Chambre(this.id,this.maxP,this.statut,this.simple,this.double2,this.etage,this.num,this.comm);
                list.add(chambre);
            }
            cursor.close();
        }
        this.maintenance = !this.maintenance;
        this.bMaintenance.setText(this.maintenance ? "En Maintenance" : "Toute les Chambres");
        this.arrayAdapter.clear();
        this.arrayAdapter.addAll(list);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chambre_menu);
        this.bAjout = findViewById(R.id.bAjout);
        this.maintenance = false;
        this.listView = findViewById(R.id.chambre2);
        //this.bModif = findViewById(R.id.bModif);
        this.bMaintenance = findViewById(R.id.maintenance);
        this.bMaintenance.setOnClickListener(this);
        this.arrayAdapter = new AdapterModifChambre(this,R.layout.liste_view_chambre,new ArrayList<>());

        this.maBD = new Stars10DB(this);
        this.writeBD = this.maBD.getWritableDatabase();

        findChambre(false);
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
        }else if (view.getId() == R.id.maintenance) {
            findChambre(this.maintenance);
        }
    }
}