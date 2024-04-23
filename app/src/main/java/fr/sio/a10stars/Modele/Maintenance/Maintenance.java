package fr.sio.a10stars.Modele.Maintenance;

import androidx.room.*;

import fr.sio.a10stars.Modele.Chambre.Chambre;
import fr.sio.a10stars.Modele.Client.Client;
import fr.sio.a10stars.Modele.Reservation.Reservation;

@Entity(tableName = "Maintenance",foreignKeys = {
        @ForeignKey(
                entity = Chambre.class,
                parentColumns = "id",
                childColumns = "fkChambre",
                onDelete = ForeignKey.CASCADE
        )
})
public class Maintenance {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int fkChambre;

    @ColumnInfo( name = "dateDebut")
    private String dateDebut;

    @ColumnInfo( name = "dateFin")
    private String dateFin;

    @ColumnInfo( name = "commentaire")
    private String commentaire;

    @Ignore
    public Maintenance(int id, int fkChambre, String dateDebut, String dateFin, String commentaire) {
        this.id = id;
        this.fkChambre = fkChambre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.commentaire = commentaire;
    }


    public Maintenance(int fkChambre, String dateDebut, String dateFin, String commentaire) {
        this.fkChambre = fkChambre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.commentaire = commentaire;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    @Override
    public String toString() {
        return "Maintenance{" +
                "id=" + id +
                ", fkChambre=" + fkChambre +
                ", dateDebut='" + dateDebut + '\'' +
                ", dateFin='" + dateFin + '\'' +
                ", commentaire='" + commentaire + '\'' +
                '}';
    }
}
