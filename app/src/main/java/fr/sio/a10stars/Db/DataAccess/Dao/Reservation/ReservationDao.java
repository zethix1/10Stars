package fr.sio.a10stars.Db.DataAccess.Dao.Reservation;

import androidx.room.*;

import java.util.List;

import fr.sio.a10stars.Modele.Maintenance.Maintenance;
import fr.sio.a10stars.Modele.Reservation.Reservation;

@Dao
public interface ReservationDao {

    @Query("SELECT * FROM Reservation")
    List<Reservation> findAll();

    @Query("SELECT * FROM Reservation WHERE fkChambre = :idChambre ")
    List<Reservation> findReservationsByIdChambre(int idChambre);


    @Query("SELECT * FROM Reservation WHERE nombreInvite = :nombreInvite AND fkChambre = :fkChambre AND fkClient = :fkClient AND commentaire = :comm AND dateDebut = :dateDebut AND dateFin = :dateFin AND nombreAdulte = :nombreA AND nombreEnfant = :nombreE")
    List<Reservation> findRservationId(int nombreInvite,int fkChambre,int fkClient,String comm,String dateDebut,String dateFin,int nombreA,int nombreE);

    @Insert
    long insert(Reservation reservation);

    @Update
    int update(Reservation reservation);

    @Delete
    int delete(Reservation reservation);
}
