package fr.sio.a10stars.Reservation;

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

import fr.sio.a10stars.Chambre.Chambre;
import fr.sio.a10stars.R;

public class AdapterSelectionChambre extends ArrayAdapter<Chambre> {
    public AdapterSelectionChambre(Context context, int ressource, List<Chambre> objects) {
        super(context,ressource,objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.chambre_filtrer, parent, false);
        }

        TextView numeroChambre = itemView.findViewById(R.id.numeroChambre_filtrer);
        TextView nb_lit_simple = itemView.findViewById(R.id.nb_lit_simple_filtrer);
        TextView nb_lit_double = itemView.findViewById(R.id.nb_lit_double_filtrer);
        TextView commentaire = itemView.findViewById(R.id.commentaire_filtrer);
        Button button = itemView.findViewById(R.id.selection_chambre);
        ImageView room = itemView.findViewById(R.id.hotelRoomFiltrer);

        Chambre currentChambre = getItem(position);
        if (currentChambre != null) {
            numeroChambre.setText(currentChambre.getNum());
            nb_lit_simple.setText(String.valueOf(currentChambre.getNb_lit_simple()));
            nb_lit_double.setText(String.valueOf(currentChambre.getNb_lit_double()));
            Random random = new Random();
            int i = random.nextInt(3) + 1;
            switch (i) {
                case 1:
                    room.setImageResource(R.drawable.hotelroom);
                    break;
                case 2:
                    room.setImageResource(R.drawable.hotelroom2);
                case 3 :
                    room.setImageResource(R.drawable.hotelroom3);
            }
            commentaire.setText(currentChambre.getComm());
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ReservationForm.class);
                Chambre.CurrentIdItem = currentChambre.getId();
                getContext().startActivity(intent);
            }
        });

        return itemView;
    }
}
