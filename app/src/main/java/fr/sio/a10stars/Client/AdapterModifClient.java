package fr.sio.a10stars.Client;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.Random;

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
                Client.CurrentIdItem = currentClient.getId();
                getContext().startActivity(intent);
            }
        });

        return itemView;
    }
}
