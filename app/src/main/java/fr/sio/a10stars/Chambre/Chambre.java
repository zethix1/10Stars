package fr.sio.a10stars.Chambre;

public class Chambre {

    private int id;

    public static int CurrentIdItem;

    private int maxP;

    private String statut;

    private int etage;

    private String type;

    private String num;

    private String comm;

    /*public Chambre(int maxP, String statut, int etage, String type, String num, String comm) {
        this.maxP = maxP;
        this.statut = statut;
        this.etage = etage;
        this.type = type;
        this.num = num;
        this.comm = comm;
    }*/

    public Chambre(int id,int maxP, String statut, int etage, String type, String num, String comm) {
        this.id = id;
        this.maxP = maxP;
        this.statut = statut;
        this.etage = etage;
        this.type = type;
        this.num = num;
        this.comm = comm;
        Chambre.CurrentIdItem = id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaxP() {
        return maxP;
    }

    public void setMaxP(int maxP) {
        this.maxP = maxP;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public int getEtage() {
        return etage;
    }

    public void setEtage(int etage) {
        this.etage = etage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getComm() {
        return comm;
    }

    public void setComm(String comm) {
        this.comm = comm;
    }

    @Override
    public String toString() {
        return "Chambre{" + '\n' +
                "maxP=" + maxP + '\n' +
                ", statut='" + statut + '\n' +
                ", etage=" + etage + '\n' +
                ", type='" + type + '\n' +
                ", num='" + num + '\n' +
                ", comm='" + comm + '\n' +
                '}';
    }
}
