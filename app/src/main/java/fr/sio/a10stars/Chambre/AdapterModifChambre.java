package fr.sio.a10stars.Chambre;

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

import fr.sio.a10stars.R;

public class AdapterModifChambre extends ArrayAdapter<Chambre> {
    public AdapterModifChambre(Context context, int ressource, List<Chambre> objects) {
        super(context,ressource,objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.liste_view_chambre, parent, false);
        }

        TextView textView = itemView.findViewById(R.id.chambre3);
        Button button = itemView.findViewById(R.id.modificationChambre);

        Chambre currentChambre = getItem(position);
        if (currentChambre != null) {
            textView.setText(currentChambre.toString());
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChambreForm.class);
                intent.putExtra("ajouter",false);
                Chambre.CurrentIdItem = currentChambre.getId();
                getContext().startActivity(intent);
            }
        });

        return itemView;
    }
}
