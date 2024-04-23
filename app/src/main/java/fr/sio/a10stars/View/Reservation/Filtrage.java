package fr.sio.a10stars.View.Reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fr.sio.a10stars.Db.DataAccess.Dao.Chambre.ChambreDao;
import fr.sio.a10stars.Db.DataAccess.Dao.Maintenance.MaintenanceDao;
import fr.sio.a10stars.Db.DataAccess.Dao.Reservation.ReservationDao;
import fr.sio.a10stars.Db.SingletonDb;
import fr.sio.a10stars.Modele.Chambre.Chambre;
import fr.sio.a10stars.Modele.Maintenance.Maintenance;
import fr.sio.a10stars.Modele.Reservation.Reservation;
import fr.sio.a10stars.R;
import fr.sio.a10stars.Db.Stars10DB;
import fr.sio.a10stars.Controller.Reservation.AdapterSelectionChambre;

public class Filtrage extends AppCompatActivity implements View.OnClickListener {

    private Button bRetours;

    private Intent intent;

    private int id;

    private int maxP;


    private String statut;


    private int etage;

    private int simple;

    private int double2;

    private String dateDebut,dateFin;

    private String num,nbLitSimple,nbLitDouble;

    private String comm;

    private ListView listView;

    private Bundle bundle;

    private ArrayAdapter<Chambre> arrayAdapter;

    public List<Chambre> list;

    public List<Reservation> list2;

    public List<Maintenance> list3;

    private Stars10DB maBD;

    private SQLiteDatabase writeBD;

    private Chambre chambre;

    private TextView textView;

    private ChambreDao chambreDao;

    private ReservationDao reservationDao;

    private MaintenanceDao maintenanceDao;

    private SingletonDb instance;

    @Override
    protected void onResume() {
        super.onResume();
        this.bundle = this.getIntent().getExtras();
        this.nbLitSimple = this.bundle.getString("litSimple");
        this.nbLitDouble =  this.bundle.getString("litDouble");
        this.dateDebut = this.bundle.getString("datedebut");
        this.dateFin = this.bundle.getString("datefin");
        findChambre();
    }


    public void findChambre() {
        list = new ArrayList<>();
        if(!this.instance.getChambreHashMap().isEmpty()) {
                if (!this.nbLitSimple.isEmpty() && !this.nbLitDouble.isEmpty()) {
                    this.list.addAll(this.instance.getChambreHashMap().values().stream()
                            .filter(chambre -> chambre.getNb_lit_simple() == Integer.parseInt(this.nbLitSimple) && chambre.getNb_lit_double() == Integer.parseInt(this.nbLitDouble))
                            .collect(Collectors.toList()));
                } else if (!this.nbLitSimple.isEmpty() && this.nbLitDouble.isEmpty()) {
                    this.list.addAll(this.instance.getChambreHashMap().values().stream()
                            .filter(chambre -> chambre.getNb_lit_simple() == Integer.parseInt(this.nbLitSimple))
                            .collect(Collectors.toList()));
                } else if (this.nbLitSimple.isEmpty() && !this.nbLitDouble.isEmpty()) {
                    this.list.addAll(this.instance.getChambreHashMap().values().stream()
                            .filter(chambre -> chambre.getNb_lit_double() == Integer.parseInt(this.nbLitDouble))
                            .collect(Collectors.toList()));
                }else {
                    this.list.addAll(this.instance.getChambreHashMap().values());
                }
        }else {
            if (!this.nbLitSimple.isEmpty() && !this.nbLitDouble.isEmpty()) {
                this.list = this.chambreDao.findChambresBySimpleDouble(this.nbLitSimple, this.nbLitDouble);
            } else if (!this.nbLitSimple.isEmpty() && this.nbLitDouble.isEmpty()) {
                this.list = this.chambreDao.findChambresBySimple(this.nbLitSimple);
            } else if (this.nbLitSimple.isEmpty() && !this.nbLitDouble.isEmpty()) {
                this.list = this.chambreDao.findChambresByDouble(this.nbLitDouble);
            }else {
                this.list = this.chambreDao.findAll();
            }
        }
        findReservation();
        findMaintenance();
        List<Chambre> tempList = new ArrayList<>(); // Créer une liste temporaire pour stocker les chambres qui valident la condition

        for (Chambre chambre : this.list) {
            boolean conditionValide = true; // Flag pour suivre si la condition est validée
            for (Reservation reservation : this.list2) {
                if (reservation.getFkChambre() == chambre.getId()) {
                    conditionValide = false; // La chambre est associée à une réservation, donc la condition est validée
                    break; // Sortir de la boucle de réservations dès qu'une correspondance est trouvée
                }
            }
            for(Maintenance maintenance: this.list3) {
                if(maintenance.getFkChambre() == chambre.getId()) {
                    conditionValide = false;
                    break;
                }
            }
            // Si la chambre ne correspond pas à une réservation et valide la condition
            if (!conditionValide) {
                tempList.add(chambre); // Ajouter la chambre à la liste temporaire
            }
        }

        this.arrayAdapter.clear();
        this.instance.getChambreHashMap().clear();
        for (Chambre chambre1 : this.list) {
            this.instance.addToChambreHashMap(chambre1);
        }
        if (list.size() <= 0) {
            this.textView.setVisibility(View.VISIBLE);
        }
        for(Chambre chambre1: tempList) {
            this.list.remove(chambre1);
        }
        this.arrayAdapter.clear();
        for (Chambre chambre1 : this.list) {
            this.arrayAdapter.add(chambre1);
        }
        if (list.size() <= 0) {
            this.textView.setVisibility(View.VISIBLE);
        }
    }

    public void findReservation() {
        list2 = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if(!this.instance.getReservationHashMap().isEmpty()) {
                this.list2.addAll(this.instance.getReservationHashMap().values().stream().filter(
                                reservation -> (LocalDate.parse(reservation.getDateDebut(),formatter).isBefore(LocalDate.parse(this.dateFin,formatter) ) ||
                                        LocalDate.parse(reservation.getDateDebut(),formatter).isEqual(LocalDate.parse(this.dateFin,formatter)))
                                        && (LocalDate.parse(reservation.getDateFin(),formatter).isAfter(LocalDate.parse(this.dateDebut,formatter)) ||
                                                LocalDate.parse(reservation.getDateFin(),formatter).isEqual(LocalDate.parse(this.dateDebut,formatter))))
                        .collect(Collectors.toList()));
        }else {
                this.list2.addAll(this.reservationDao.findAll().stream().filter(
                                reservation -> (LocalDate.parse(reservation.getDateDebut(),formatter).isBefore(LocalDate.parse(this.dateFin,formatter) ) ||
                                        LocalDate.parse(reservation.getDateDebut(),formatter).isEqual(LocalDate.parse(this.dateFin,formatter)))
                                                && (LocalDate.parse(reservation.getDateFin(),formatter).isAfter(LocalDate.parse(this.dateDebut,formatter)) ||
                                                LocalDate.parse(reservation.getDateFin(),formatter).isEqual(LocalDate.parse(this.dateDebut,formatter))))
                        .collect(Collectors.toList()));
        }
    }

    public void findMaintenance() {
        list3 = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if(!this.instance.getMaintenanceHashMap().isEmpty()) {
            this.list3.addAll(this.instance.getMaintenanceHashMap().values().stream().filter(
                            maintenance -> (LocalDate.parse(maintenance.getDateDebut(),formatter).isBefore(LocalDate.parse(this.dateFin,formatter) ) ||
                                    LocalDate.parse(maintenance.getDateDebut(),formatter).isEqual(LocalDate.parse(this.dateFin,formatter)))
                                    && (LocalDate.parse(maintenance.getDateFin(),formatter).isAfter(LocalDate.parse(this.dateDebut,formatter)) ||
                                    LocalDate.parse(maintenance.getDateFin(),formatter).isEqual(LocalDate.parse(this.dateDebut,formatter))))
                    .collect(Collectors.toList()));
        }else {
            this.list3.addAll(this.maintenanceDao.findAll().stream().filter(
                            maintenance -> (LocalDate.parse(maintenance.getDateDebut(),formatter).isBefore(LocalDate.parse(this.dateFin,formatter) ) ||
                                    LocalDate.parse(maintenance.getDateDebut(),formatter).isEqual(LocalDate.parse(this.dateFin,formatter)))
                                    && (LocalDate.parse(maintenance.getDateFin(),formatter).isAfter(LocalDate.parse(this.dateDebut,formatter)) ||
                                    LocalDate.parse(maintenance.getDateFin(),formatter).isEqual(LocalDate.parse(this.dateDebut,formatter))))
                    .collect(Collectors.toList()));
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtrage);
        this.listView = findViewById(R.id.chambreFiltrer);
        this.textView = findViewById(R.id.introuvable);
        this.arrayAdapter = new AdapterSelectionChambre(this,R.layout.chambre_filtrer,new ArrayList<>());

        this.instance = SingletonDb.getInstance(this);
        this.maBD = this.instance.getAppDatabase();
        this.chambreDao = this.maBD.chambreDao();
        this.reservationDao = this.maBD.reservationDao();
        this.maintenanceDao = this.maBD.maintenanceDao();

        this.bundle = this.getIntent().getExtras();
        this.nbLitSimple = this.bundle.getString("litSimple");
        this.nbLitDouble =  this.bundle.getString("litDouble");
        this.dateDebut = this.bundle.getString("datedebut");
        this.dateFin = this.bundle.getString("datefin");
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
