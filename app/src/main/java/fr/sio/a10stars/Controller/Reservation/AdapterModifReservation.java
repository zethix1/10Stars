package fr.sio.a10stars.Controller.Reservation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import fr.sio.a10stars.Modele.Reservation.Reservation;
import fr.sio.a10stars.R;
import fr.sio.a10stars.View.Reservation.ReservationForm;

public class AdapterModifReservation extends ArrayAdapter<Reservation> {
    public AdapterModifReservation(Context context, int ressource, List<Reservation> objects) {
        super(context,ressource,objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.liste_view_reservation, parent, false);
        }

        TextView dateArrive = itemView.findViewById(R.id.dateArriveReservationMenu);
        TextView dateDepart = itemView.findViewById(R.id.dateDepartReservationMenu);
        TextView nbInvite = itemView.findViewById(R.id.nombreInviteReserv);
        TextView nomClient = itemView.findViewById(R.id.nomClientReserv);
        TextView numChambre = itemView.findViewById(R.id.numeroChambreReserv);
        TextView commentaire = itemView.findViewById(R.id.commReserv);
        Button button = itemView.findViewById(R.id.modificationReservation);

        Reservation currentReservation = getItem(position);
        if (currentReservation != null) {
            dateArrive.setText(currentReservation.getDateDebut());
            dateDepart.setText(currentReservation.getDateFin());
            nbInvite.setText(String.valueOf(currentReservation.getNbInvite()));
            nomClient.setText(String.valueOf(currentReservation.getFkClient()));
            commentaire.setText(currentReservation.getComm());
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ReservationForm.class);
                intent.putExtra("ajouter",false);
                intent.putExtra("idReservation",currentReservation.getId());
                intent.putExtra("idChambre",currentReservation.getFkChambre());
                getContext().startActivity(intent);
            }
        });

        return itemView;
    }
}