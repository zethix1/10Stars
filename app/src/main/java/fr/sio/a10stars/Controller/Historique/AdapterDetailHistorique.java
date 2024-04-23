package fr.sio.a10stars.Controller.Historique;

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

import fr.sio.a10stars.Modele.Historique.Historique;
import fr.sio.a10stars.R;
import fr.sio.a10stars.View.Historique.HistoriqueForm;

public class AdapterDetailHistorique extends ArrayAdapter<Historique> {
    public AdapterDetailHistorique(Context context, int ressource, List<Historique> objects) {
        super(context,ressource,objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.liste_view_historique, parent, false);
        }

        TextView dateDebut = itemView.findViewById(R.id.dateDebutHistoriqueMenu);
        TextView dateFin = itemView.findViewById(R.id.dateFinHistoriqueMenu);
        TextView numChambre = itemView.findViewById(R.id.numChambreHistoriqueMenu);
        TextView nomClient = itemView.findViewById(R.id.nomClientHistoriqueMenu);
        Button button = itemView.findViewById(R.id.detailHistorique);

        Historique currentHistorique = getItem(position);
        if (currentHistorique != null) {
            dateDebut.setText(currentHistorique.getDateDebut());
            dateFin.setText(currentHistorique.getDateFin());
            numChambre.setText(String.valueOf(currentHistorique.getFkChambre()));
            nomClient.setText(String.valueOf(currentHistorique.getFkClient()));
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), HistoriqueForm.class);
                intent.putExtra("idHistorique",currentHistorique.getId());
                intent.putExtra("chambreId",currentHistorique.getFkChambre());
                intent.putExtra("clientId",currentHistorique.getFkClient());
                intent.putExtra("reservationId",currentHistorique.getFkReservation());
                getContext().startActivity(intent);
            }
        });

        return itemView;
    }


}
