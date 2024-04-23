package fr.sio.a10stars.Modele.Reservation;

import androidx.room.*;

import fr.sio.a10stars.Modele.Chambre.Chambre;
import fr.sio.a10stars.Modele.Client.Client;

@Entity(tableName = "Reservation",foreignKeys = {
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
        )
})
public class Reservation {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo( name = "nombreInvite")
    private int nbInvite;

    private int fkChambre;

    private int fkClient;

    @ColumnInfo( name = "commentaire")
    private String comm;

    @ColumnInfo( name = "dateDebut")
    private String dateDebut;

    @ColumnInfo( name = "dateFin")
    private String dateFin;

    @ColumnInfo(name = "nombreAdulte")
    private int nombreAdulte;

    @ColumnInfo(name = "nombreEnfant")
    private int nombreEnfant;

    @Ignore
    public Reservation(int id, int nbInvite, int fkChambre, int fkClient, String dateDebut, String dateFin, String comm,int nombreAdulte,int nombreEnfant) {
        this.id = id;
        this.nbInvite = nbInvite;
        this.fkChambre = fkChambre;
        this.fkClient = fkClient;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nombreAdulte = nombreAdulte;
        this.nombreEnfant = nombreEnfant;
        this.comm = comm;
    }

    public Reservation(int nbInvite, int fkChambre, int fkClient, String dateDebut, String dateFin, String comm,int nombreAdulte,int nombreEnfant) {
        this.nbInvite = nbInvite;
        this.fkChambre = fkChambre;
        this.fkClient = fkClient;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nombreEnfant = nombreEnfant;
        this.nombreAdulte = nombreAdulte;
        this.comm = comm;
    }


    public int getNombreAdulte() {
        return nombreAdulte;
    }

    public void setNombreAdulte(int nombreAdulte) {
        this.nombreAdulte = nombreAdulte;
    }

    public int getNombreEnfant() {
        return nombreEnfant;
    }

    public void setNombreEnfant(int nombreEnfant) {
        this.nombreEnfant = nombreEnfant;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbInvite() {
        return nbInvite;
    }

    public void setNbInvite(int nbInvite) {
        this.nbInvite = nbInvite;
    }

    public int getFkChambre() {
        return fkChambre;
    }

    public void setFkChambre(int fkChambre) {
        this.fkChambre = fkChambre;
    }

    public int getFkClient() {
        return fkClient;
    }

    public void setFkClient(int fkClient) {
        this.fkClient = fkClient;
    }


    public String getComm() {
        return comm;
    }

    public void setComm(String comm) {
        this.comm = comm;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "nbInvite=" + nbInvite +
                ", fkChambre=" + fkChambre +
                ", fkClient=" + fkClient +
                ", dateDebut='" + this.dateDebut + '\'' +
                ", dateFin='" + this.dateFin + '\'' +
                ", comm='" + comm + '\'' +
                '}';
    }
}
