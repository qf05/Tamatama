package ru.javaops.android.tamagotchi.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import ru.javaops.android.tamagotchi.enums.ActionType;
import ru.javaops.android.tamagotchi.model.History;

@Dao
public interface HistoryDao {

    @Insert
    long insert(History history);

    @Delete
    void delete(List<History> history);

    @Query("SELECT * FROM history")
    List<History> getAll();

    @Query("SELECT date FROM history")
    List<Long> getAllDate();

    @Query("SELECT * FROM history WHERE date BETWEEN :startDay AND :endDay")
    List<History> getAllFromDate(long startDay, long endDay);

    @Query("SELECT * FROM history WHERE action_type LIKE :actionType")
    List<History> getAllFromAction(ActionType actionType);

    @Query("SELECT * FROM history WHERE pet_id LIKE :petId")
    List<History> getAllFromPetId(long petId);
}
