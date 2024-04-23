package fr.sio.a10stars.View.Chambre;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fr.sio.a10stars.Controller.Chambre.AdapterModifChambre;
import fr.sio.a10stars.Db.DataAccess.Dao.Chambre.ChambreDao;
import fr.sio.a10stars.Db.DataAccess.Dao.Maintenance.MaintenanceDao;
import fr.sio.a10stars.Db.DataAccess.Dao.Reservation.ReservationDao;
import fr.sio.a10stars.Db.SingletonDb;
import fr.sio.a10stars.Modele.Chambre.Chambre;
import fr.sio.a10stars.Modele.Maintenance.Maintenance;
import fr.sio.a10stars.Modele.Reservation.Reservation;
import fr.sio.a10stars.R;
import fr.sio.a10stars.Db.Stars10DB;

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

    public List<Chambre> list;

    public List<Reservation> list2;

    public List<Maintenance> list3;

    private Stars10DB maBD;

    private SQLiteDatabase writeBD;

    private Chambre chambre;


    private SingletonDb instance;

    private ChambreDao chambreDao;

    private ReservationDao reservationDao;

    private MaintenanceDao maintenanceDao;

    private Bundle bundle;

    public static boolean maintenance2;


    @Override
    protected void onResume() {
        super.onResume();
        this.maintenance = false;
        findChambre(this.maintenance);
    }


    public void searchChambre(boolean maintenance, String query) {
        this.list = new ArrayList<>();
        if (!this.instance.getChambreHashMap().isEmpty()) {
            this.list.addAll(this.instance.getChambreHashMap().values().stream()
                    .filter(chambre -> chambre.getNum().contains(query))
                    .collect(Collectors.toList()));
        } else {
            this.list = maintenance ? this.chambreDao.findChambresByNumMaintenance(query) :
                    this.chambreDao.findChambresByNumDispoOccupe(query);
            this.arrayAdapter.clear();
            this.arrayAdapter.addAll(list);
        }
    }


    public void findChambre(boolean maintenance) {
        list = new ArrayList<>();
        if (!this.instance.getChambreHashMap().isEmpty()) {
            if (!maintenance) {
                list.addAll(this.instance.getChambreHashMap().values().stream()
                        .filter(chambre -> chambre.getStatut().equals("disponible") || chambre.getStatut().equals("occupe"))
                        .collect(Collectors.toList()));
            } else {
                list.addAll(this.instance.getChambreHashMap().values().stream()
                        .filter(chambre -> chambre.getStatut().equals("maintenance"))
                        .collect(Collectors.toList()));
            }
        } else {
            this.list = maintenance ? this.chambreDao.findChambreMaintenance() :
                    this.chambreDao.findChambreDispoOccupe();
        }
        findReservation();
        findMaintenance();
        this.maintenance = !this.maintenance;
        this.bMaintenance.setText(this.maintenance ? "En Maintenance" : "Toute les Chambres");
        this.instance.getChambreHashMap().clear();
        for (Chambre chambre1 : this.list) {
            this.instance.addToChambreHashMap(chambre1);
            this.arrayAdapter.add(chambre1);
        }
        for(Chambre chambre1: this.instance.getChambreHashMap().values()) {
            chambre1.setStatut("disponible");
            for(Reservation reservation: this.list2) {
                if(chambre1.getId() == reservation.getFkChambre()) {
                    chambre1.setStatut("occupe");
                }
            }

            for(Maintenance maintenance1: this.list3) {
                if(chambre1.getId() == maintenance1.getFkChambre()) {
                    chambre1.setStatut("maintenance");
                }
            }
            this.chambreDao.update(chambre1);
            this.list.remove(chambre1);
            this.list.add(chambre1);
        }
        this.arrayAdapter.clear();
        this.instance.getChambreHashMap().clear();
        for (Chambre chambre1 : this.list) {
            this.instance.addToChambreHashMap(chambre1);
            this.arrayAdapter.add(chambre1);
        }
    }

    public void findMaintenance() {
        list3 = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if(!this.instance.getReservationHashMap().isEmpty()) {
            this.list3.addAll(this.instance.getMaintenanceHashMap().values().stream().filter(
                            maintenance ->
                                    (LocalDate.parse(maintenance.getDateDebut(),formatter).isEqual(LocalDate.now())
                                            || LocalDate.parse(maintenance.getDateDebut(),formatter).isAfter(LocalDate.now()))
                                            && (LocalDate.parse(maintenance.getDateFin(),formatter).isEqual(LocalDate.now()))
                                            || LocalDate.parse(maintenance.getDateFin(),formatter).isBefore(LocalDate.now()))
                    .collect(Collectors.toList()));
        }else {
            this.list3 = this.maintenanceDao.findAll().stream().filter(
                            maintenance ->
                                    (LocalDate.parse(maintenance.getDateDebut(),formatter).isEqual(LocalDate.now())
                                            || LocalDate.parse(maintenance.getDateDebut(),formatter).isAfter(LocalDate.now()))
                                            && (LocalDate.parse(maintenance.getDateFin(),formatter).isEqual(LocalDate.now()))
                                            || LocalDate.parse(maintenance.getDateFin(),formatter).isBefore(LocalDate.now()))
                    .collect(Collectors.toList());
        }
        this.instance.getMaintenanceHashMap().clear();
    }

    public void findReservation() {
        list2 = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if(!this.instance.getReservationHashMap().isEmpty()) {
            this.list2.addAll(this.instance.getReservationHashMap().values().stream().filter(
                    reservation ->
                            (LocalDate.parse(reservation.getDateDebut(),formatter).isEqual(LocalDate.now())
                                    || LocalDate.parse(reservation.getDateDebut(),formatter).isAfter(LocalDate.now()))
                                    && (LocalDate.parse(reservation.getDateFin(),formatter).isEqual(LocalDate.now()))
                                    || LocalDate.parse(reservation.getDateFin(),formatter).isBefore(LocalDate.now()))
                    .collect(Collectors.toList()));
        }else {
            this.list2 = this.reservationDao.findAll().stream().filter(
                            reservation ->
                                    (LocalDate.parse(reservation.getDateDebut(),formatter).isEqual(LocalDate.now())
                                            || LocalDate.parse(reservation.getDateDebut(),formatter).isAfter(LocalDate.now()))
                                            && (LocalDate.parse(reservation.getDateFin(),formatter).isEqual(LocalDate.now()))
                                            || LocalDate.parse(reservation.getDateFin(),formatter).isBefore(LocalDate.now()))
                    .collect(Collectors.toList());
        }
        this.instance.getReservationHashMap().clear();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chambre_menu);
        this.bAjout = findViewById(R.id.bAjout);
        this.bundle = this.getIntent().getExtras();
        if(this.bundle.getBoolean("maintenance")) {
            this.bAjout.setVisibility(View.GONE);
            ChambreMenu.maintenance2 = this.bundle.getBoolean("maintenance");
        }else {
            this.bAjout.setVisibility(View.VISIBLE);
            ChambreMenu.maintenance2 = this.bundle.getBoolean("maintenance");
        }
        this.maintenance = false;
        this.listView = findViewById(R.id.chambre2);
        this.bMaintenance = findViewById(R.id.maintenance);
        this.bMaintenance.setOnClickListener(this);
        this.arrayAdapter = new AdapterModifChambre(this,R.layout.liste_view_chambre,new ArrayList<>());

        this.instance = SingletonDb.getInstance(this);

        this.maBD = this.instance.getAppDatabase();
        this.chambreDao = this.maBD.chambreDao();
        this.reservationDao = this.maBD.reservationDao();
        this.maintenanceDao = this.maBD.maintenanceDao();

        findChambre(false);
        this.listView.setAdapter(this.arrayAdapter);
        this.bRetours = findViewById(R.id.bRetours2);
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