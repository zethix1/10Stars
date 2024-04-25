package fr.sio.a10stars.Db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import fr.sio.a10stars.Db.DataAccess.Dao.Historique.HistoriqueDao;
import fr.sio.a10stars.Db.DataAccess.Dao.Maintenance.MaintenanceDao;
import fr.sio.a10stars.Modele.Chambre.Chambre;
import fr.sio.a10stars.Db.DataAccess.Dao.Chambre.ChambreDao;
import fr.sio.a10stars.Db.DataAccess.Dao.Client.ClientDao;
import fr.sio.a10stars.Db.DataAccess.Dao.Reservation.ReservationDao;
import fr.sio.a10stars.Modele.Client.Client;
import fr.sio.a10stars.Modele.Historique.Historique;
import fr.sio.a10stars.Modele.Maintenance.Maintenance;
import fr.sio.a10stars.Modele.Reservation.Reservation;

@Database(entities = {Client.class, Reservation.class, Chambre.class, Maintenance.class, Historique.class}, version = 12, exportSchema = false)
public abstract class Stars10DB extends RoomDatabase {

    public abstract ClientDao clientDao();

    public abstract ChambreDao chambreDao();

    public abstract ReservationDao reservationDao();

    public abstract MaintenanceDao maintenanceDao();

    public abstract HistoriqueDao historiqueDao();
}

