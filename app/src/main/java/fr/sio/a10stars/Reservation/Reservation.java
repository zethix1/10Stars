package fr.sio.a10stars.Reservation;

public class Reservation {

    private int id,nbInvite,fkChambre,fkClient;

    private String dateDebut,dateFin,comm;

    public Reservation(int id, int nbInvite, int fkChambre, int fkClient, String dateDebut, String dateFin, String comm) {
        this.id = id;
        this.nbInvite = nbInvite;
        this.fkChambre = fkChambre;
        this.fkClient = fkClient;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.comm = comm;
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
                ", dateDebut='" + dateDebut + '\'' +
                ", dateFin='" + dateFin + '\'' +
                ", comm='" + comm + '\'' +
                '}';
    }
}
