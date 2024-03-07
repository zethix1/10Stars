package fr.sio.a10stars;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Stars10DB extends SQLiteOpenHelper {

    // Nom de la base de données
    private static final String NOM_BASE_DE_DONNEES = "stars10";

    // Version de la base de données
    private static final int VERSION_BASE_DE_DONNEES = 1;

    // Nom de la table
    private static final String TABLE_CLIENTS = "clients";

    // Colonnes de la table
    private static final String COLONNE_ID = "id";
    private static final String COLONNE_NOM = "nom";
    private static final String COLONNE_PRENOM = "prenom";
    private static final String COLONNE_EMAIL = "email";
    private static final String COLONNE_TELEPHONE = "telephone";
    private static final String COLONNE_COMMENTAIRE = "commentaire";

    // Requête de création de la table
    private static final String CREATION_TABLE_CLIENTS =
            "CREATE TABLE " + TABLE_CLIENTS + " (" +
                    COLONNE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLONNE_NOM + " TEXT CHECK( LENGTH(nom) <= 15) NOT NULL," +
                    COLONNE_PRENOM + " TEXT CHECK ( LENGTH(prenom) <= 15) NOT NULL," +
                    COLONNE_EMAIL + " TEXT CHECK ( LENGTH(email) <= 25 ) NOT NULL DEFAULT 'placeholder@gmail.com'," +
                    COLONNE_TELEPHONE + " TEXT CHECK( LENGTH(telephone) <= 25) ," +
                    COLONNE_COMMENTAIRE + " TEXT DEFAULT NULL)";


    // Nom de la table
    private static final String TABLE_CHAMBRE = "chambre";

    // Colonnes de la table
    private static final String COLONNE_ID_CHAMBRE = "id";
    private static final String COLONNE_MAX_PERSONNE_CHAMBRE = "maxPersonne";
    private static final String COLONNE_STATUT_CHAMBRE = "statut";
    private static final String COLONNE_NUM_CHAMBRE = "numeroChambre";
    private static final String COLONNE_ETAGE_CHAMBRE = "etage";
    private static final String COLONNE_TYPELIT_CHAMBRE = "typeLit";
    private static final String COLONNE_COMMENTAIRE_CHAMBRE = "commentaire";

    // Requête de création de la table
    private static final String CREATION_TABLE_CHAMBRE =
            "CREATE TABLE " + TABLE_CHAMBRE + " (" +
                    COLONNE_ID_CHAMBRE + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    COLONNE_MAX_PERSONNE_CHAMBRE + " INTEGER DEFAULT '0'," +
                    COLONNE_STATUT_CHAMBRE + " TEXT CHECK( statut IN ('disponible','occupe','maintenance')) NOT NULL DEFAULT 'disponible'," +
                    COLONNE_ETAGE_CHAMBRE + " INTEGER CHECK( LENGTH(etage) <= 2 ) NOT NULL," +
                    COLONNE_TYPELIT_CHAMBRE + " TEXT CHECK( typeLit IN ('simple','double') )," +
                    COLONNE_NUM_CHAMBRE + " TEXT CHECK ( LENGTH(numeroChambre) <= 5 ) NOT NULL," +
                    COLONNE_COMMENTAIRE_CHAMBRE + " TEXT DEFAULT NULL)";

    private static final String TABLE_RESERVATION = "reservations";
    private static final String COLONNE_ID_RESERVATION = "id";
    private static final String COLONNE_DATEARRIVE_RESERVATION = "dateArrivee";
    private static final String COLONNE_DATEDEPART_RESERVATION = "dateDepart";
    private static final String COLONNE_NBINVITE_RESERVATION = "nombreInvites";

    private static final String COLONNE_COMMENTAIRE_RESERVATION = "commentaire";

    private static final String FK_IDCHAMBRE_RESERVATION = "idChambre";

    private static final String FK_IDCLIENT_RESERVATION = "idClient";


    private static final String CREATION_TABLE_RESERVATION =
            "CREATE TABLE " + TABLE_RESERVATION + " (" +
                    COLONNE_ID_RESERVATION + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    COLONNE_DATEARRIVE_RESERVATION + "TEXT," +
                    COLONNE_DATEDEPART_RESERVATION + " TEXT," +
                    COLONNE_NBINVITE_RESERVATION+ " INTEGER CHECK( LENGTH(nombreInvites) <= 50 )," +
                    COLONNE_COMMENTAIRE_RESERVATION + " TEXT," +
                    FK_IDCHAMBRE_RESERVATION + "INTEGER," +
                    FK_IDCLIENT_RESERVATION + "INTEGER," +
                    "CONSTRAINT fk_idChambre " +
                    "FOREIGN KEY ("+ FK_IDCHAMBRE_RESERVATION + ")" +
                    "REFERENCES " + TABLE_CHAMBRE + " ("+ COLONNE_ID_CHAMBRE + ")," +
                    "CONSTRAINT fk_idClient " +
                    "FOREIGN KEY ("+ FK_IDCLIENT_RESERVATION + ")" +
                    "REFERENCES " + TABLE_CLIENTS + " ("+ COLONNE_ID+ "))" ;

    private static final String TABLE_MAINTENANCE = "maintenance";
    private static final String COLONNE_ID_MAINTENANCE= "id";
    private static final String COLONNE_DATEDEBUT_MAINTENANCE = "dateDebut";
    private static final String COLONNE_DATEFIN_MAINTENANCE = "dateFin";
    private static final String COLONNE_COMMENTAIRE_MAINTENANCE = "commentaire";

    private static final String FK_IDCHAMBRE_MAINTENANCE = "idchambre";

    private static final String CREATION_TABLE_MAINTENANCE =
            "CREATE TABLE " + TABLE_MAINTENANCE + " (" +
                    COLONNE_ID_MAINTENANCE + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    COLONNE_DATEDEBUT_MAINTENANCE + "TEXT," +
                    COLONNE_DATEFIN_MAINTENANCE+ " TEXT," +
                    COLONNE_COMMENTAIRE_MAINTENANCE + " TEXT," +
                    FK_IDCHAMBRE_MAINTENANCE + "INTEGER," +
                    "CONSTRAINT fk_idChambre " +
                    "FOREIGN KEY ("+ FK_IDCHAMBRE_MAINTENANCE + ")" +
                    "REFERENCES " + TABLE_CHAMBRE + " ("+ COLONNE_ID_CHAMBRE + "))" ;


    public Stars10DB(Context context) {
        super(context, NOM_BASE_DE_DONNEES, null, VERSION_BASE_DE_DONNEES);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Création de la table lors de la première exécution
        db.execSQL(CREATION_TABLE_CLIENTS);
        db.execSQL(CREATION_TABLE_RESERVATION);
        db.execSQL(CREATION_TABLE_CHAMBRE);
        db.execSQL(CREATION_TABLE_MAINTENANCE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Mise à jour de la base de données (si nécessaire)
        // Cette méthode est appelée lorsqu'une nouvelle version de la base de données est disponible.
    }
}
