package fr.sio.a10stars.Chambre;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import fr.sio.a10stars.R;


public class MyAdapter extends ArrayAdapter<String> {

    public MyAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.liste_view, parent, false);
        }

        Button chambreButton = convertView.findViewById(R.id.Chambre);
        Button clientButton = convertView.findViewById(R.id.Client);
        chambreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click for the specific item
                Intent intent = new Intent(getContext(), ChambreMenu.class);
                getContext().startActivity(intent);
            }
        });

        clientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChambreMenu.class);
                getContext().startActivity(intent);
            }
        });


        // Set other buttons' click listeners as needed

        return convertView;
    }
}

