package ru.javaops.android.tamagotchi.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.javaops.android.tamagotchi.model.Pet;

@Dao
public interface PetDao {
    @Query("SELECT * FROM pet")
    List<Pet> getAll();

    @Query("SELECT * FROM pet WHERE id = :id")
    LiveData<Pet> findById(long id);

    @Query("SELECT * FROM pet LIMIT 1")
    LiveData<Pet> findAny();

    @Insert
    long insert(Pet pet);

    @Update
    void update(Pet pet);

    @Delete
    void delete(List<Pet> pet);
}
