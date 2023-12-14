package fr.sio.a10stars;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Stars10DB extends SQLiteOpenHelper {

    // Nom de la base de données
    private static final String NOM_BASE_DE_DONNEES = "clients.db";

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
                    COLONNE_NOM + " TEXT," +
                    COLONNE_PRENOM + " TEXT," +
                    COLONNE_EMAIL + " TEXT," +
                    COLONNE_TELEPHONE + " TEXT," +
                    COLONNE_COMMENTAIRE + " TEXT)";

    private static final String TABLE_RESERVATION = "reservations";
    private static final String COLONNE_ID_RESERVATION = "id";
    private static final String COLONNE_DATEARRIVE_RESERVATION = "DateArrivee";
    private static final String COLONNE_DATEDEPART_RESERVATION = "DateDepart";
    private static final String COLONNE_NBINVITE_RESERVATION = "NombreInvites";

    private static final String COLONNE_COMMENTAIRE_RESERVATION = "commentaire";

    private static final String CREATION_TABLE_RESERVATION =
            "CREATE TABLE " + TABLE_CLIENTS + " (" +
                    COLONNE_ID_RESERVATION + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLONNE_DATEARRIVE_RESERVATION + "TEXT," +
                    COLONNE_DATEDEPART_RESERVATION + " TEXT," +
                    COLONNE_NBINVITE_RESERVATION+ " TEXT," +
                    COLONNE_COMMENTAIRE_RESERVATION + " TEXT)";

    public Stars10DB(Context context) {
        super(context, NOM_BASE_DE_DONNEES, null, VERSION_BASE_DE_DONNEES);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Création de la table lors de la première exécution
        db.execSQL(CREATION_TABLE_CLIENTS);
        db.execSQL(CREATION_TABLE_RESERVATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Mise à jour de la base de données (si nécessaire)
        // Cette méthode est appelée lorsqu'une nouvelle version de la base de données est disponible.
    }
}
