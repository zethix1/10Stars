package fr.sio.a10stars.Db.DataAccess.Dao.Chambre;

import androidx.room.*;

import java.util.List;

import fr.sio.a10stars.Modele.Chambre.Chambre;

@Dao
public interface ChambreDao {

    @Query("SELECT * FROM Chambre")
    List<Chambre> findAll();

    @Query("SELECT * from chambre WHERE (statut = 'disponible' OR statut = 'occupe')")
    List<Chambre> findChambreDispoOccupe();

    @Query("SELECT * from chambre WHERE statut = 'maintenance'")
    List<Chambre> findChambreMaintenance();

    @Query("SELECT * FROM Chambre WHERE numeroChambre LIKE '%' || :query || '%' AND (statut = 'disponible' OR statut = 'occupe')")
    List<Chambre> findChambresByNumDispoOccupe(String query);

    @Query("SELECT * FROM Chambre WHERE numeroChambre LIKE '%' || :query || '%' AND (statut = 'maintenance')")
    List<Chambre> findChambresByNumMaintenance(String query);

    @Query("SELECT * from chambre WHERE nombreLitSimple = :nbLitSimple AND nombreLitDouble = :nbLitDouble ")
    List<Chambre> findChambresBySimpleDouble(String nbLitSimple,String nbLitDouble);

    @Query("SELECT * from chambre WHERE nombreLitSimple = :nbLitSimple")
    List<Chambre> findChambresBySimple(String nbLitSimple);

    @Query("SELECT * from chambre WHERE nombreLitDouble = :nbLitDouble")
    List<Chambre> findChambresByDouble(String nbLitDouble);

    @Insert
    long insert(Chambre chambre);

    @Update
    int update(Chambre chambre);

    @Delete
    int delete(Chambre chambre);
}
