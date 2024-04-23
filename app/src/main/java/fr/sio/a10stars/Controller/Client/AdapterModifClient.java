package fr.sio.a10stars.Controller.Client;

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

import fr.sio.a10stars.Db.SingletonDb;
import fr.sio.a10stars.View.Client.ClientForm;
import fr.sio.a10stars.View.Historique.HistoriqueMenu;
import fr.sio.a10stars.Modele.Client.Client;
import fr.sio.a10stars.Modele.Historique.Historique;
import fr.sio.a10stars.R;

public class AdapterModifClient extends ArrayAdapter<Client> {
    public AdapterModifClient(Context context, int ressource, List<Client> objects) {
        super(context,ressource,objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.liste_view_client, parent, false);
        }

        TextView nom = itemView.findViewById(R.id.nomClientMenu);
        TextView prenom = itemView.findViewById(R.id.prenomClientMenu);
        TextView email = itemView.findViewById(R.id.emailClient);
        TextView telephone = itemView.findViewById(R.id.telephoneClient);
        TextView commentaire = itemView.findViewById(R.id.commentaireClient);
        Button button = itemView.findViewById(R.id.modificationClient);
        Button button1 = itemView.findViewById(R.id.historiqueClient);

        Client currentClient = getItem(position);
        if (currentClient != null) {
            nom.setText(currentClient.getNom());
            prenom.setText(String.valueOf(currentClient.getPrenom()));
            email.setText(String.valueOf(currentClient.getEmail()));
            telephone.setText(currentClient.getTelephone());
            commentaire.setText(currentClient.getComm());
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ClientForm.class);
                intent.putExtra("ajouter",false);
                intent.putExtra("idClient",currentClient.getId());
                getContext().startActivity(intent);
            }
        });

        button1.setVisibility(View.GONE);
        for(Historique historique: SingletonDb.getInstance(getContext()).getAppDatabase().historiqueDao().findAll()) {
            if(historique.getFkClient() == currentClient.getId()) {
                button1.setVisibility(View.VISIBLE);
            }
        }
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), HistoriqueMenu.class);
                getContext().startActivity(intent);
            }
        });

        return itemView;
    }
}
