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

import fr.sio.a10stars.R;
import fr.sio.a10stars.Stars10DB;

public class ChambreMenu extends AppCompatActivity implements View.OnClickListener {

    private Button bModif,bAjout,bRetours;

    private Intent intent;

    private int maxP;

    private String statut;

    private int etage;

    private String type;

    private String num;

    private String comm;

    private ListView listView;

    private ArrayAdapter<String> arrayAdapter;

    private Stars10DB maBD;

    private SQLiteDatabase writeBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chambre_menu);
        this.bAjout = findViewById(R.id.bAjout);
        this.listView = findViewById(R.id.chambre2);
        //this.bModif = findViewById(R.id.bModif);
        this.arrayAdapter = new ArrayAdapter<String>(this,R.layout.liste_view_chambre,R.id.chambre3);

        this.maBD = new Stars10DB(this);
        this.writeBD = this.maBD.getWritableDatabase();
        Cursor cursor = this.writeBD.rawQuery("SELECT * from chambre;",null);

        if(cursor != null) {
            while (cursor.moveToNext()) {
                this.maxP = cursor.getInt(1);
                this.statut = cursor.getString(2);
                this.etage = cursor.getInt(3);
                this.type = cursor.getString(4);
                this.num = cursor.getString(5);
                this.comm = cursor.getString(6);
                this.arrayAdapter.add("Nombre de personnes maximum: "
                        + this.maxP + "\n" + "Statut: "
                        + this.statut + "\n"
                        + "Etage: " + this.etage + "\n"
                        + "Type: " + this.type + "\n"
                        + "Num: " +this.num + "\n"
                        + "Commentaire: " +this.comm
                );
            }
            cursor.close();
        }

        this.listView.setAdapter(this.arrayAdapter);
        this.bRetours = findViewById(R.id.bRetours2);

        //this.bModif.setOnClickListener(this);
        this.bAjout.setOnClickListener(this);
        this.bRetours.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Boolean ajouter = true;
        if(view.getId() == R.id.bAjout) {
            this.intent = new Intent(this, ChambreForm.class);
            this.intent.putExtra("ajouter",ajouter);
            startActivity(this.intent);
        /*}else if(view.getId() == R.id.bModif){
            ajouter = false;
            this.intent = new Intent(this, ChambreForm.class);
            this.intent.putExtra("ajouter",ajouter);
            startActivity(this.intent);*/
        }else if(view.getId() == R.id.bRetours2) {
            this.finish();
        }
    }
}