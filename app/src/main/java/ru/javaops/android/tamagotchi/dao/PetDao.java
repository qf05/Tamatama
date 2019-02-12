package ru.javaops.android.tamagotchi.dao;


import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import ru.javaops.android.tamagotchi.model.Pet;

@Dao
public interface PetDao {
    @Query("SELECT * FROM pet")
    List<Pet> getAll();

    @Query("SELECT * FROM pet WHERE id IN (:petId)")
    List<Pet> loadAllById(long[] petId);

    @Query("SELECT * FROM pet WHERE id LIKE :id LIMIT 1")
    Pet findById(long id);

    @Query("SELECT * FROM pet LIMIT 1")
    Pet findAny();

    @Insert
    long insert(Pet pet);

    @Update
    void update(Pet pet);

    @Delete
    void delete(Pet pet);
}
