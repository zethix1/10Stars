package fr.sio.a10stars.View.Reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import fr.sio.a10stars.View.Client.ClientForm;
import fr.sio.a10stars.Db.DataAccess.Dao.Chambre.ChambreDao;
import fr.sio.a10stars.Db.DataAccess.Dao.Client.ClientDao;
import fr.sio.a10stars.Db.DataAccess.Dao.Historique.HistoriqueDao;
import fr.sio.a10stars.Db.DataAccess.Dao.Maintenance.MaintenanceDao;
import fr.sio.a10stars.Db.DataAccess.Dao.Reservation.ReservationDao;
import fr.sio.a10stars.Db.SingletonDb;
import fr.sio.a10stars.Modele.Chambre.Chambre;
import fr.sio.a10stars.Db.Stars10DB;
import fr.sio.a10stars.Modele.Client.Client;
import fr.sio.a10stars.Modele.Historique.Historique;
import fr.sio.a10stars.Modele.Maintenance.Maintenance;
import fr.sio.a10stars.Modele.Reservation.Reservation;
import fr.sio.a10stars.R;
import fr.sio.a10stars.Controller.Reservation.MaxLengthFilter;

public class ReservationForm extends AppCompatActivity implements View.OnClickListener, TextWatcher, AdapterView.OnItemClickListener {

    private Button bRetours,bAjout,bSuppr,bCrea;

    private AutoCompleteTextView sClient;

    private TextView tNbLitSimple,tNbLitDouble,tNumChambre,tMaxP,tDateDebut,tDatedeFin;

    private EditText eNbAdulte,eNbEnfant,ecomm,eNbInvite;

    private Chambre chambre;

    private Stars10DB maBD;


    private ArrayAdapter<String> client;

    private ArrayList<String> clientA;

    private int etage,idChambre,maxP,idClient,idClientMap,idReserv;

    private Map<String,Integer> clientMap = new HashMap<>();

    private int simple;

    private int double2;

    private String num,statut;

    private String comm,nomC,prenomC;

    private SingletonDb instance;

    private Reservation reservation;

    private ChambreDao chambreDao;

    private ClientDao clientDao;

    private ReservationDao reservationDao;

    private HistoriqueDao historiqueDao;
    private Bundle bundle;

    private Boolean ajouter;

    private String input;

    private MaintenanceDao maintenanceDao;


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
        this.tMaxP = findViewById(R.id.tnbMaxPersonneChambreReserv);
        this.bCrea = findViewById(R.id.bCreaClientReserv);
        this.bCrea.setOnClickListener(this);
        int maxLength = 9;
        this.eNbAdulte.setFilters(new InputFilter[] {new MaxLengthFilter(maxLength)});
        this.eNbEnfant.setFilters(new InputFilter[]{new MaxLengthFilter(maxLength)});
        this.bAjout = findViewById(R.id.bAjoutReserv);
        this.bAjout.setOnClickListener(this);
        this.sClient = findViewById(R.id.client_autocomplete_reserv);
        this.bundle = this.getIntent().getExtras();
        this.idChambre = this.bundle.getInt("idChambre");
        this.ajouter = this.bundle.getBoolean("ajouter");
        this.bSuppr = findViewById(R.id.bSupprReserv);

        this.idClientMap = 0;
        this.eNbInvite = findViewById(R.id.nbInvite);
        this.tDateDebut = findViewById(R.id.dateDebutReservForm);
        this.tDatedeFin = findViewById(R.id.dateFinReservForm);
        this.tDateDebut.setOnClickListener(this);
        this.tDatedeFin.setOnClickListener(this);
        this.ecomm = findViewById(R.id.commentaire_reservation);


        this.eNbInvite.setBackgroundColor(getColor(R.color.white));
        this.eNbEnfant.setBackgroundColor(getColor(R.color.white));
        this.eNbAdulte.setBackgroundColor(getColor(R.color.white));
        this.tDatedeFin.setBackground(getDrawable(R.drawable.rounded_textview));
        this.tDateDebut.setBackground(getDrawable(R.drawable.rounded_textview));
        this.sClient.setBackgroundColor(getColor(R.color.white));

        this.instance = SingletonDb.getInstance(this);
        this.maBD = this.instance.getAppDatabase();
        this.chambreDao = this.maBD.chambreDao();
        this.clientDao = this.maBD.clientDao();
        this.reservationDao = this.maBD.reservationDao();
        this.historiqueDao = this.maBD.historiqueDao();
        this.maintenanceDao = this.maBD.maintenanceDao();
        findClient();
        this.client = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,this.clientA);
        this.sClient.setAdapter(this.client);
        this.sClient.setThreshold(1);
        this.sClient.addTextChangedListener(this);
        this.sClient.setOnItemClickListener(this);

        findChambre();
        if(!ajouter) {
            this.bSuppr.setOnClickListener(this);
            this.idReserv = this.bundle.getInt("idReservation");
            this.tNumChambre.setText(this.instance.getChambreHashMap().get(this.idChambre).getNum());
            this.tNbLitSimple.setText(String.valueOf(this.instance.getChambreHashMap().get(this.idChambre).getNb_lit_simple()));
            this.tNbLitDouble.setText(String.valueOf(this.instance.getChambreHashMap().get(this.idChambre).getNb_lit_double()));
            this.eNbAdulte.setText(String.valueOf(this.instance.getReservationHashMap().get(this.idReserv).getNombreAdulte()));
            this.eNbEnfant.setText(String.valueOf(this.instance.getReservationHashMap().get(this.idReserv).getNombreEnfant()));
            this.tMaxP.setText(String.valueOf(this.instance.getChambreHashMap().get(this.idChambre).getMaxP()));
            this.tDateDebut.setText(this.instance.getReservationHashMap().get(this.idReserv).getDateDebut());
            this.tDatedeFin.setText(this.instance.getReservationHashMap().get(this.idReserv).getDateFin());
            this.sClient.setText(this.instance.getClientHashMap().get(this.instance.getReservationHashMap().get(this.idReserv).getFkClient()).getNom() + " " + this.instance.getClientHashMap().get(this.instance.getReservationHashMap().get(this.idReserv).getFkClient()).getPrenom());
            this.eNbInvite.setText(String.valueOf(this.instance.getReservationHashMap().get(this.idReserv).getNbInvite()));
            this.ecomm.setText(this.instance.getReservationHashMap().get(this.idReserv).getComm());
            this.bAjout.setText("Appliquer");
        }else {
            this.bAjout.setText("Ajouter");
            this.bSuppr.setVisibility(View.GONE);
        }

    }

    public void findClient() {
        this.clientA = new ArrayList<>();
        if(!this.instance.getClientHashMap().isEmpty()) {
            for (Map.Entry<Integer, Client> entry : this.instance.getClientHashMap().entrySet()) {
                this.clientA.add(entry.getValue().getNom() + " " + entry.getValue().getPrenom());
            }
        }else {
            if(!this.clientDao.findAll().isEmpty()) {
                this.instance.getClientHashMap().clear();
                for(Client client1 : this.clientDao.findAll()) {
                    this.clientA.add(client1.getNom() + " " + client1.getPrenom());
                    this.instance.addToClientHashMap(client1);
                }
            }else {
                if (this.clientA.isEmpty()) {
                    this.clientA.add("pas de client disponible");
                    this.instance.getClientHashMap().clear();
                }
            }
        }
    }

    public void findChambre() {
        if(this.instance.getChambreHashMap().isEmpty()) {
            this.instance.getChambreHashMap().clear();
            for(Chambre chambre1 : this.chambreDao.findAll()) {
                this.instance.addToChambreHashMap(chambre1);
            }
        }
        this.chambre = this.instance.getChambreHashMap().get(this.idChambre);
        this.tNumChambre.setText(this.chambre.getNum());
        this.tNbLitDouble.setText(Integer.toString(this.chambre.getNb_lit_double()));
        this.tNbLitSimple.setText(Integer.toString(this.chambre.getNb_lit_simple()));
        this.tMaxP.setText(Integer.toString(this.chambre.getMaxP()));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bRetoursReservation) {
            this.finish();
        } else if (view.getId() == R.id.dateDebutReservForm) {
         afficherDatePicker(this.tDateDebut);
        } else if (view.getId() == R.id.dateFinReservForm) {
            afficherDatePicker(this.tDatedeFin);

        } else if(this.eNbInvite.getText().toString().isEmpty() || this.eNbEnfant.getText().toString().isEmpty() || this.eNbAdulte.getText().toString().isEmpty() || this.tDateDebut.getText().toString().isEmpty() ||this.tDatedeFin.getText().toString().isEmpty() || this.sClient.getText().toString().isEmpty()) {
            this.eNbInvite.setBackgroundColor(getColor(R.color.maintenance));
            this.eNbEnfant.setBackgroundColor(getColor(R.color.maintenance));
            this.eNbAdulte.setBackgroundColor(getColor(R.color.maintenance));
            this.tDatedeFin.setBackground(getDrawable(R.drawable.rounded_textview_rouge));
            this.tDateDebut.setBackground(getDrawable(R.drawable.rounded_textview_rouge));
            this.sClient.setBackgroundColor(getColor(R.color.maintenance));
            Toast.makeText(this,"veuillez remplir tous les champs obligatoire",Toast.LENGTH_SHORT).show();
        }
        else if((dateAlreadyBooked(this.tDateDebut.getText().toString(), this.tDatedeFin.getText().toString(),this.idChambre)) && this.ajouter) {
            // Vérification si les dates sont déjà réservées ou en maintenance
            Toast.makeText(this,"Les dates sélectionnées sont déjà réservées ou en maintenance",Toast.LENGTH_SHORT).show();
        }else {
            if (view.getId() == R.id.bAjoutReserv) {
                if (this.bAjout.getText().toString().equals("Ajouter")) {
                    this.reservation = new Reservation(Integer.parseInt(this.eNbInvite.getText().toString()), this.idChambre, this.idClientMap, this.tDateDebut.getText().toString(), this.tDatedeFin.getText().toString(), this.ecomm.getText().toString(), Integer.parseInt(this.eNbAdulte.getText().toString()), Integer.parseInt(this.eNbEnfant.getText().toString()));
                    this.reservationDao.insert(this.reservation);
                    this.instance.getReservationHashMap().clear();
                    for (Reservation reservation1 : this.reservationDao.findAll()) {
                        this.instance.addToReservationHashMap(reservation1);
                    }
                    Historique historique = new Historique(
                            this.idClientMap,
                            this.reservationDao.findRservationId(Integer.parseInt(this.eNbInvite.getText().toString()), this.idChambre, this.idClientMap, this.ecomm.getText().toString(), this.tDateDebut.getText().toString(), this.tDatedeFin.getText().toString(), Integer.parseInt(this.eNbAdulte.getText().toString()), Integer.parseInt(this.eNbEnfant.getText().toString())).get(0).getId(),
                            this.idChambre,
                            this.tDateDebut.getText().toString(),
                            this.tDatedeFin.getText().toString()
                    );
                    this.historiqueDao.insert(historique);
                    this.instance.getHistoriqueHashMap().clear();
                    for (Historique historique1 : this.historiqueDao.findAll()) {
                        this.instance.addToHistoriqueHashMap(historique1);
                        System.out.println(historique1.getId());
                    }
                    Toast.makeText(this, "Reservation crée avec succée", Toast.LENGTH_SHORT).show();
                    this.finish();
                } else {
                    if (this.idClientMap == 0) {
                        this.idClientMap = this.instance.getReservationHashMap().get(this.idReserv).getFkClient();
                    }
                    this.reservation = new Reservation(this.idReserv, Integer.parseInt(this.eNbInvite.getText().toString()), this.idChambre, this.idClientMap, this.tDateDebut.getText().toString(), this.tDatedeFin.getText().toString(), this.ecomm.getText().toString(), Integer.parseInt(this.eNbAdulte.getText().toString()), Integer.parseInt(this.eNbEnfant.getText().toString()));
                    this.reservationDao.update(this.reservation);
                    this.instance.removeFromReservationHashMap(this.reservation.getId());
                    this.instance.addToReservationHashMap(this.reservation);
                    Historique historique = new Historique(this.historiqueDao.findAll().stream().filter(historique1 -> historique1.getFkReservation() == this.idReserv).collect(Collectors.toList()).get(0).getId(), this.idClientMap, this.idReserv, this.idChambre, this.tDateDebut.getText().toString(), this.tDatedeFin.getText().toString());
                    this.historiqueDao.update(historique);
                    this.instance.removeFromHistoriqueHashMap(historique.getId());
                    this.instance.addToHistoriqueHashMap(historique);
                    Toast.makeText(this, "Reservation modifié avec succée", Toast.LENGTH_SHORT).show();
                    this.finish();
                }
            } else if (view.getId() == R.id.bSupprReserv) {
                if (this.instance.getHistoriqueHashMap().get(this.instance.getHistoriqueHashMap().get(this.historiqueDao.findAll().stream().filter(historique1 -> historique1.getFkReservation() == this.idReserv).collect(Collectors.toList()))) != null) {
                    this.instance.removeFromHistoriqueHashMap(this.historiqueDao.findAll().stream().filter(historique1 -> historique1.getFkReservation() == this.idReserv).collect(Collectors.toList()).get(0).getId());
                }
                this.reservationDao.delete(this.instance.getReservationHashMap().get(this.idReserv));
                this.instance.removeFromReservationHashMap(this.idReserv);
                Toast.makeText(this, "Reservation supprimé avec succée", Toast.LENGTH_SHORT).show();
                this.finish();
            } else if (view.getId() == R.id.bCreaClientReserv) {
                Intent intent = new Intent(this, ClientForm.class);
                intent.putExtra("nomClient", this.input);
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
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        System.out.println(charSequence);
        this.input = charSequence.toString();
        if (!clientA.contains(input)) {
            bCrea.setVisibility(View.VISIBLE);
            this.bCrea.setOnClickListener(this);
        } else {
            bCrea.setVisibility(View.GONE);
            this.idClientMap = this.instance.getClientHashMap().values().stream().filter(client1 -> (client1.getNom() + " " + client1.getPrenom()).equals(input)).collect(Collectors.toList()).get(0).getId();
        }
    }


    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String nomClient = parent.getItemAtPosition(position).toString();
        if(nomClient != null && !nomClient.equals("pas de client disponible")) {
            this.idClientMap = this.instance.getClientHashMap().values().stream().filter(client -> (client.getNom() + " " + client.getPrenom()).equals(nomClient)).collect(Collectors.toList()).get(0).getId();
        }
        this.bCrea.setVisibility(View.GONE);
    }
}