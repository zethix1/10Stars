package fr.sio.a10stars;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import fr.sio.a10stars.Chambre.ChambreMenu;
import fr.sio.a10stars.Client.ClientMenu;
import fr.sio.a10stars.Reservation.Filtrage;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button bClient,bChambre,bEnd,bStart,bRecherche;

    private EditText eSimple,eDouble;

    private TextView tEnd,tStart;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.bChambre = findViewById(R.id.Chambre);
        this.bClient = findViewById(R.id.Client);
        this.bChambre.setOnClickListener(this);
        this.bClient.setOnClickListener(this);
        this.bStart = findViewById(R.id.selectStartDateButton);
        this.bEnd = findViewById(R.id.selectEndDateButton);
        this.tStart = findViewById(R.id.startDateTextView);
        this.tEnd = findViewById(R.id.endDateTextView);
        this.bRecherche = findViewById(R.id.brecherche);
        this.bRecherche.setOnClickListener(this);
        this.eSimple = findViewById(R.id.filter_reserv_simple);
        this.eDouble = findViewById(R.id.filter_reserv_double);
    }

    public void showStartDatePickerDialog(View v) {
        // Afficher le dialogue de sélection de la date de début
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, startDateListener, 2024, 1, 1);
        datePickerDialog.show();
    }

    public void showEndDatePickerDialog(View v) {
        // Afficher le dialogue de sélection de la date de fin
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, endDateListener, 2024, 1, 1);
        datePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener startDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            // Traitement de la date de début sélectionnée
            // Mettre à jour l'affichage ou effectuer toute autre action nécessaire
            tStart.setText("Date de début sélectionnée : " + dayOfMonth + "/" + (month + 1) + "/" + year);
            tStart.setVisibility(View.VISIBLE);
        }
    };

    private DatePickerDialog.OnDateSetListener endDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            // Traitement de la date de fin sélectionnée
            // Mettre à jour l'affichage ou effectuer toute autre action nécessaire
            tEnd.setText("Date de fin sélectionnée : " + dayOfMonth + "/" + (month + 1) + "/" + year);
            tEnd.setVisibility(View.VISIBLE);
        }
    };

    @Override
    public void onClick(View view) {
        String enddatecut2 = null;
        String startdatecut2 = null;
        if(view.getId() == R.id.Chambre) {
            Intent intent = new Intent(this, ChambreMenu.class);
            this.startActivity(intent);
        } else if (view.getId() == R.id.Client) {
            Intent intent = new Intent(this, ClientMenu.class);
            this.startActivity(intent);
        } else if (view.getId() == R.id.brecherche) {
            Intent intent = new Intent(this, Filtrage.class);
            if(this.tStart.getText().toString() != "") {
                String[] startdatecut = this.tStart.getText().toString().split(":");
                startdatecut2 = startdatecut[1];
            }
            if(this.tEnd.getText().toString() != "") {
                String[] enddatecut = this.tEnd.getText().toString().split(":");
                enddatecut2 = enddatecut[1];
            }

            intent.putExtra("litSimple",this.eSimple.getText().toString());
            intent.putExtra("litDouble",this.eDouble.getText().toString());
            intent.putExtra("datedebut",startdatecut2);
            intent.putExtra("datefin",enddatecut2);
            this.startActivity(intent);
        }
    }
}