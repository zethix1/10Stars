package fr.sio.a10stars.Chambre;

public class Chambre {

    private int id;

    public static int CurrentIdItem;

    private String statut;

    private int maxP;

    private int nb_lit_double;

    private int nb_lit_simple;

    private int etage;

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

    public Chambre(int id,int maxP,String statut, int nb_lit_simple,int nb_lit_double, int etage, String num, String comm) {
        this.id = id;
        this.maxP = maxP;
        this.statut = statut;
        this.nb_lit_simple = nb_lit_simple;
        this.nb_lit_double = nb_lit_double;
        this.etage = etage;
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



    public int getNb_lit_double() {
        return nb_lit_double;
    }

    public void setNb_lit_double(int nb_lit_double) {
        this.nb_lit_double = nb_lit_double;
    }

    public int getNb_lit_simple() {
        return nb_lit_simple;
    }

    public void setNb_lit_simple(int nb_lit_simple) {
        this.nb_lit_simple = nb_lit_simple;
    }

    public int getEtage() {
        return etage;
    }

    public void setEtage(int etage) {
        this.etage = etage;
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
        return "Chambre{" +
                "statut='" + statut + '\'' +
                ", maxP=" + maxP +
                ", nb_lit_double=" + nb_lit_double +
                ", nb_lit_simple=" + nb_lit_simple +
                ", etage=" + etage +
                ", num='" + num + '\'' +
                ", comm='" + comm + '\'' +
                '}';
    }
}
