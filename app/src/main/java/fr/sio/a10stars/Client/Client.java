package fr.sio.a10stars.Client;

public class Client {

    private int id;

    public static int CurrentIdItem;

    private String nom;

    private String prenom;

    private String email;

    private String telephone;

    private String comm;


    public Client(int id, String nom, String prenom, String email, String telephone,String comm) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.comm = comm;
        Client.CurrentIdItem = id;
    }

    public static int getCurrentIdItem() {
        return CurrentIdItem;
    }

    public static void setCurrentIdItem(int currentIdItem) {
        CurrentIdItem = currentIdItem;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComm() {
        return comm;
    }

    public void setComm(String comm) {
        this.comm = comm;
    }

    @Override
    public String toString() {
        return "Client{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", comm='" + comm + '\'' +
                '}';
    }
}
