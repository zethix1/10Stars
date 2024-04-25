package fr.sio.a10stars.View.Reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import fr.sio.a10stars.Db.DataAccess.Dao.Reservation.ReservationDao;
import fr.sio.a10stars.Db.SingletonDb;
import fr.sio.a10stars.Db.Stars10DB;
import fr.sio.a10stars.Modele.Reservation.Reservation;
import fr.sio.a10stars.R;
import fr.sio.a10stars.Controller.Reservation.AdapterModifReservation;

public class ReservationMenu extends AppCompatActivity implements View.OnClickListener {

    private Stars10DB maBD;

    private SQLiteDatabase writeBD;

    private ArrayAdapter<Reservation> arrayAdapter;

    public List<Reservation> list;

    private ListView listView;

    private Reservation reservation;

    private int id,nbInvite,fkChambre,fkClient;

    private String comm;

    public String dateDebut,dateFin;

    private SingletonDb instance;

    private ReservationDao reservationDao;

    private Button bRetours;


    @Override
    protected void onResume() {
        super.onResume();
        findReservation();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_menu);
        this.listView = findViewById(R.id.reservation2);
        this.arrayAdapter = new AdapterModifReservation(this,R.layout.liste_view_reservation,new ArrayList<>());

        this.bRetours = findViewById(R.id.bRetoursReservationMenu);
        this.bRetours.setOnClickListener(this);
        this.instance = SingletonDb.getInstance(this);
        this.maBD = this.instance.getAppDatabase();
        this.reservationDao = this.maBD.reservationDao();
        findReservation();
        this.listView.setAdapter(this.arrayAdapter);
    }

    public void findReservation() {
        list = new ArrayList<>();
        if(!this.instance.getReservationHashMap().isEmpty()) {
            this.list.addAll(this.instance.getReservationHashMap().values());
        }else {
            this.list = this.reservationDao.findAll();
        }
        this.arrayAdapter.clear();
        for(Reservation reservation1 : this.list) {
            this.arrayAdapter.add(reservation1);
            if(!this.instance.getReservationHashMap().containsValue(reservation1)) {
                this.instance.addToReservationHashMap(reservation1);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.bRetoursReservationMenu) {
            this.finish();
        }
    }
}