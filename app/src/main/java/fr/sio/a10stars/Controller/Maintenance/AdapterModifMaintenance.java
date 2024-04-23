package fr.sio.a10stars.Controller.Maintenance;

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

import fr.sio.a10stars.Modele.Maintenance.Maintenance;
import fr.sio.a10stars.R;
import fr.sio.a10stars.View.Maintenance.MaintenanceForm;

public class AdapterModifMaintenance extends ArrayAdapter<Maintenance> {
    public AdapterModifMaintenance(Context context, int ressource, List<Maintenance> objects) {
        super(context,ressource,objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.liste_view_maintenance, parent, false);
        }

        TextView dateDebut = itemView.findViewById(R.id.dateDebutMaintenanceMenu);
        TextView dateFin = itemView.findViewById(R.id.dateFinMaintenanceMenu);
        TextView numChambre = itemView.findViewById(R.id.numChambreMaintenance);
        TextView commentaire = itemView.findViewById(R.id.commMaintenance);
        Button button = itemView.findViewById(R.id.modificationMaintenance);

        Maintenance currentMaintenance = getItem(position);
        if (currentMaintenance != null) {
            dateDebut.setText(currentMaintenance.getDateDebut());
            dateFin.setText(currentMaintenance.getDateFin());
            numChambre.setText(String.valueOf(currentMaintenance.getFkChambre()));
            commentaire.setText(currentMaintenance.getCommentaire());
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MaintenanceForm.class);
                intent.putExtra("ajouter",false);
                intent.putExtra("idMaintenance",currentMaintenance.getId());
                intent.putExtra("chambreId",currentMaintenance.getFkChambre());
                getContext().startActivity(intent);
            }
        });

        return itemView;
    }
}
