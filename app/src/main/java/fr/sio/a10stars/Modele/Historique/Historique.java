package fr.sio.a10stars.Modele.Historique;

import androidx.room.*;

import fr.sio.a10stars.Modele.Chambre.Chambre;
import fr.sio.a10stars.Modele.Client.Client;
import fr.sio.a10stars.Modele.Reservation.Reservation;

@Entity(tableName = "Historique",foreignKeys = {
        @ForeignKey(
                entity = Chambre.class,
                parentColumns = "id",
                childColumns = "fkChambre",
                onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
                entity = Client.class,
                parentColumns = "id",
                childColumns = "fkClient",
                onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
                entity = Reservation.class,
                parentColumns = "id",
                childColumns = "fkReservation",
                onDelete = ForeignKey.CASCADE
        )
})
public class Historique {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int fkClient;

    private int fkReservation;

    private int fkChambre;

    @ColumnInfo( name = "dateDebut")
    private String dateDebut;

    @ColumnInfo( name = "dateFin")
    private String dateFin;

    @Ignore
    public Historique(int id, int fkClient, int fkReservation, int fkChambre, String dateDebut, String dateFin) {
        this.id = id;
        this.fkClient = fkClient;
        this.fkReservation = fkReservation;
        this.fkChambre = fkChambre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }


    public Historique(int fkClient, int fkReservation, int fkChambre, String dateDebut, String dateFin) {
        this.fkClient = fkClient;
        this.fkReservation = fkReservation;
        this.fkChambre = fkChambre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFkClient() {
        return fkClient;
    }

    public void setFkClient(int fkClient) {
        this.fkClient = fkClient;
    }

    public int getFkReservation() {
        return fkReservation;
    }

    public void setFkReservation(int fkReservation) {
        this.fkReservation = fkReservation;
    }

    public int getFkChambre() {
        return fkChambre;
    }

    public void setFkChambre(int fkChambre) {
        this.fkChambre = fkChambre;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    @Override
    public String toString() {
        return "Historique{" +
                "id=" + id +
                ", fkClient=" + fkClient +
                ", fkReservation=" + fkReservation +
                ", fkChambre=" + fkChambre +
                ", dateDebut='" + dateDebut + '\'' +
                ", dateFin='" + dateFin + '\'' +
                '}';
    }
}
