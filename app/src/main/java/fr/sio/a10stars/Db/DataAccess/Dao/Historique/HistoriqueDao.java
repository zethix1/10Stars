package fr.sio.a10stars.Db.DataAccess.Dao.Historique;

import androidx.room.*;

import java.util.List;

import fr.sio.a10stars.Modele.Historique.Historique;
import fr.sio.a10stars.Modele.Maintenance.Maintenance;

@Dao
public interface HistoriqueDao {

    @Query("SELECT * FROM Historique")
    List<Historique> findAll();

    @Insert
    long insert(Historique historique);

    @Update
    int update(Historique historique);

    @Delete
    int delete(Historique historique);
}
