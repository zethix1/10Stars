package fr.sio.a10stars.View.Maintenance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import fr.sio.a10stars.View.Chambre.ChambreMenu;
import fr.sio.a10stars.Db.DataAccess.Dao.Maintenance.MaintenanceDao;
import fr.sio.a10stars.Db.SingletonDb;
import fr.sio.a10stars.Db.Stars10DB;
import fr.sio.a10stars.Controller.Maintenance.AdapterModifMaintenance;
import fr.sio.a10stars.Modele.Maintenance.Maintenance;
import fr.sio.a10stars.R;

public class MaintenanceMenu extends AppCompatActivity implements View.OnClickListener {

    private SingletonDb instance;

    private MaintenanceDao maintenanceDao;

    private List<Maintenance> list;

    private ArrayAdapter<Maintenance> arrayAdapter;

    private Stars10DB stars10DB;

    private Button bRetours,bAjout;

    private ListView listView;

    @Override
    protected void onResume() {
        super.onResume();
        findMaintenance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_menu);

        this.listView = findViewById(R.id.maintenanceListMenu);
        this.bRetours = findViewById(R.id.bRetoursMaintenanceMenu);
        this.bAjout = findViewById(R.id.bAjoutMaintenanceMenu);
        this.bAjout.setOnClickListener(this);
        this.bRetours.setOnClickListener(this);
        this.instance = SingletonDb.getInstance(this);
        this.stars10DB = this.instance.getAppDatabase();
        this.maintenanceDao = this.stars10DB.maintenanceDao();
        this.arrayAdapter = new AdapterModifMaintenance(this,R.layout.liste_view_maintenance,new ArrayList<>());
        findMaintenance();
        this.listView.setAdapter(this.arrayAdapter);
    }

    public void findMaintenance() {
        list = new ArrayList<>();
        if(!this.instance.getMaintenanceHashMap().isEmpty()) {
            this.list.addAll(this.instance.getMaintenanceHashMap().values());
        }else {
            this.list = this.maintenanceDao.findAll();
        }
        this.instance.getMaintenanceHashMap().clear();
        this.arrayAdapter.clear();
        for(Maintenance maintenance : this.list) {
            this.arrayAdapter.add(maintenance);
            this.instance.addToMaintenanceHashMap(maintenance);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.bRetoursMaintenanceMenu) {
            this.finish();
        } else if (v.getId() == R.id.bAjoutMaintenanceMenu) {
            Intent intent = new Intent(this, ChambreMenu.class);
            intent.putExtra("maintenance",true);
            this.startActivity(intent);
        }
    }
}