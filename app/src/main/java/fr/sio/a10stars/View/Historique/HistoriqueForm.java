package fr.sio.a10stars.View.Historique;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import fr.sio.a10stars.Db.DataAccess.Dao.Chambre.ChambreDao;
import fr.sio.a10stars.Db.DataAccess.Dao.Client.ClientDao;
import fr.sio.a10stars.Db.DataAccess.Dao.Historique.HistoriqueDao;
import fr.sio.a10stars.Db.DataAccess.Dao.Reservation.ReservationDao;
import fr.sio.a10stars.Db.SingletonDb;
import fr.sio.a10stars.Db.Stars10DB;
import fr.sio.a10stars.Modele.Chambre.Chambre;
import fr.sio.a10stars.Modele.Client.Client;
import fr.sio.a10stars.Modele.Historique.Historique;
import fr.sio.a10stars.Modele.Reservation.Reservation;
import fr.sio.a10stars.R;

public class HistoriqueForm extends AppCompatActivity implements View.OnClickListener {

    private SingletonDb instance;

    private Stars10DB stars10DB;

    private ChambreDao chambreDao;

    private HistoriqueDao historiqueDao;

    private ClientDao clientDao;

    private ReservationDao reservationDao;

    private Button bRetours,bSuppr;

    private TextView tDateDebut,tDateFin,tClient,tChambre;

    private List<Historique> list;

    private List<Chambre> list2;

    private List<Reservation> list3;

    private List<Client> list4;

    private Bundle bundle;

    private int idReserv,idClient,idChambre,idHistorique;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique_form);

        this.instance = SingletonDb.getInstance(this);
        this.stars10DB = this.instance.getAppDatabase();
        this.historiqueDao = this.stars10DB.historiqueDao();
        this.chambreDao = this.stars10DB.chambreDao();
        this.clientDao = this.stars10DB.clientDao();
        this.reservationDao = this.stars10DB.reservationDao();

        this.bRetours = findViewById(R.id.bRetoursHistoriqueForm);
        this.bSuppr = findViewById(R.id.bSupprHistorique);
        this.bRetours.setOnClickListener(this);
        this.bSuppr.setOnClickListener(this);
        this.tDateDebut = findViewById(R.id.dateDebutHistoriqueForm);
        this.tDateFin = findViewById(R.id.dateFinHistoriqueForm);
        this.tChambre = findViewById(R.id.chambre_historique);
        this.tClient = findViewById(R.id.client_historique);

        this.bundle = this.getIntent().getExtras();
        this.idHistorique = this.bundle.getInt("idHistorique");
        this.idChambre = this.bundle.getInt("chambreId");
        this.idClient = this.bundle.getInt("clientId");
        this.idReserv = this.bundle.getInt("reservationId");

        if(this.instance.getReservationHashMap().isEmpty()) {
            this.list3 = new ArrayList<>();
            this.list3 = this.reservationDao.findAll();
            this.instance.getReservationHashMap().clear();
            for(Reservation reservation: this.list3) {
                this.instance.addToReservationHashMap(reservation);
            }
        }
        this.tDateDebut.setText(this.instance.getReservationHashMap().get(this.idReserv).getDateDebut());
        this.tDateFin.setText(this.instance.getReservationHashMap().get(this.idReserv).getDateFin());

        if(this.instance.getClientHashMap().isEmpty()) {
            this.list4 = new ArrayList<>();
            this.list4 = this.clientDao.findAll();
            this.instance.getClientHashMap().clear();
            for(Client client: this.list4) {
                this.instance.addToClientHashMap(client);
            }
        }

        if(this.instance.getChambreHashMap().isEmpty()) {
            this.list2 = new ArrayList<>();
            this.list2 = this.chambreDao.findAll();
            this.instance.getChambreHashMap().clear();
            for(Chambre chambre: this.list2) {
                this.instance.addToChambreHashMap(chambre);
            }
        }
        this.tChambre.setText(this.instance.getClientHashMap().get(this.idClient).getNom() + " " + this.instance.getClientHashMap().get(this.idClient).getPrenom());
        this.tClient.setText(this.instance.getChambreHashMap().get(this.idChambre).getNum());
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.bRetoursHistoriqueForm) {
            this.finish();
        } else if (v.getId() == R.id.bSupprHistorique) {
            this.historiqueDao.delete(this.instance.getHistoriqueHashMap().get(this.idHistorique));
            this.instance.removeFromHistoriqueHashMap(this.idHistorique);
            System.out.println(this.historiqueDao.findAll());
            Toast.makeText(this,"Historique supprimé avec succée",Toast.LENGTH_SHORT).show();
            this.finish();
        }
    }
}