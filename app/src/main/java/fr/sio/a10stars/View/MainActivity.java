package fr.sio.a10stars.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import fr.sio.a10stars.View.Chambre.ChambreMenu;
import fr.sio.a10stars.View.Client.ClientMenu;
import fr.sio.a10stars.Db.DataAccess.Dao.Chambre.ChambreDao;
import fr.sio.a10stars.Db.DataAccess.Dao.Client.ClientDao;
import fr.sio.a10stars.Db.DataAccess.Dao.Reservation.ReservationDao;
import fr.sio.a10stars.Db.SingletonDb;
import fr.sio.a10stars.Db.Stars10DB;
import fr.sio.a10stars.View.Maintenance.MaintenanceMenu;
import fr.sio.a10stars.Modele.Chambre.Chambre;
import fr.sio.a10stars.Modele.Client.Client;
import fr.sio.a10stars.Modele.Reservation.Reservation;
import fr.sio.a10stars.R;
import fr.sio.a10stars.View.Reservation.Filtrage;
import fr.sio.a10stars.View.Reservation.ReservationMenu;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button bClient,bChambre,bEnd,bStart,bRecherche,bReserv,bMaintenance;

    private EditText eSimple,eDouble;

    private ListView listViewArive,listViewDepart;

    private ArrayAdapter<String> arrayAdapterArrive,arrayAdapterDepart;

    private ArrayAdapter<Chambre> arrayAdapter2;

    private ArrayAdapter<Reservation> arrayAdapter3;

    private SingletonDb instance;

    private ChambreDao chambreDao;

    private ReservationDao reservationDao;

    private ClientDao clientDao;

    private List<Chambre> list;

    private List<Reservation> list2;

    private List<Client> list3;

    private TextView tEnd,tStarte,tDateDebut,tDatedeFin;

    private Stars10DB stars10DB;

    @Override
    protected void onResume() {
        super.onResume();
        this.tDateDebut.setBackground(getResources().getDrawable(R.drawable.rounded_textview));
        this.tDatedeFin.setBackground(getResources().getDrawable(R.drawable.rounded_textview));
        findReservationArrive();
        findReservationDepart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.bChambre = findViewById(R.id.Chambre);
        this.bClient = findViewById(R.id.Client);
        this.bChambre.setOnClickListener(this);
        this.bClient.setOnClickListener(this);
        this.bReserv = findViewById(R.id.Reservation);
        this.bReserv.setOnClickListener(this);
        this.bMaintenance = findViewById(R.id.Maintenance);
        this.bMaintenance.setOnClickListener(this);
        this.listViewArive = findViewById(R.id.listeAriveeList);
        this.listViewDepart = findViewById(R.id.listeDepartList);
        this.arrayAdapterArrive = new ArrayAdapter<>(this,R.layout.listview_depart_arrive,R.id.listeDepartAriveeText);
        this.arrayAdapterDepart = new ArrayAdapter<>(this,R.layout.listview_depart_arrive,R.id.listeDepartAriveeText);
        this.listViewDepart.setAdapter(this.arrayAdapterDepart);
        this.tDateDebut = findViewById(R.id.dateDebutMain);
        this.tDatedeFin = findViewById(R.id.dateFinMain);
        this.tDateDebut.setBackground(getResources().getDrawable(R.drawable.rounded_textview));
        this.tDatedeFin.setBackground(getResources().getDrawable(R.drawable.rounded_textview));
        this.tDateDebut.setOnClickListener(this);
        this.tDatedeFin.setOnClickListener(this);
        this.bRecherche = findViewById(R.id.brecherche);
        this.bRecherche.setOnClickListener(this);
        this.eSimple = findViewById(R.id.filter_reserv_simple);
        this.eDouble = findViewById(R.id.filter_reserv_double);

        this.instance = SingletonDb.getInstance(this);
        this.stars10DB = this.instance.getAppDatabase();
        this.chambreDao = this.stars10DB.chambreDao();
        this.reservationDao = this.stars10DB.reservationDao();
        this.clientDao = this.stars10DB.clientDao();
        findReservationArrive();
        findReservationDepart();
    }

    public void findReservationArrive() {
        list2 = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Formater la date en utilisant le formateur
        String formattedDate = now.format(formatter);
        if(!this.instance.getReservationHashMap().isEmpty()) {
            this.list2.addAll(this.instance.getReservationHashMap().values().stream().filter(reservation -> reservation.getDateDebut().equals(formattedDate)).collect(Collectors.toList()));
        }else {
            this.list2 = this.reservationDao.findAll().stream().filter(reservation -> reservation.getDateDebut().equals(formattedDate)).collect(Collectors.toList());
        }
        findChambre();
        findClient();
        this.arrayAdapterArrive.clear();
        int i =0;
        String reservationInfo = "";
        for(Reservation reservation: this.list2) {
            reservationInfo = "Chambre concernée: " + this.list.get(i).getNum() + "\n";
            reservationInfo += "Client concernée: " + this.list3.get(i).getNom();
            i += 1;
            this.arrayAdapterArrive.add(reservationInfo);
        }
        this.listViewArive.setAdapter(this.arrayAdapterArrive);
    }

    public void findReservationDepart() {
        list2 = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Formater la date en utilisant le formateur
        String formattedDate = now.format(formatter);
        if(!this.instance.getReservationHashMap().isEmpty()) {
            this.list2.addAll(this.instance.getReservationHashMap().values().stream().filter(reservation -> reservation.getDateFin().equals(formattedDate)).collect(Collectors.toList()));
        }else {
            this.list2 = this.reservationDao.findAll().stream().filter(reservation -> reservation.getDateFin().equals(formattedDate)).collect(Collectors.toList());
        }
        findChambre();
        findClient();
        this.arrayAdapterDepart.clear();
        int i =0;
        String reservationInfo = "";
        for(Reservation reservation: this.list2) {
            reservationInfo = "Chambre concernée: " + this.list.get(i).getNum() + "\n";
            reservationInfo += "Client concernée: " + this.list3.get(i).getNom();
            i += 1;
            this.arrayAdapterDepart.add(reservationInfo);
        }
        this.listViewDepart.setAdapter(this.arrayAdapterDepart);
    }

    public void findClient() {
        this.list3 = new ArrayList<>();
        if(!this.instance.getClientHashMap().isEmpty()) {
            for(Reservation reservation: this.list2) {
                this.list3.addAll(this.instance.getClientHashMap().values().stream().filter(client -> client.getId() == reservation.getFkClient()).collect(Collectors.toList()));
            }
        }else {
            for(Reservation reservation: this.list2) {
                this.list3.addAll(this.clientDao.findAll().stream().filter(client -> client.getId() == reservation.getFkClient()).collect(Collectors.toList()));
            }
        }
    }

    public void findChambre() {
        list = new ArrayList<>();
        if (!this.instance.getChambreHashMap().isEmpty()) {
            for(Reservation reservation : list2) {
                list.addAll(this.instance.getChambreHashMap().values().stream().filter(chambre -> chambre.getId() == reservation.getFkChambre() && (chambre.getStatut().equals("disponible") || chambre.getStatut().equals("occupe"))).collect(Collectors.toList()));
            }
        }else {
            for(Reservation reservation: list2) {
                list.addAll(this.chambreDao.findChambreDispoOccupe().stream().filter(chambre -> chambre.getId() == reservation.getFkChambre()).collect(Collectors.toList()));
            }
        }
        for (Chambre chambre1 : this.list.stream().filter(chambre -> chambre.getStatut().equals("disponible")).collect(Collectors.toList())) {
            this.list.remove(chambre1);
            if(this.instance.getChambreHashMap().containsValue(chambre1)) {
                this.instance.removeFromChambreHashMap(chambre1.getId());
                chambre1.setStatut("occupe");
                this.instance.addToChambreHashMap(chambre1);
            }
            chambre1.setStatut("occupe");
            this.chambreDao.update(chambre1);
            this.list.add(chambre1);
        }
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.Chambre) {
            Intent intent = new Intent(this, ChambreMenu.class);
            intent.putExtra("maintenance",false);
            this.startActivity(intent);
        } else if (view.getId() == R.id.Client) {
            Intent intent = new Intent(this, ClientMenu.class);
            this.startActivity(intent);
        } else if (view.getId() == R.id.brecherche) {
            if(this.tDateDebut.getText().toString().isEmpty() || this.tDatedeFin.getText().toString().isEmpty()) {
                this.tDateDebut.setBackground(getResources().getDrawable(R.drawable.rounded_textview_rouge));
                this.tDatedeFin.setBackground(getResources().getDrawable(R.drawable.rounded_textview_rouge));
                Toast.makeText(this,"Veuillez renseignez la date de début et de fin de réservation",Toast.LENGTH_SHORT).show();
            }else {
                Intent intent = new Intent(this, Filtrage.class);

                intent.putExtra("litSimple",this.eSimple.getText().toString());
                intent.putExtra("litDouble",this.eDouble.getText().toString());
                intent.putExtra("datedebut",this.tDateDebut.getText().toString());
                intent.putExtra("datefin",this.tDatedeFin.getText().toString());
                this.startActivity(intent);
            }
        }else if (view.getId() == R.id.Reservation) {
            Intent intent = new Intent(this, ReservationMenu.class);
            this.startActivity(intent);
        } else if(view.getId() == R.id.dateDebutMain) {
            afficherDatePicker(this.tDateDebut);
        }else if (view.getId() == R.id.dateFinMain) {
            afficherDatePicker(this.tDatedeFin);
        } else if (view.getId() == R.id.Maintenance) {
            Intent intent = new Intent(this, MaintenanceMenu.class);
            this.startActivity(intent);
        }
    }

    private void afficherDatePicker(TextView textView) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Créer un DatePickerDialog avec la localisation française
        Locale locale = new Locale("fr");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String date = String.format(Locale.getDefault(), "%02d/%02d/%d", dayOfMonth, monthOfYear + 1, year1);
                    textView.setText(date);
                }, year, month, day);
        datePickerDialog.show();
    }

}