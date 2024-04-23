package fr.sio.a10stars.View.Historique;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import fr.sio.a10stars.Db.DataAccess.Dao.Historique.HistoriqueDao;
import fr.sio.a10stars.Db.SingletonDb;
import fr.sio.a10stars.Db.Stars10DB;
import fr.sio.a10stars.Controller.Historique.AdapterDetailHistorique;
import fr.sio.a10stars.Modele.Historique.Historique;
import fr.sio.a10stars.R;

public class HistoriqueMenu extends AppCompatActivity implements View.OnClickListener {

    private SingletonDb instance;

    private Stars10DB stars10DB;

    private HistoriqueDao historiqueDao;

    private ArrayAdapter<Historique> arrayAdapter;

    private List<Historique> list;

    private ListView listView;

    private Button bRetours;

    @Override
    protected void onResume() {
        super.onResume();
        findHistorique();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique_menu);

        this.instance = SingletonDb.getInstance(this);
        this.stars10DB = this.instance.getAppDatabase();
        this.historiqueDao = this.stars10DB.historiqueDao();

        this.bRetours = findViewById(R.id.bRetoursHistoriqueMenu);
        this.listView = findViewById(R.id.historiqueListMenu);
        this.bRetours.setOnClickListener(this);

        this.arrayAdapter = new AdapterDetailHistorique(this,R.layout.liste_view_historique,new ArrayList<>());
        findHistorique();
        this.listView.setAdapter(this.arrayAdapter);
        System.out.println(this.historiqueDao.findAll());
    }

    public void findHistorique() {
        list = new ArrayList<>();
        if(!this.instance.getHistoriqueHashMap().isEmpty()) {
            this.list.addAll(this.instance.getHistoriqueHashMap().values());
        }else {
            this.list = this.historiqueDao.findAll();
        }
        System.out.println(this.list);
        this.instance.getHistoriqueHashMap().clear();
        this.arrayAdapter.clear();
        for(Historique historique : this.list) {
            this.arrayAdapter.add(historique);
            this.instance.addToHistoriqueHashMap(historique);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.bRetoursHistoriqueMenu) {
            this.finish();
        }
    }
}