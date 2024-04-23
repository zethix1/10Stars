package fr.sio.a10stars.Db.DataAccess.Dao.Maintenance;

import androidx.room.*;

import java.util.List;

import fr.sio.a10stars.Modele.Client.Client;
import fr.sio.a10stars.Modele.Maintenance.Maintenance;

@Dao
public interface MaintenanceDao {

    @Query("SELECT * FROM Maintenance")
    List<Maintenance> findAll();

    @Query("SELECT * FROM Maintenance WHERE fkChambre = :idChambre ")
    List<Maintenance> findMaintenancesByIdChambre(int idChambre);

    @Insert
    long insert(Maintenance maintenance);

    @Update
    int update(Maintenance maintenance);

    @Delete
    int delete(Maintenance maintenance);
}
