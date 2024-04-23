package fr.sio.a10stars.View.Maintenance;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import fr.sio.a10stars.View.Chambre.ChambreForm;
import fr.sio.a10stars.Db.DataAccess.Dao.Chambre.ChambreDao;
import fr.sio.a10stars.Db.DataAccess.Dao.Maintenance.MaintenanceDao;
import fr.sio.a10stars.Db.DataAccess.Dao.Reservation.ReservationDao;
import fr.sio.a10stars.Db.SingletonDb;
import fr.sio.a10stars.Db.Stars10DB;
import fr.sio.a10stars.Modele.Chambre.Chambre;
import fr.sio.a10stars.Modele.Maintenance.Maintenance;
import fr.sio.a10stars.Modele.Reservation.Reservation;
import fr.sio.a10stars.R;

public class MaintenanceForm extends AppCompatActivity implements View.OnClickListener, TextWatcher, AdapterView.OnItemClickListener {

    private SingletonDb instance;

    private Stars10DB stars10DB;

    private ChambreDao chambreDao;

    private MaintenanceDao maintenanceDao;

    private ReservationDao reservationDao;

    private Button bRetours,bSuppr,bAjout,bCreaChambre;

    private TextView tDateDebut,tDateFin;

    private AutoCompleteTextView atChambre;

    private EditText commentaire;

    private ArrayAdapter<String> arrayAdapter;

    private ArrayList<String> numChambre;

    private Boolean ajouter;

    private Bundle bundle;

    private int idChambre,idMaintenance;


    private String input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_form);

        this.bRetours = findViewById(R.id.bRetoursMaintenanceForm);
        this.bSuppr = findViewById(R.id.bSupprMaintenance);
        this.bAjout = findViewById(R.id.bAjoutMaintenance);
        this.bCreaChambre = findViewById(R.id.bCreaChambreMaintenance);
        this.tDateDebut = findViewById(R.id.dateDebutMaintenanceForm);
        this.tDateFin = findViewById(R.id.dateFinMaintenanceForm);
        this.atChambre = findViewById(R.id.chambre_autocomplete_maintenance);
        this.commentaire = findViewById(R.id.commentaire_maintenance);

        this.bRetours.setOnClickListener(this);
        this.bSuppr.setOnClickListener(this);
        this.bAjout.setOnClickListener(this);
        this.bCreaChambre.setOnClickListener(this);
        this.tDateDebut.setOnClickListener(this);
        this.tDateFin.setOnClickListener(this);

        this.bundle = this.getIntent().getExtras();
        this.ajouter = this.bundle.getBoolean("ajouter");
        this.idChambre = this.bundle.getInt("chambreId");

        this.instance = SingletonDb.getInstance(this);
        this.stars10DB = this.instance.getAppDatabase();
        this.chambreDao = this.stars10DB.chambreDao();
        this.maintenanceDao = this.stars10DB.maintenanceDao();
        this.reservationDao = this.stars10DB.reservationDao();

        this.tDateFin.setBackground(getDrawable(R.drawable.rounded_textview));
        this.tDateDebut.setBackground(getDrawable(R.drawable.rounded_textview));
        this.atChambre.setBackgroundColor(getColor(R.color.white));

        findChambre();
        this.atChambre.setText(this.instance.getChambreHashMap().get(this.idChambre).getNum());
        this.arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,this.numChambre);
        this.atChambre.setAdapter(this.arrayAdapter);
        this.atChambre.setThreshold(1);
        this.atChambre.addTextChangedListener(this);
        this.atChambre.setOnItemClickListener(this);

        if(!ajouter) {
            this.bAjout.setText("Appliquer");
            this.bSuppr.setOnClickListener(this);
            this.idMaintenance = this.bundle.getInt("idMaintenance");
            this.tDateDebut.setText(this.instance.getMaintenanceHashMap().get(this.idMaintenance).getDateDebut());
            this.tDateFin.setText(this.instance.getMaintenanceHashMap().get(this.idMaintenance).getDateFin());
            this.atChambre.setText(this.instance.getChambreHashMap().get(this.instance.getMaintenanceHashMap().get(this.idMaintenance).getFkChambre()).getNum());
            this.commentaire.setText(this.instance.getMaintenanceHashMap().get(this.idMaintenance).getCommentaire());
        }else {
            this.bAjout.setText("Ajouter");
            this.bSuppr.setVisibility(View.GONE);
        }
    }

    public void findChambre() {
        this.numChambre = new ArrayList<>();
        if(this.instance.getChambreHashMap().isEmpty()) {
            this.instance.getChambreHashMap().clear();
            for(Chambre chambre1 : this.chambreDao.findAll()) {
                this.instance.addToChambreHashMap(chambre1);
            }
        }
        for(Chambre chambre1: this.instance.getChambreHashMap().values()) {
            this.numChambre.add(chambre1.getNum());
        }
        if(this.numChambre.isEmpty()) {
            this.numChambre.add("pas de chambre disponible");
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bRetoursMaintenanceForm) {
            this.finish();
        } else if (v.getId() == R.id.dateDebutMaintenanceForm) {
        afficherDatePicker(this.tDateDebut);
    } else if (v.getId() == R.id.dateFinMaintenanceForm) {
        afficherDatePicker(this.tDateFin);
    }else if(this.tDateFin.getText().toString().isEmpty() ||this.tDateDebut.getText().toString().isEmpty() || this.atChambre.getText().toString().isEmpty()) {
            this.tDateFin.setBackground(getDrawable(R.drawable.rounded_textview_rouge));
            this.tDateDebut.setBackground(getDrawable(R.drawable.rounded_textview_rouge));
            this.atChambre.setBackgroundColor(getColor(R.color.maintenance));
            Toast.makeText(this,"veuillez remplir tous les champs obligatoire",Toast.LENGTH_SHORT).show();
        }
        else if((dateAlreadyBooked(this.tDateDebut.getText().toString(), this.tDateFin.getText().toString(),this.idChambre)) && this.ajouter) {
            // Vérification si les dates sont déjà réservées ou en maintenance
            Toast.makeText(this, "Les dates sélectionnées sont déjà réservées ou en maintenance", Toast.LENGTH_SHORT).show();
        }else {
        if (v.getId() == R.id.bAjoutMaintenance) {
                if (ajouter) {
                    Maintenance maintenance = new Maintenance(this.idChambre, this.tDateDebut.getText().toString(), this.tDateFin.getText().toString(), this.commentaire.getText().toString());
                    this.maintenanceDao.insert(maintenance);
                    this.instance.getMaintenanceHashMap().clear();
                    for (Maintenance maintenance1 : this.maintenanceDao.findAll()) {
                        this.instance.addToMaintenanceHashMap(maintenance1);
                    }
                    Toast.makeText(this, "Maintenance crée avec succée", Toast.LENGTH_SHORT).show();
                    this.finish();
                } else {
                    Maintenance maintenance = new Maintenance(this.idMaintenance, this.idChambre, this.tDateDebut.getText().toString(), this.tDateFin.getText().toString(), this.commentaire.getText().toString());
                    this.maintenanceDao.update(maintenance);
                    this.instance.removeFromMaintenanceHashMap(this.idMaintenance);
                    this.instance.addToMaintenanceHashMap(maintenance);
                    Toast.makeText(this, "Maintenance modifié avec succée", Toast.LENGTH_SHORT).show();
                    this.finish();
                }
            } else if (v.getId() == R.id.bSupprMaintenance) {
                this.maintenanceDao.delete(this.instance.getMaintenanceHashMap().get(this.idMaintenance));
                this.instance.removeFromMaintenanceHashMap(this.idMaintenance);
                Toast.makeText(this, "Maintenance supprimé avec succée", Toast.LENGTH_SHORT).show();
                this.finish();
            } else if (v.getId() == R.id.bCreaChambreMaintenance) {
                Intent intent = new Intent(this, ChambreForm.class);
                intent.putExtra("nomChambre", this.input);
                intent.putExtra("ajouter", true);
                this.startActivity(intent);
            }
        }

    }

    private boolean dateAlreadyBooked(String dateDebut, String dateFin, int idChambre) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if (instance.getReservationHashMap().isEmpty() && instance.getMaintenanceHashMap().isEmpty()) {
            // Les hashmaps sont vides, vérifier directement dans la base de données
            List<Reservation> reservations = reservationDao.findReservationsByIdChambre(idChambre);
            List<Maintenance> maintenances = maintenanceDao.findMaintenancesByIdChambre(idChambre);
            for(Reservation reservation1: reservations) {
                if((LocalDate.parse(reservation1.getDateDebut(),formatter).isEqual(LocalDate.parse(dateFin,formatter)) || LocalDate.parse(reservation1.getDateDebut(),formatter).isBefore(LocalDate.parse(dateFin,formatter))) && (LocalDate.parse(reservation1.getDateFin(),formatter).isEqual(LocalDate.parse(dateDebut,formatter))) || LocalDate.parse(reservation1.getDateFin(),formatter).isAfter(LocalDate.parse(dateDebut,formatter))) {
                    return true;
                }
            }
            for(Maintenance maintenance1: maintenances) {
                if((LocalDate.parse(maintenance1.getDateDebut(),formatter).isEqual(LocalDate.parse(dateFin,formatter)) || LocalDate.parse(maintenance1.getDateDebut(),formatter).isBefore(LocalDate.parse(dateFin,formatter))) && (LocalDate.parse(maintenance1.getDateFin(),formatter).isEqual(LocalDate.parse(dateDebut,formatter))) || LocalDate.parse(maintenance1.getDateFin(),formatter).isAfter(LocalDate.parse(dateDebut,formatter))) {
                    return true;
                }
            }
            // Si une réservation ou une maintenance est trouvée pour les dates spécifiées, renvoyer true
            return false;
        } else {
            // Les hashmaps contiennent des données, vérifier dans les hashmaps
            for (Reservation reservation : instance.getReservationHashMap().values().stream().filter(reservation1 -> reservation1.getFkChambre() == this.idChambre).collect(Collectors.toList())) {
                if ((LocalDate.parse(reservation.getDateDebut(),formatter).isEqual(LocalDate.parse(dateFin,formatter)) || LocalDate.parse(reservation.getDateDebut(),formatter).isBefore(LocalDate.parse(dateFin,formatter))) && (LocalDate.parse(reservation.getDateFin(),formatter).isEqual(LocalDate.parse(dateDebut,formatter))) || LocalDate.parse(reservation.getDateFin(),formatter).isAfter(LocalDate.parse(dateDebut,formatter))) {
                    return true; // Réservation déjà existante pour les dates spécifiées pour cette chambre
                }
            }

            for (Maintenance maintenance : instance.getMaintenanceHashMap().values().stream().filter(maintenance1 -> maintenance1.getFkChambre() == this.idChambre).collect(Collectors.toList())) {
                if ((LocalDate.parse(maintenance.getDateDebut(),formatter).isEqual(LocalDate.parse(dateFin,formatter)) || LocalDate.parse(maintenance.getDateDebut(),formatter).isBefore(LocalDate.parse(dateFin,formatter))) && (LocalDate.parse(maintenance.getDateFin(),formatter).isEqual(LocalDate.parse(dateDebut,formatter))) || LocalDate.parse(maintenance.getDateFin(),formatter).isAfter(LocalDate.parse(dateDebut,formatter))) {
                    return true; // Maintenance déjà existante pour les dates spécifiées pour cette chambre
                }
            }
            return false; // Aucune réservation ou maintenance pour les dates spécifiées pour cette chambre
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        this.input = s.toString();
        if (!this.numChambre.contains(input)) {
            this.bCreaChambre.setVisibility(View.VISIBLE);
            this.bCreaChambre.setOnClickListener(this);
        } else {
            this.bCreaChambre.setVisibility(View.GONE);
            this.idChambre = this.instance.getChambreHashMap().values().stream().filter(chambre1 -> chambre1.getNum().equals(this.input)).collect(Collectors.toList()).get(0).getId();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String numChambre = parent.getItemAtPosition(position).toString();
        if(numChambre != null && !numChambre.equals("pas de chambre disponible")) {
            this.idChambre = this.instance.getChambreHashMap().values().stream().filter(chambre1 -> chambre1.getNum().equals(numChambre)).collect(Collectors.toList()).get(0).getId();
        }
        this.bCreaChambre.setVisibility(View.GONE);
    }
}