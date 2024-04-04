package fr.sio.a10stars.Reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.sio.a10stars.Chambre.Chambre;
import fr.sio.a10stars.R;
import fr.sio.a10stars.Db.Stars10DB;

public class Filtrage extends AppCompatActivity implements View.OnClickListener {

    private Button bRetours;

    private Intent intent;

    private int id;

    private int maxP;


    private String statut;


    private int etage;

    private int simple;

    private int double2;

    private String num,nbLitSimple,nbLitDouble,datedebut,datefin;

    private String comm;

    private ListView listView;

    private Bundle bundle;

    private ArrayAdapter<Chambre> arrayAdapter;

    public static List<Chambre> list;

    private Stars10DB maBD;

    private SQLiteDatabase writeBD;

    private Chambre chambre;

    private TextView textView;

    @Override
    protected void onResume() {
        super.onResume();
        this.bundle = this.getIntent().getExtras();
        this.nbLitSimple = this.bundle.getString("litSimple");
        this.nbLitDouble =  this.bundle.getString("litDouble");
        this.datedebut = this.bundle.getString("datedebut");
        this.datefin = this.bundle.getString("datefin");
        findChambre();
    }



    public void findChambre() {
        list = new ArrayList<>();
        Cursor cursor = null;
        if(!this.nbLitSimple.equals("") && !this.nbLitDouble.equals("")) {
            cursor = this.writeBD.rawQuery("SELECT * from chambre WHERE statut = 'disponible' AND nbLitSimple = " + this.nbLitSimple + " AND nbLitDouble = " + this.nbLitDouble + " ;",null);
        } else if (!this.nbLitSimple.equals("") && this.nbLitDouble.equals("")) {
            cursor = this.writeBD.rawQuery("SELECT * from chambre WHERE statut = 'disponible' AND nbLitSimple = " + this.nbLitSimple + " ;",null);
        }else if(this.nbLitSimple.equals("") && !this.nbLitDouble.equals("")) {
            cursor = this.writeBD.rawQuery("SELECT * from chambre WHERE statut = 'disponible' AND nbLitDouble = " + this.nbLitDouble + " ;",null);
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
        this.arrayAdapter.clear();
        this.arrayAdapter.addAll(list);
        if(list.size() <= 0) {
            this.textView.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtrage);
        this.listView = findViewById(R.id.chambreFiltrer);
        this.textView = findViewById(R.id.introuvable);
        this.arrayAdapter = new AdapterSelectionChambre(this,R.layout.chambre_filtrer,new ArrayList<>());

        this.maBD = new Stars10DB(this);
        this.writeBD = this.maBD.getWritableDatabase();

        this.bundle = this.getIntent().getExtras();
        this.nbLitSimple = this.bundle.getString("litSimple");
        this.nbLitDouble =  this.bundle.getString("litDouble");
        this.datedebut = this.bundle.getString("datedebut");
        this.datefin = this.bundle.getString("datefin");
        findChambre();
        this.listView.setAdapter(this.arrayAdapter);
        this.bRetours = findViewById(R.id.bRetoursFiltrer);
        this.bRetours.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.bRetoursFiltrer) {
            this.finish();
        }
    }
    }
