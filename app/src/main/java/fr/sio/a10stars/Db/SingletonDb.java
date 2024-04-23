package fr.sio.a10stars.Db;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.HashMap;
import java.util.List;

import fr.sio.a10stars.Modele.Chambre.Chambre;
import fr.sio.a10stars.Modele.Client.Client;
import fr.sio.a10stars.Modele.Historique.Historique;
import fr.sio.a10stars.Modele.Maintenance.Maintenance;
import fr.sio.a10stars.Modele.Reservation.Reservation;

public class SingletonDb {
    private Context mCtx;
    private static volatile SingletonDb mInstance;
    //our app database object
    private static volatile Stars10DB stars10DB;

    private static volatile HashMap<Integer, Client> clientHashMap;

    private static volatile HashMap<Integer, Chambre> chambreHashMap;

    private static volatile HashMap<Integer, Reservation> reservationHashMap;

    private static volatile HashMap<Integer, Maintenance> maintenanceHashMap;

    private static volatile HashMap<Integer, Historique> historiqueHashMap;

    private SingletonDb(Context mCtx) {
        this.mCtx = mCtx;
        //creating the app database with Room database builder
        RoomDatabase.Builder<Stars10DB> Stars10db= Room.databaseBuilder(mCtx,
                Stars10DB.class, "Stars10").fallbackToDestructiveMigration();
        //autorise à exécuter l’accès bd dans le thread principal
        Stars10db.allowMainThreadQueries();
        stars10DB = Stars10db.build();
        clientHashMap = new HashMap<Integer,Client>();
        chambreHashMap = new HashMap<Integer,Chambre>();
        reservationHashMap = new HashMap<Integer,Reservation>();
        maintenanceHashMap = new HashMap<Integer,Maintenance>();
        historiqueHashMap = new HashMap<Integer,Historique>();
    }

    public static synchronized SingletonDb getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new SingletonDb(mCtx);
        }
        return mInstance;
    }
    public Stars10DB getAppDatabase() {
        return stars10DB;
    }


    //client hashmap
    public HashMap<Integer,Client> getClientHashMap() {
        return clientHashMap;
    }

    public void updateClientHashMap(List<Client> clients) {
        clientHashMap.clear();
        for (Client client : clients) {
            clientHashMap.put(client.getId(), client);
        }
    }

    public void addToClientHashMap(Client client) {
        clientHashMap.put(client.getId(), client);
    }

    public void removeFromClientHashMap(int clientId) {
        clientHashMap.remove(clientId);
    }


    //chambre hashmap


    public HashMap<Integer,Chambre> getChambreHashMap() {
        return chambreHashMap;
    }

    public void updateChambreHashMap(List<Chambre> chambres) {
        chambreHashMap.clear();
        for (Chambre chambre : chambres) {
            chambreHashMap.put(chambre.getId(), chambre);
        }
    }

    public void addToChambreHashMap(Chambre chambre) {
        chambreHashMap.put(chambre.getId(), chambre);
    }

    public void removeFromChambreHashMap(int chambreId) {
        chambreHashMap.remove(chambreId);
    }


    //reservation hashmap


    public HashMap<Integer,Reservation> getReservationHashMap() {
        return reservationHashMap;
    }

    public void updateReservationHashMap(List<Reservation> reservations) {
        reservationHashMap.clear();
        for (Reservation reservation : reservations) {
            reservationHashMap.put(reservation.getId(), reservation);
        }
    }

    public void addToReservationHashMap(Reservation reservation) {
        reservationHashMap.put(reservation.getId(), reservation);
    }

    public void removeFromReservationHashMap(int reservationId) {
        reservationHashMap.remove(reservationId);
    }

    //maintenance hashmap
    public HashMap<Integer,Maintenance> getMaintenanceHashMap() {
        return maintenanceHashMap;
    }

    public void updateMaintenanceHashMap(List<Maintenance> maintenances) {
        maintenanceHashMap.clear();
        for (Maintenance maintenance : maintenances) {
            maintenanceHashMap.put(maintenance.getId(), maintenance);
        }
    }

    public void addToMaintenanceHashMap(Maintenance maintenance) {
        maintenanceHashMap.put(maintenance.getId(), maintenance);
    }

    public void removeFromMaintenanceHashMap(int maintenanceId) {
        maintenanceHashMap.remove(maintenanceId);
    }

    //historique hashmap
    public HashMap<Integer,Historique> getHistoriqueHashMap() {
        return historiqueHashMap;
    }

    public void updateHistoriqueHashMap(List<Historique> historiques) {
        historiqueHashMap.clear();
        for (Historique historique : historiques) {
            historiqueHashMap.put(historique.getId(), historique);
        }
    }

    public void addToHistoriqueHashMap(Historique historique) {
        historiqueHashMap.put(historique.getId(), historique);
    }

    public void removeFromHistoriqueHashMap(int historiqueId) {
        historiqueHashMap.remove(historiqueId);
    }
}
