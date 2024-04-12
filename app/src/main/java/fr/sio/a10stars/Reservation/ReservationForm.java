package fr.sio.a10stars.Reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fr.sio.a10stars.Chambre.Chambre;
import fr.sio.a10stars.Client.Client;
import fr.sio.a10stars.Db.Stars10DB;
import fr.sio.a10stars.R;

public class ReservationForm extends AppCompatActivity implements View.OnClickListener, TextWatcher, AdapterView.OnItemSelectedListener {

    private Button bRetours,bAjout;

    private Spinner sClient;

    private TextView tNbLitSimple,tNbLitDouble,tNumChambre,tMaxP;

    private EditText eNbAdulte,eNbEnfant;

    private Chambre chambre;

    private Stars10DB maBD;

    private SQLiteDatabase writeBD;

    private ArrayAdapter<String> client;

    private ArrayList<String> clientA;

    private int etage,id,maxP,idClient,idClientMap;

    private Map<String,Integer> clientMap = new HashMap<>();

    private int simple;

    private int double2;

    private String num,statut;

    private String comm,nomC,prenomC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_form);
        this.bRetours = findViewById(R.id.bRetoursReservation);
        this.bRetours.setOnClickListener(this);
        this.tNbLitSimple = findViewById(R.id.tnbLitSimple_reserv);
        this.tNbLitDouble = findViewById(R.id.tnbLitSDouble_reserv);
        this.tNumChambre = findViewById(R.id.tnumChambreReserv);
        this.eNbAdulte = findViewById(R.id.eNbNombreAdulte);
        this.eNbEnfant = findViewById(R.id.eNbNombreEnfant);
        this.tMaxP = findViewById(R.id.tnbMaxPersonneReserv);
        this.eNbEnfant.addTextChangedListener(this);
        this.eNbAdulte.addTextChangedListener(this);
        int maxLength = 9;
        this.eNbAdulte.setFilters(new InputFilter[] {new MaxLengthFilter(maxLength)});
        this.eNbEnfant.setFilters(new InputFilter[]{new MaxLengthFilter(maxLength)});
        this.bAjout = findViewById(R.id.bAjoutReserv);
        this.bAjout.setOnClickListener(this);
        this.sClient = findViewById(R.id.client_spinner_reservation);
        this.sClient.setOnItemSelectedListener(this);

        this.maBD = new Stars10DB(this);
        this.writeBD = this.maBD.getWritableDatabase();
        findClient();
        this.client = new ArrayAdapter<>(this,R.layout.spinner_client,this.clientA);
        this.client.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        this.sClient.setAdapter(this.client);

        findChambre();
    }

    public void findClient() {
        Cursor cursor = this.writeBD.rawQuery("SELECT id,nom,prenom from clients ;",null);
        this.clientA = new ArrayList<>();

        this.clientA.add("pas de client disponible");
        if(cursor != null) {
            while (cursor.moveToNext()) {
                this.clientA.clear();
                this.idClient = cursor.getInt(0);
                this.nomC = cursor.getString(1);
                this.prenomC = cursor.getString(2);
                String nomComplet = this.nomC + " " + this.prenomC;
                this.clientA.add(nomComplet);
                this.clientMap.put(nomComplet,this.idClient);
            }
            cursor.close();
        }
    }

    public void findChambre() {
        Cursor cursor = this.writeBD.rawQuery("SELECT * from chambre WHERE statut = 'disponible' AND id = " + Chambre.CurrentIdItem + " ;",null);

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
            }
            cursor.close();
        }
        this.tNumChambre.setText(this.chambre.getNum());
        this.tNbLitDouble.setText(Integer.toString(this.chambre.getNb_lit_double()));
        this.tNbLitSimple.setText(Integer.toString(this.chambre.getNb_lit_simple()));
    }

    public void ajoutReservation(String dateArrivee,String dateDepart,String nombreInvites,String comm,int idCli,int idCham) {
        this.writeBD.rawQuery("INSERT INTO reservations(dateArrivee,dateDepart,nombreInvites,commentaire,idChambre,idClient) values("+dateArrivee +" , "+dateDepart +" , " + nombreInvites + " , "+comm+" , '" + idCham+"' , '"+idCham+"');",null);
    }
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.bRetoursReservation) {
            this.finish();
        } else if (view.getId() == R.id.bAjoutReserv) {
            ajoutReservation(Reservation.dateDebut,Reservation.dateFin,"0",this.comm,this.id,this.idClientMap);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String adulteText = this.eNbAdulte.getText().toString();
        String enfantText = this.eNbEnfant.getText().toString();

        if (!adulteText.isEmpty() && !enfantText.isEmpty()) {
            int adulteCount = Integer.parseInt(adulteText);
            int enfantCount = Integer.parseInt(enfantText);
            this.tMaxP.setText(String.valueOf(adulteCount + enfantCount));
        } else if (adulteText.isEmpty() && !enfantText.isEmpty()) {
            this.tMaxP.setText(enfantText);
        } else {
            this.tMaxP.setText(adulteText);
        }
    }


    @Override
    public void afterTextChanged(Editable editable) {

    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String nomClient = adapterView.getItemAtPosition(i).toString();
        if(nomClient != null) {
            this.idClientMap = this.clientMap.get(nomClient);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}