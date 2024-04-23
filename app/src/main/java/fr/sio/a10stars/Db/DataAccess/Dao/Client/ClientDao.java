package fr.sio.a10stars.Db.DataAccess.Dao.Client;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.HashMap;
import java.util.List;

import fr.sio.a10stars.Modele.Client.Client;

@Dao
public interface ClientDao {

    @Query("SELECT * FROM Client")
    List<Client> findAll();

    @Query("SELECT * FROM Client WHERE nom LIKE '%' || :query || '%'")
    List<Client> findClientsByName(String query);



    @Insert
    long insert(Client client);

    @Update
    int update(Client client);

    @Delete
    int delete(Client client);
}
